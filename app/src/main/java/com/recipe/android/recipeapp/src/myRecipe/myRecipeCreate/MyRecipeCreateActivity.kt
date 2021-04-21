package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityMyRecipeCreateBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResponse
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`.MyRecipeCreateActivityView
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.adapter.PickItemRecyclerViewAdapter
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.dialog.AddIngredientDialog
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.dialog.PickIconDialog
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.DirectIngredientList
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreate
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse
import gun0912.tedimagepicker.builder.TedImagePicker
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

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

    lateinit var bitmap: Bitmap

    lateinit var uri: Uri

    // 리사이클러뷰
    val pickItem = ArrayList<DirectIngredientList>()
    lateinit var pickItemRecyclerViewAdapter: PickItemRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.imgPick.setOnClickListener {
            TedImagePicker.with(this)
                .start { uri ->
                    this.uri = uri
                    showSingleImage(uri)
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

        // 재료 추가 버튼 클릭
        binding.btnIngredient.setOnClickListener {
            val addIngredientDialog = AddIngredientDialog(this, this, this)
            addIngredientDialog.show()
        }

        // 재료 선택
        pickItemRecyclerViewAdapter = PickItemRecyclerViewAdapter(this)
        binding.rvIngredient.apply {
            adapter = pickItemRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@MyRecipeCreateActivity, RecyclerView.HORIZONTAL, false)
        }
    }

    private fun showSingleImage(uri: Uri) {
        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        binding.imgPick.setImageBitmap(bitmap)
        isPhoto = true
    }

    private fun imageUpload() {
        if (isPhoto) {
            // 사진선택
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)

        } else {
            // 기본 이미지
            bitmap = (ContextCompat.getDrawable(
                this,
                R.drawable.img_default_my_recipe
            ) as BitmapDrawable).bitmap
        }

        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()


        var timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imgFileName = "${userIdx}_${timeStamp}_.png"
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

                val param = MyRecipeCreate(thumbnail, title!!, content!!, ingredientList)
                MyRecipeCreateService(this).postMyRecipeCreate(param)

            } else {
                showCustomToast(getString(R.string.networkError))
            }
        }

    }

    override fun onResume() {
        super.onResume()

        pickItem.clear()
        pickItemRecyclerViewAdapter.submitList(pickItem)

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

    }

    // 재료 선택 버튼
    override fun selectPickDirect() {
        val pickIconDialog = PickIconDialog(this, this, this)
        pickIconDialog.show()
    }

    // 재료 선택
    override fun pickItem(ingredient: Ingredient) {

    }

    // 삭제
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
    }


    override fun onPostMyRecipeCreateSuccess(response: MyRecipeCreateResponse) {
        dismissLoadingDialog()
        finish()
        showCustomToast(getString(R.string.myRecipeSaveComplete))
    }

    override fun onMyRecipeCreateFailure(message: String) {

    }

    override fun onGetIngredientMyRecipeSuccess(response: IngredientResponse) {

    }

    override fun btnSaveClick(pickIconUrl: String, name: String) {
        pickItem.add(DirectIngredientList(name, pickIconUrl))
    }


}