package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.dialog

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.GOOGLE_LOGIN
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.KAKAO_LOGIN
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.LOGIN_TYPE
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.NAVER_LOGIN
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sSharedPreferences
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.DialogCancelCreateRecipeBinding
import com.recipe.android.recipeapp.databinding.DialogDeleteIdBinding
import com.recipe.android.recipeapp.src.myRecipe.MyRecipeActivity
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.MyRecipeCreateActivity
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`.MyRecipeCreateActivityView
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreate
import com.recipe.android.recipeapp.src.setting.SettingService

class CreateCancelDialog(context: Context, val view: MyRecipeCreateActivityView): Dialog(context) {

    val TAG = "DeleteIdDialog"

    private lateinit var binding: DialogCancelCreateRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogCancelCreateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val params = window!!.attributes
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params

        binding.btnYes.setOnClickListener {
            view.cancelCreateRecipe()
            dismiss()
        }

        binding.btnNo.setOnClickListener {
            dismiss()
        }

    }
}