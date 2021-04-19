package com.recipe.android.recipeapp.src.receipt

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.ktx.Firebase
import com.google.gson.*
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityReceiptBinding
import com.recipe.android.recipeapp.src.receipt.`interface`.ReceiptView
import com.recipe.android.recipeapp.src.receipt.adapter.ReceiptRecyclerviewAdapter
import com.recipe.android.recipeapp.src.receipt.dialog.ReceiptDialog
import com.recipe.android.recipeapp.src.receipt.models.BuyItem
import com.recipe.android.recipeapp.src.receipt.models.GetAllReceiptResponse
import com.recipe.android.recipeapp.src.receipt.models.PostNewReceiptRequest
import com.recipe.android.recipeapp.src.receipt.models.PostNewReceiptResponse
import gun0912.tedimagepicker.builder.TedImagePicker
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ReceiptActivity : BaseActivity<ActivityReceiptBinding>(ActivityReceiptBinding::inflate), ReceiptView {

    val TAG = "ReceiptActivity"
    lateinit var bitmap : Bitmap
    private lateinit var functions: FirebaseFunctions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뒤로 가기 버튼 클릭
        binding.btnBack.setOnClickListener {
            finish()
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

                    // 영수증 입력 api 호출
                    addReceipt()

                    // 영수증 전체 조회 api 호출
                    ReceiptService(this).tryGetAllReceipt()
                }
            }
    }

    @SuppressLint("SimpleDateFormat")
    private fun addReceipt() {
        val calendar = Calendar.getInstance().time
        val today = SimpleDateFormat("yy.MM.dd").format(calendar)

        val list = arrayListOf<BuyItem>()
        list.add(BuyItem("오이"))
        list.add(BuyItem("당근"))

        val request = PostNewReceiptRequest(title = "Emart", receiptDate = today, buyList = list)
        ReceiptService(this).tryPostNewReceipt(request)
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


    override fun onPostNewReceiptSuccess(response: PostNewReceiptResponse) {

    }

    override fun onPostNewReceiptFailure(message: String) {

    }

    override fun onGetAllReceiptSuccess(response: GetAllReceiptResponse) {
        val receiptList = response.result

        val adapter = ReceiptRecyclerviewAdapter(receiptList)
        binding.receiptActivityRecyclerview.adapter = adapter
        adapter.receiptMoreBtnItemClick = object : ReceiptRecyclerviewAdapter.ReceiptMoreBtnItemClick{
            override fun onClick(view: View, position: Int) {
                ReceiptDialog(this@ReceiptActivity).show()
            }
        }
    }

    override fun onGetAllReceiptFailure(message: String) {

    }
}