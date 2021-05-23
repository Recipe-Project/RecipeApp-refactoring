package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.GOOGLE_LOGIN
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.KAKAO_LOGIN
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.LOGIN_TYPE
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.NAVER_LOGIN
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sSharedPreferences
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.DialogDeleteIdBinding
import com.recipe.android.recipeapp.src.myRecipe.MyRecipeActivity
import com.recipe.android.recipeapp.src.setting.SettingService

class CreateCancelDialog: BaseActivity<DialogDeleteIdBinding>(DialogDeleteIdBinding::inflate) {

    val TAG = "DeleteIdDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.btnYes.setOnClickListener {
            val intent = Intent(this, MyRecipeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        binding.btnNo.setOnClickListener {
            finish()
        }

    }
}