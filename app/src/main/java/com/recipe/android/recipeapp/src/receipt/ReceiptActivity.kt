package com.recipe.android.recipeapp.src.receipt

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.ktx.Firebase
import com.google.gson.*
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityReceiptBinding
import gun0912.tedimagepicker.builder.TedImagePicker
import java.io.ByteArrayOutputStream
import java.io.IOException


class ReceiptActivity : BaseActivity<ActivityReceiptBinding>(ActivityReceiptBinding::inflate) {

    val TAG = "ReceiptActivity"
    lateinit var bitmap : Bitmap
    private lateinit var functions: FirebaseFunctions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // 영수증 입력을 위해 카메라 및 앫범 실행
        binding.inputReceiptBtn.setOnClickListener {
            TedImagePicker.with(this).start { uri ->  
                Log.d(TAG, uri.toString())

                recognizeReceipt(uri)
            }
        }

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
                    val result = annotation["text"].asString
                    Log.d(TAG, result)
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

    override fun onStart() {
        super.onStart()

    }
}