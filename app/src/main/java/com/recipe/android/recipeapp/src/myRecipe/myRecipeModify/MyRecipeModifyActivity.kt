package com.recipe.android.recipeapp.src.myRecipe.myRecipeModify

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityMyRecipeModifyBinding
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreate
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse
import com.recipe.android.recipeapp.src.myRecipe.myRecipeModify.`interface`.MyRecipeModifyActivityView
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MyRecipeModifyActivity: BaseActivity<ActivityMyRecipeModifyBinding>(ActivityMyRecipeModifyBinding::inflate), MyRecipeModifyActivityView {

    val TAG = "MyRecipeModifyActivity"

    private var myRecipeIdx: Int = 0

    // 현재 유저
    val userIdx = ApplicationClass.sSharedPreferences.getInt(ApplicationClass.USER_IDX, 0)

    // 파이어베이스 사진
    var data: ByteArray? = null
    var downloadUri: String? = null

    // 마이레시피 저장
    var thumbnail: String? = null
    var title: String? = null
    var content: String? = null
    var ingredientList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myRecipeIdx = intent.getIntExtra("myRecipeIdx", 0)
        Log.d(TAG, "MyRecipeModifyActivity - onCreate() : 마이레시피 인덱스 : $myRecipeIdx")

        // 저장된 입력레시피 정보
        title = intent.getStringExtra("title")
        content = intent.getStringExtra("content")
        // 수정 필요
//        ingredientList = intent.getIntegerArrayListExtra("ingredientList") as ArrayList<Int>
        thumbnail = intent.getStringExtra("thumbnail")


        binding.tvTitle.text = title

        binding.tvTitle.setOnClickListener {
            binding.tvTitle.visibility = View.GONE
            binding.etTitle.visibility = View.VISIBLE
            binding.etTitle.requestFocus()
            showKeyboard(binding.etTitle)
        }

        Glide.with(this).load(thumbnail).into(binding.imgPick)

        // 취소 버튼 클릭
        binding.btnCancel.setOnClickListener {
            finish()
        }

        // 저장 버튼 클릭
        binding.btnSave.setOnClickListener {

            showLoadingDialog()

            title = binding.etTitle.text.toString()
            if (title!!.isEmpty()) {
                dismissLoadingDialog()
                showCustomToast(getString(R.string.pleaseEnterTitle))
            }

            content = binding.etContent.text.toString()

            if (content!!.isEmpty()) {
                dismissLoadingDialog()
                showCustomToast(getString(R.string.pleaseEnterContent))
            }


            if (title!!.isNotEmpty() && content!!.isNotEmpty()) {
                imageUpload()
            }

        }

    }

    private fun imageUpload() {

        // 이미지 피커 수정 필요
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

                // 나만의 레시피 수정
                val param = MyRecipeCreate(thumbnail, title!!, content!!, ingredientList)
                MyRecipeModifyService(this).patchMyRecipe(param, myRecipeIdx)
            } else {
                showCustomToast(getString(R.string.networkError))
            }
        }

    }

    // 수정 성공
    override fun onPatchMyRecipeSuccess(response: MyRecipeCreateResponse) {
        dismissLoadingDialog()
        if (response.isSuccess) {
            showCustomToast(getString(R.string.myRecipeModifyComplete))
            finish()
        } else {
            showCustomToast(getString(R.string.networkError))
        }
    }

    override fun onPatchMyRecipeFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(getString(R.string.networkError))
    }
}