package com.recipe.android.recipeapp.src.signIn

import android.os.Bundle
import android.util.Log
import com.kakao.sdk.common.util.Utility
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivitySignInBinding

// 로그인 액티비티
class SignInActivity: BaseActivity<ActivitySignInBinding>(ActivitySignInBinding::inflate) {

    val TAG = "SignInActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var keyHash = Utility.getKeyHash(this)
        Log.d(
            TAG, "SignInActivity - onCreate() : 해시 : $keyHash")

    }
}