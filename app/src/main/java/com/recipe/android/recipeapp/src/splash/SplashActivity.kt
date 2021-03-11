package com.recipe.android.recipeapp.src.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivitySplashBinding
import com.recipe.android.recipeapp.src.MainActivity
import com.recipe.android.recipeapp.src.signIn.SignInActivity
import com.recipe.android.recipeapp.src.splash.`interface`.SplashActivityView
import com.recipe.android.recipeapp.src.splash.models.AutoLoginResponse

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate),
    SplashActivityView {

    val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 자동로그인 API 호출
        SplashSerivce(this).postAutoLogin()

    }

    override fun onPostAutoLoginSuccess(response: AutoLoginResponse) {
        if (response.isSuccess && response.code == 1000) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            Log.d(
                TAG,
                "SplashActivity - onPostAutoLoginSuccess() : 코드 ${response.code} : ${response.message}"
            )
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPostAutoLoginFailure(message: String) {
        Log.d(TAG, "SplashActivity - onPostAutoLoginFailure() : $message")
        showCustomToast(getString(R.string.networkError))
    }
}