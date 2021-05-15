package com.recipe.android.recipeapp.src.setting.deleteId

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
import com.recipe.android.recipeapp.src.setting.SettingService

class DeleteIdDialog: BaseActivity<DialogDeleteIdBinding>(DialogDeleteIdBinding::inflate) {

    val TAG = "DeleteIdDialog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val userIdx = intent.getIntExtra("userIdx", 0)

        val mOAuthLoginInstance = OAuthLogin.getInstance()

        binding.btnYes.setOnClickListener {
            SettingService().deleteId(userIdx)

            when (sSharedPreferences.getString(LOGIN_TYPE, "")) {
                NAVER_LOGIN -> {
                    val isSuccessDeleteToken: Boolean = mOAuthLoginInstance.logoutAndDeleteToken(this)

                    if (!isSuccessDeleteToken) {
                        Log.d(TAG, "errorCode:" + mOAuthLoginInstance.getLastErrorCode(this))
                        Log.d(TAG, "errorDesc:" + mOAuthLoginInstance.getLastErrorDesc(this))
                    }

                }
                GOOGLE_LOGIN -> {
                    val firebaseAuth = FirebaseAuth.getInstance()
                    firebaseAuth.getCurrentUser().delete()
                }
                KAKAO_LOGIN -> {
                    UserApiClient.instance.unlink { error ->
                        if (error != null) {
                            Log.e(TAG, "연결 끊기 실패", error)
                        } else {
                            Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                        }
                    }
                }
            }

            val intent = Intent(this, DeleteIdSuccess::class.java)
            startActivity(intent)
        }

        binding.btnNo.setOnClickListener {
            finish()
        }

    }
}