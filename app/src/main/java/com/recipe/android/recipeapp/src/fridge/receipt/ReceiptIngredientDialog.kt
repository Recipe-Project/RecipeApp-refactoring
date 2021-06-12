package com.recipe.android.recipeapp.src.fridge.receipt

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.*
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.DialogReceiptIngredientBinding
import com.recipe.android.recipeapp.src.fridge.addDirect.AddDirectActivity
import com.recipe.android.recipeapp.src.fridge.basket.BasketActivity
import com.recipe.android.recipeapp.src.fridge.dialog.PickIngredientIconDialog
import com.recipe.android.recipeapp.src.fridge.pickIngredient.PickIngredientService
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.*
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.PostReceiptIngredientRequest
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.ReceiptIngredientDialogView
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.ReceiptIngredientView
import com.recipe.android.recipeapp.src.fridge.receipt.adapter.ReceiptIngredientRecyclerviewAdapter
import com.recipe.android.recipeapp.src.fridge.receipt.models.PostReceiptIngredientResponse
import com.recipe.android.recipeapp.src.fridge.receipt.models.PostReceiptIngredientResult
import java.io.ByteArrayOutputStream
import java.io.IOException

class ReceiptIngredientDialog : BaseActivity<DialogReceiptIngredientBinding>(DialogReceiptIngredientBinding::inflate), ReceiptIngredientView, ReceiptIngredientDialogView,
    PickIngredientActivityView, PickIngredientIconDialog.PickIcon {

    val TAG = "ReceiptIngredientDialog"
    var bitmap : Bitmap? = null
    private lateinit var functions: FirebaseFunctions
    var receiptIngredientList = ArrayList<PostReceiptIngredientResult>()
    lateinit var receiptIngredientRecyclerviewAdapter : ReceiptIngredientRecyclerviewAdapter
    lateinit var pickIngredientIconDialog : PickIngredientIconDialog
    var ingredientResult = ArrayList<CategoryIngrediets>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var uri : String? = null
        if(intent.hasExtra("uri")) {
            uri = intent.getStringExtra("uri")
            val newUri = "file://$uri"
            showLoadingDialog()
            recognizeReceipt(Uri.parse(newUri))
        }


        // 전체 재료 조회
        PickIngredientService(this).getIngredients("")

        binding.btnSave.setOnClickListener {
            // 냉장고 바구니로 이동
            showLoadingDialog()
            val pickIdxList = ArrayList<Int>()
            receiptIngredientList.forEach {
                pickIdxList.add(it.ingredientIdx)
            }
            PickIngredientService(this).postIngredients(pickIdxList)
        }
    }

    private fun recognizeReceipt(uri: Uri) {
        if (Build.VERSION.SDK_INT >= 29) {
            val source = ImageDecoder.createSource(contentResolver, uri)
            try {
                bitmap = ImageDecoder.decodeBitmap(source)
            } catch (e : IOException) {
                e.printStackTrace()
            }
        } else {
            bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        }
        bitmap = bitmap?.let { scaleBitmapDown(it, 640) }

        // Convert bitmap to base64 encoded string
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes: ByteArray = byteArrayOutputStream.toByteArray()
        val base64encoded = Base64.encodeToString(imageBytes, Base64.NO_WRAP)

        // Cloud Function의 인스턴스 초기화
        functions = FirebaseFunctions.getInstance()

        // Create json request to cloud vision
        val request = JsonObject()
        // Add image to request
        val image = JsonObject()
        image.add("content", JsonPrimitive(base64encoded))
        request.add("image", image)
        //Add features to the request
        val feature = JsonObject()
        feature.add("type", JsonPrimitive("DOCUMENT_TEXT_DETECTION"))
        val features = JsonArray()
        features.add(feature)
        request.add("features", features)

        val imageContext = JsonObject()
        val languageHints = JsonArray()
        languageHints.add("ko")
        imageContext.add("languageHints", languageHints)
        request.add("imageContext", imageContext)

        Log.d(TAG, "ReceiptIngredientDialog - recognizeReceipt() : request : $request")

        annotateImage(request.toString())
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    // Task failed with an exception
                    Log.d(TAG, "ReceiptActivity - Text Recognition Failed")
                    Log.d(TAG, "ReceiptIngredientDialog - recognizeReceipt() : result :  \n exceptions: ${task.exception}")
                } else {
                    // Task completed successfully
                    Log.d(TAG, "ReceiptActivity - Text Recognition Success")

                    val annotation = task.result!!.asJsonArray[0].asJsonObject["fullTextAnnotation"].asJsonObject
                    val result = annotation["text"].toString()
                    Log.d(TAG, result)

                    // 영수증으로 재료 입력 api 호출
                    ReceiptIngredientService(this).postReceiptIngredient(
                        PostReceiptIngredientRequest(result)
                    )
                }
            }
    }

    private fun scaleBitmapDown(bitmap: Bitmap, maxDimension: Int): Bitmap {
        val originalWidth = bitmap.width
        val originalHeight = bitmap.height
        var resizedWidth = maxDimension
        var resizedHeight = maxDimension
        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension
            resizedWidth =
                (resizedHeight * originalWidth.toFloat() / originalHeight.toFloat()).toInt()
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension
            resizedHeight =
                (resizedWidth * originalHeight.toFloat() / originalWidth.toFloat()).toInt()
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension
            resizedWidth = maxDimension
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false)
    }

    private fun annotateImage(requestJson: String): Task<JsonElement> {
        return functions
            .getHttpsCallable("annotateImage")
            .call(requestJson)
            .continueWith { task ->
                // This continuation runs on either success or failure, but if the task
                // has failed then result will throw an Exception which will be
                // propagated down.
                val result = task.result?.data
                JsonParser.parseString(Gson().toJson(result))
            }
    }

    override fun onPostReceiptIngredientSuccess(response: PostReceiptIngredientResponse) {
        dismissLoadingDialog()
        if(response.isSuccess) {
            receiptIngredientList = response.result
            receiptIngredientRecyclerviewAdapter = ReceiptIngredientRecyclerviewAdapter(this)
            binding.recyclerview.adapter = receiptIngredientRecyclerviewAdapter
            receiptIngredientRecyclerviewAdapter.submitList(receiptIngredientList)
        } else {
            showCustomToast("검색된 재료가 없습니다.")
        }
    }

    override fun onPostReceiptIngredientFailure(message: String) {

    }

    override fun clearIngredient(position: Int) {
        receiptIngredientList.removeAt(position)
        receiptIngredientRecyclerviewAdapter.notifyDataSetChanged()
    }

    override fun pickIngredient() {
        pickIngredientIconDialog.show()
    }

    override fun onGetIngredientSuccess(response: IngredientResponse) {
        if (response.isSuccess) {

            // 전체 재료 리스트 for PickingIngredientIconDialog
            response.result.ingredients.forEach{
                ingredientResult.add(it)
            }
            pickIngredientIconDialog = PickIngredientIconDialog(
                this,
                this,
                ingredientResult,
                this
            )
        }
    }

    override fun onPostIngredientSuccess(response: PostIngredientsResponse) {
        dismissLoadingDialog()

        if (response.isSuccess) {
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        } else {
            when (response.code) {
                3069 -> showCustomToast(response.message)
                2068 -> showCustomToast(response.message)
                else -> {
                    Log.d(TAG, "PickIngredientActivity - onPostIngredientSuccess() : ${response.message}")
                    showCustomToast(getString(R.string.networkError))
                }
            }
        }
    }

    override fun pickItem(ingredient: Ingredient) {

    }

    override fun removePickItem(ingredient: Int) {

    }

    override fun addDirectFailure(message: String) {

    }

    override fun removePickMyIngredients(ingredient: Ingredient) {

    }

    override fun getBasketCntSuccess(response: GetBasketCntResponse) {

    }

    override fun btnSaveClick(pickIconUrl: String?) {

    }
}