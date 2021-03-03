package com.recipe.android.recipeapp.src.signIn

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.recipe.android.recipeapp.BuildConfig
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivitySignInBinding
import com.recipe.android.recipeapp.src.signIn.`interface`.SignInActivityView
import com.recipe.android.recipeapp.src.signIn.models.SignInResponse

// 로그인 액티비티
class SignInActivity : BaseActivity<ActivitySignInBinding>(ActivitySignInBinding::inflate),
    SignInActivityView {

    val TAG = "SignInActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var keyHash = Utility.getKeyHash(this)
        Log.d(
            TAG, "SignInActivity - onCreate() : 해시 : $keyHash"
        )

        // 카카오 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e(TAG, "로그인 실패", error)
            } else if (token != null) {
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
                val kakaoAccessToken = token.accessToken
                // 레저 서버 -> 카카오 로그인 API 호출
                SignInService(this).postKaKaoLogin(kakaoAccessToken)
            }
        }

        // 카카오 로그인 버튼 클릭
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        binding.btnKakaoSignIn.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

//            // 카카오 연결 끊기
//            UserApiClient.instance.unlink { error ->
//                if (error != null) {
//                    Log.e(TAG, "연결 끊기 실패", error)
//                }
//                else {
//                    Log.i(TAG, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
//                }
//            }

        // 네이버 로그인 버튼 클릭
        binding.btnNaverSignIn.setOnClickListener {

            val mOAuthLoginModule = OAuthLogin.getInstance();
            mOAuthLoginModule.init(
                this, BuildConfig.NAVER_CLIENT_ID, BuildConfig.NAVER_CLIENT_SECRET, "RecipeApp"
            )
            val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak")
            object : OAuthLoginHandler() {
                override fun run(success: Boolean) {
                    if (success) {
                        val accessToken: String =
                            mOAuthLoginModule.getAccessToken(applicationContext)
                        val refreshToken: String =
                            mOAuthLoginModule.getRefreshToken(applicationContext)
                        val expiresAt: Long = mOAuthLoginModule.getExpiresAt(applicationContext)
                        val tokenType: String = mOAuthLoginModule.getTokenType(applicationContext)
                        Log.d(TAG, "SignInDialog - run() : 네이버 로그인 성공 / $accessToken")
                        SignInService(this@SignInActivity).postNaverLogin(accessToken)
                    } else {
                        val errorCode: String =
                            mOAuthLoginModule.getLastErrorCode(applicationContext).code
                        val errorDesc: String =
                            mOAuthLoginModule.getLastErrorDesc(applicationContext)
                        Log.d(
                            TAG,
                            "SignInDialog - run() : / errorCode: $errorCode errorDesc: $errorDesc"
                        )
                    }
                }
            }
            mOAuthLoginModule.startOauthLoginActivity(this, mOAuthLoginHandler)
        }
    }

    override fun onPostSignInSuccess(response: SignInResponse) {
        // 로그인 동작 추후 수정 필요
    }

    override fun onPostSignInFailure(message: String) {

    }
}
