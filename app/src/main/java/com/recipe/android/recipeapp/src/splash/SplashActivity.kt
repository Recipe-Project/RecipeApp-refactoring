package com.recipe.android.recipeapp.src.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.FCM_TOKEN
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sSharedPreferences
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

        Log.d(TAG, "SplashActivity - onCreate() : ${sSharedPreferences.getString(FCM_TOKEN, "")}")

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            sSharedPreferences.edit().putString(FCM_TOKEN, token).apply()
            Log.d(TAG, "SplashActivity - onCreate() : fcm 토큰 : $token")
        })
    }

    override fun onPostAutoLoginSuccess(response: AutoLoginResponse) {
        if (response.isSuccess && response.code == 1000) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        } else {
            Log.d(
                TAG,
                "SplashActivity - onPostAutoLoginSuccess() : 코드 ${response.code} : ${response.message}"
            )
            val intent = Intent(this, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    override fun onPostAutoLoginFailure(message: String) {
        Log.d(TAG, "SplashActivity - onPostAutoLoginFailure() : $message")
        showCustomToast(getString(R.string.networkError))
    }
}