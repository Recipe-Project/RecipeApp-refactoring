package com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeDetail.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.DialogDeleteMyRecipeBinding
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeDetail.MyRecipeDetailActivity
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeDetail.MyRecipeDetailService
import com.recipe.android.recipeapp.src.setting.SettingService

class MyRecipeDeleteDialog : BaseActivity<DialogDeleteMyRecipeBinding>(DialogDeleteMyRecipeBinding::inflate) {

    private val DELETE_YES = 300
    private val DELETE_NO = 400

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // 네
        binding.btnYes.setOnClickListener {
            // result 보내기
            val intent = Intent(this, MyRecipeDetailActivity::class.java)
            setResult(DELETE_YES, intent)
            finish()
        }

        // 취소
        binding.btnNo.setOnClickListener {
            val intent = Intent(this, MyRecipeDetailActivity::class.java)
            setResult(DELETE_NO, intent)
            finish()
        }

    }
}