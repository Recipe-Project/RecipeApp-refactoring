package com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityMyRecipeDetailBinding
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.MyRecipeCreateActivity
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.DirectIngredientList
import com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.`interface`.MyRecipeDetailActivityView
import com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.adapter.MyRecipeIngredientRecyclerViewAdapter
import com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.dialog.MyRecipeDeleteDialog
import com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.models.MyRecipeDeleteResponse
import com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.models.MyRecipeDetailResponse
import com.recipe.android.recipeapp.src.myRecipe.myRecipeModify.MyRecipeModifyActivity

class MyRecipeDetailActivity: BaseActivity<ActivityMyRecipeDetailBinding>(ActivityMyRecipeDetailBinding::inflate), MyRecipeDetailActivityView {

    val TAG = "MyRecipeDetailActivity"

    // 삭제 관련 코드
    private val DELETE_CODE = 200
    private val DELETE_YES = 300

    private var myRecipeIdx: Int = 0

    val myRecipeIngredientItemList = ArrayList<DirectIngredientList>()
    lateinit var myRecipeIngredientRecyclerViewAdapter: MyRecipeIngredientRecyclerViewAdapter

    // 썸네일
    private var thumbnail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myRecipeIdx = intent.getIntExtra("myRecipeIdx", 0)

        myRecipeIngredientRecyclerViewAdapter = MyRecipeIngredientRecyclerViewAdapter()
        binding.rvIngredient.apply {
            adapter = myRecipeIngredientRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }

        // 삭제 버튼 클릭
        binding.btnDelete.setOnClickListener {
            // 삭제 확인 다이얼로그
            val intent = Intent(this, MyRecipeDeleteDialog::class.java)
            startActivityForResult(intent, DELETE_CODE)
        }

        // 수정 버튼 클릭
        binding.btnModify.setOnClickListener {
            val intent = Intent(this, MyRecipeCreateActivity::class.java)
            intent.putExtra("isModify", true)
            intent.putExtra("myRecipeIdx", myRecipeIdx)
            intent.putExtra("title", binding.tvTitle.text)
            intent.putExtra("content", binding.tvContent.text)
            intent.putExtra("ingredientList", myRecipeIngredientItemList)
            intent.putExtra("thumbnail", thumbnail)
            startActivity(intent)
        }

        // 나만의 레시피 상세 조회
        MyRecipeDetailService(this).getMyRecipeDetail(myRecipeIdx)

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            DELETE_CODE -> {
                when (resultCode) {
                    DELETE_YES -> {
                        // 나만의 레시피 삭제
                        showLoadingDialog()
                        MyRecipeDetailService(this).deleteMyRecipe(myRecipeIdx)
                        finish()
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }

    // 레시피 상세보기 성공
    override fun onGetMyRecipeDetailSuccess(response: MyRecipeDetailResponse) {
        if (response.isSuccess) {
            if (response.result.thumbnail != null) {
                thumbnail = response.result.thumbnail.toString()
                Glide.with(this).load(thumbnail).into(binding.imgThumbnail)
            }

            binding.tvTitle.text = response.result.title
            binding.tvContent.text = response.result.content

            myRecipeIngredientItemList.clear()
            response.result.ingredientList.forEach {
                myRecipeIngredientItemList.add(it)
            }
            myRecipeIngredientRecyclerViewAdapter.submitList(myRecipeIngredientItemList)
        }
    }

    override fun onGetMyRecipeDetailFailure(message: String) {

    }

    // 레시피 삭제 성공
    override fun onDeleteMyRecipeSuccess(response: MyRecipeDeleteResponse) {
        dismissLoadingDialog()
        if (response.isSuccess) {
            showCustomToast(getString(R.string.deleteMyRecipeMessage))
        } else {
            showCustomToast(getString(R.string.networkError))
            Log.d(TAG, "MyRecipeDetailActivity - onDeleteMyRecipeSuccess() : ${response.code}, ${response.message}")
        }
    }
}