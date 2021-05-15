package com.recipe.android.recipeapp.src.setting.signOut

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sSharedPreferences
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.DialogSignOutBinding
import com.recipe.android.recipeapp.src.signIn.SignInActivity

class SignOutDialog: BaseActivity<DialogSignOutBinding>(DialogSignOutBinding::inflate) {

    val TAG = "SignOutDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val userIdx = intent.getIntExtra("userIdx", 0)

        binding.btnYes.setOnClickListener {
            sSharedPreferences.edit().putString(X_ACCESS_TOKEN, null).apply()

            when (sSharedPreferences.getString(ApplicationClass.LOGIN_TYPE, "")) {
                ApplicationClass.NAVER_LOGIN -> {
                    val mOAuthLoginModule = OAuthLogin.getInstance();
                    mOAuthLoginModule.logout(this)
                }
                ApplicationClass.GOOGLE_LOGIN -> {
                    val firebaseAuth = FirebaseAuth.getInstance()
                    firebaseAuth.signOut()
                }
                ApplicationClass.KAKAO_LOGIN -> {
                    UserApiClient.instance.logout { error ->
                        if (error != null) {
                            Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                        }
                        else {
                            Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                        }
                    }
                }
            }


            val intent = Intent(this, SignInActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            showCustomToast(getString(R.string.logOutSuccess))
        }

        binding.btnNo.setOnClickListener {
            finish()
        }

    }
}