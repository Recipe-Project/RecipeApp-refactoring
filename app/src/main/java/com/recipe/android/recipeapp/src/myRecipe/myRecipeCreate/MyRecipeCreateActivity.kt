package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityMyRecipeCreateBinding
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`.MyRecipeCreateActivityView
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreate
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse
import gun0912.tedimagepicker.builder.TedImagePicker
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyRecipeCreateActivity :
    BaseActivity<ActivityMyRecipeCreateBinding>(ActivityMyRecipeCreateBinding::inflate),
    MyRecipeCreateActivityView {

    val TAG = "MyRecipeCreateActivity"

    private var selectedUriList: List<Uri>? = null

    // 현재 유저
    val userIdx = ApplicationClass.sSharedPreferences.getInt(ApplicationClass.USER_IDX, 0)

    // 파이어베이스 사진
    var uriPhoto: Uri? = null
    var data: ByteArray? = null
    var downloadUri: String? = null

    // 마이레시피 저장
    var thumbnail: String? = null
    var title: String? = null
    var content: String? = null
    val ingredientList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 수정 필요
        binding.imgPick.setOnClickListener {
            TedImagePicker.with(this)
                .startMultiImage { uriList ->
                    // showMultiImage(uriList)
                }
        }

        binding.etTitle.setOnClickListener {
            binding.etTitle.requestFocus()
            showKeyboard(binding.etTitle)
        }

        // 취소 버튼 클릭
        binding.btnCancel.setOnClickListener {
            finish()
        }

        // 저장 버튼 클릭
        binding.btnSave.setOnClickListener {

            showLoadingDialog()

            title = binding.etTitle.text.toString()
            if (title!!.isEmpty()) {
                showCustomToast(getString(R.string.pleaseEnterTitle))
            }

            content = binding.etContent.text.toString()

            if (content!!.isEmpty()) {
                showCustomToast(getString(R.string.pleaseEnterContent))
            }


            if (title!!.isNotEmpty() && content!!.isNotEmpty()) {
                imageUpload()
            }

        }
    }

    private fun imageUpload() {

        val bitmap = (ContextCompat.getDrawable(
            this,
            R.drawable.img_default_my_recipe
        ) as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()


        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imgFileName = "${userIdx}_${timeStamp}_.png"
        val storage = Firebase.storage("gs://recipeapp-a79ed.appspot.com")
        val storageRef = storage.reference
        val thumbnailRef = storageRef.child(imgFileName)

//        thumbnailRef.putFile(uriPhoto!!)?.addOnSuccessListener {
//            Log.d(TAG, "MyRecipeCreateActivity - imageUpload() : 파이어베이스에 사진 업로드 완료")
//        }

//        var uploadTask = thumbnailRef.putBytes(data)
//        uploadTask.addOnFailureListener {
//            // Handle unsuccessful uploads
//        }.addOnSuccessListener { taskSnapshot ->
//            Log.d(TAG, "MyRecipeCreateActivity - imageUpload() : 업로드 성공")
//        }

        val ref = storageRef.child(imgFileName)
        var uploadTask = ref.putBytes(data)


        val urlTask = uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                downloadUri = task.result.toString()
                Log.d(TAG, "MyRecipeCreateActivity - imageUpload() : 다운로드 uri : $downloadUri")

                thumbnail = downloadUri

                val param = MyRecipeCreate(thumbnail, title!!, content!!, ingredientList)
                MyRecipeCreateService(this).postMyRecipeCreate(param)

            } else {
                showCustomToast(getString(R.string.networkError))
            }
        }

    }


    override fun onPostMyRecipeCreateSuccess(response: MyRecipeCreateResponse) {
        dismissLoadingDialog()
        finish()
        showCustomToast(getString(R.string.myRecipeSaveComplete))
    }

    override fun onPostMyRecipeCreateFailure(message: String) {
        TODO("Not yet implemented")
    }
}

//    private fun showMultiImage(uriList: List<Uri>) {
//        this.selectedUriList = uriList
//        Log.d("ted", "uriList: $uriList")
//        binding.ivImage.visibility = View.GONE
//        binding.containerSelectedPhotos.visibility = View.VISIBLE
//
//        binding.containerSelectedPhotos.removeAllViews()
//
//        val viewSize =
//            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics)
//                .toInt()
//        uriList.forEach {
//            val itemImageBinding = ItemImageBinding.inflate(LayoutInflater.from(this))
//            Glide.with(this)
//                .load(it)
//                .apply(RequestOptions().fitCenter())
//                .into(itemImageBinding.ivMedia)
//            itemImageBinding.root.layoutParams = FrameLayout.LayoutParams(viewSize, viewSize)
//            binding.containerSelectedPhotos.addView(itemImageBinding.root)
//        }
//
//    }