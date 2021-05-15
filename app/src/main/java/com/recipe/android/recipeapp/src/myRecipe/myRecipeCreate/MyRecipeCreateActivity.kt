package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.opensooq.supernova.gligar.GligarPicker
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityMyRecipeCreateBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResponse
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`.MyRecipeCreateActivityView
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.adapter.PickItemRecyclerViewAdapter
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.dialog.AddDirectMyRecipeActivity
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.dialog.AddIngredientDialog
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.dialog.MultiplePickIngredientsDialog
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.dialog.PickIconDialog
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.DirectIngredientList
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MyRecipeCreateActivity :
    BaseActivity<ActivityMyRecipeCreateBinding>(ActivityMyRecipeCreateBinding::inflate),
    PickIconDialog.PickIcon,
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

    // 사진 유무
    private var isPhoto = false

    var bitmap: Bitmap? = null

    lateinit var uri: Uri

    val PICKER_REQUEST_CODE = 5300
    val ADD_DIRECT_CODE = 2900

    var isModify = false

    // 리사이클러뷰
    var pickItem = ArrayList<DirectIngredientList>()
    lateinit var pickItemRecyclerViewAdapter: PickItemRecyclerViewAdapter

    // 수정
    private var myRecipeIdx: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.etTitle.setOnClickListener {
            binding.etTitle.requestFocus()
            showKeyboard(binding.etTitle)
        }

        // 취소 버튼 클릭
        binding.btnCancel.setOnClickListener {
            finish()
        }

        // 재료 선택
        pickItemRecyclerViewAdapter = PickItemRecyclerViewAdapter(this)
        binding.rvIngredient.apply {
            adapter = pickItemRecyclerViewAdapter
            layoutManager =
                LinearLayoutManager(this@MyRecipeCreateActivity, RecyclerView.HORIZONTAL, false)
        }

        // 수정
        isModify = intent?.getBooleanExtra("isModify", false) ?: false

        if (isModify) {
            myRecipeIdx = intent.getIntExtra("myRecipeIdx", 0)

            // 저장된 입력레시피 정보
            title = intent.getStringExtra("title")
            content = intent.getStringExtra("content")
            thumbnail = intent.getStringExtra("thumbnail")

            // 기존 정보 표시
            binding.etTitle.setText(title, TextView.BufferType.EDITABLE)
            binding.etContent.setText(content, TextView.BufferType.EDITABLE)

            if (thumbnail != null) {
                Glide.with(this).load(thumbnail).centerCrop().into(binding.imgPick)
            }

            pickItem = intent?.extras?.getParcelableArrayList<Parcelable>("ingredientList") as ArrayList<DirectIngredientList>
            pickItemRecyclerViewAdapter.submitList(pickItem)

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

        binding.imgPick.setOnClickListener {
            GligarPicker().limit(1).requestCode(PICKER_REQUEST_CODE).withActivity(this)
                .show()
        }

        // 재료 추가 버튼 클릭
        binding.btnIngredient.setOnClickListener {
            val addIngredientDialog = AddIngredientDialog(this, this, this)
            addIngredientDialog.show()
        }


    }

    private fun showSingleImage(uri: Uri) {
        dismissLoadingDialog()
        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        binding.imgPick.setImageBitmap(bitmap)
        isPhoto = true
    }

    private fun imageUpload() {
        if (isPhoto) {
            // 사진
            try {
                bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    ImageDecoder.decodeBitmap(
                        ImageDecoder.createSource(
                            contentResolver,
                            uri
                        )
                    )
                } else {
                    MediaStore.Images.Media.getBitmap(contentResolver, uri)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } else {
            // 기본 이미지
            bitmap = null
        }

        val baos = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()


        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imgFileName = "${userIdx}/${userIdx}_${timeStamp}_.png"
        val storage = Firebase.storage("gs://recipeapp-a79ed.appspot.com")
        val storageRef = storage.reference

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

                val param = HashMap<String, Any?>()
                if (bitmap != null) {
                    param["thumbnail"] = thumbnail!!
                } else {
                    param["thumbnail"] = null
                }

                param["title"] = title!!
                param["content"] = content!!
                param["ingredientList"] = pickItem

                if (isModify) {
                    MyRecipeCreateService(this).patchMyRecipe(param, myRecipeIdx)
                } else {
                    MyRecipeCreateService(this).postMyRecipeCreate(param)
                }

            } else {
                showCustomToast(getString(R.string.networkError))
            }
        }

    }

    override fun onResume() {
        super.onResume()

        if (pickItem.size > 0) {
            binding.rvIngredient.visibility = View.VISIBLE
            binding.tvPleaseAddIngredient.visibility = View.INVISIBLE
        } else {
            binding.rvIngredient.visibility = View.GONE
            binding.tvPleaseAddIngredient.visibility = View.VISIBLE
        }

    }

    // 재료 직접 추가 버튼
    override fun selectAddDirect() {
        val intent = Intent(this, AddDirectMyRecipeActivity::class.java)
        startActivityForResult(intent, ADD_DIRECT_CODE)
    }

    // 재료 선택 버튼
    override fun selectPickDirect() {
        val pickIconDialog = MultiplePickIngredientsDialog(this, this)
        pickIconDialog.show()

    }

    override fun pickItem(ingredient: Ingredient) {

    }

    // 리사이클러뷰에서 삭제 버튼
    override fun removePickItem(position: Int) {
        pickItem.removeAt(position)
        pickItemRecyclerViewAdapter.notifyDataSetChanged()
        if (pickItem.size > 0) {
            binding.rvIngredient.visibility = View.VISIBLE
            binding.tvPleaseAddIngredient.visibility = View.INVISIBLE
        } else {
            binding.rvIngredient.visibility = View.GONE
            binding.tvPleaseAddIngredient.visibility = View.VISIBLE
        }
        binding.rvIngredient.scrollToPosition(pickItem.size - 1)
    }

    // 재료 선택 다이얼로그에서 저장 버튼 클릭
    override fun pickBtnSaveClick(pickIngredientsMyRecipe: ArrayList<Ingredient>) {
        pickIngredientsMyRecipe.forEach {
            if (it.ingredientIcon == "") {
                pickItem.add(DirectIngredientList(it.ingredientName, null))
            } else {
                pickItem.add(DirectIngredientList(it.ingredientName, it.ingredientIcon))
            }
        }
        pickItemRecyclerViewAdapter.submitList(pickItem)
        if (pickItem.size > 0) {
            binding.rvIngredient.visibility = View.VISIBLE
            binding.tvPleaseAddIngredient.visibility = View.INVISIBLE
        } else {
            binding.rvIngredient.visibility = View.GONE
            binding.tvPleaseAddIngredient.visibility = View.VISIBLE
        }
        binding.rvIngredient.scrollToPosition(pickItem.size - 1)
    }


    override fun onPostMyRecipeCreateSuccess(response: MyRecipeCreateResponse) {
        dismissLoadingDialog()
        finish()
        showCustomToast(getString(R.string.myRecipeSaveComplete))
    }

    override fun onMyRecipeCreateFailure(message: String) {
        Log.d(TAG, "MyRecipeCreateActivity - onMyRecipeCreateFailure() : $message")
        showCustomToast(getString(R.string.networkError))
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

    override fun onGetIngredientMyRecipeSuccess(response: IngredientResponse) {

    }

    override fun btnSaveClick(pickIconUrl: String, name: String) {
        pickItem.add(DirectIngredientList(name, pickIconUrl))
        pickItemRecyclerViewAdapter.submitList(pickItem)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICKER_REQUEST_CODE -> {
                showLoadingDialog()
                val imagesList = data?.extras?.getStringArray(GligarPicker.IMAGES_RESULT)
                val pickImage = imagesList?.get(0)
                uri = Uri.parse("file://$pickImage")

                showSingleImage(uri)
            }
            ADD_DIRECT_CODE -> {
                val data = data?.extras?.getParcelableArrayList<Parcelable>("pick") as java.util.ArrayList<Ingredient>
                pickBtnSaveClick(data)
            }
        }
    }
}