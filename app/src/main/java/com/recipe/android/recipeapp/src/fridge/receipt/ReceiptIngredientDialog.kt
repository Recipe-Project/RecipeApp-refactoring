package com.recipe.android.recipeapp.src.fridge.receipt

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
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.DialogReceiptIngredientBinding
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.PostReceiptIngredientRequest
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.ReceiptIngredientDialogView
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.ReceiptIngredientView
import com.recipe.android.recipeapp.src.fridge.receipt.adapter.ReceiptIngredientRecyclerviewAdapter
import com.recipe.android.recipeapp.src.fridge.receipt.models.PostReceiptIngredientResponse
import com.recipe.android.recipeapp.src.fridge.receipt.models.PostReceiptIngredientResult
import java.io.ByteArrayOutputStream
import java.io.IOException

class ReceiptIngredientDialog : BaseActivity<DialogReceiptIngredientBinding>(DialogReceiptIngredientBinding::inflate), ReceiptIngredientView, ReceiptIngredientDialogView {

    val TAG = "ReceiptIngredientDialog"
    lateinit var bitmap : Bitmap
    private lateinit var functions: FirebaseFunctions
    var receiptIngredientList = ArrayList<PostReceiptIngredientResult>()
    lateinit var receiptIngredientRecyclerviewAdapter : ReceiptIngredientRecyclerviewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var uri : String? = null
        if(intent.hasExtra("uri")) {
            uri = intent.getStringExtra("uri")
        }
        recognizeReceipt(Uri.parse(uri))

        binding.btnSave.setOnClickListener {
            // 냉장고 바구니로 이동
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
        bitmap = scaleBitmapDown(bitmap, 640)

        // Convert bitmap to base64 encoded string
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
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

        annotateImage(request.toString())
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    // Task failed with an exception
                    Log.d(TAG, "ReceiptActivity - Text Recognition Failed")
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
        if(response.result != null) {
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

    }
}