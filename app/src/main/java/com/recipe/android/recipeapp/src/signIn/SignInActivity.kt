package com.recipe.android.recipeapp.src.signIn

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.recipe.android.recipeapp.BuildConfig
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivitySignInBinding
import com.recipe.android.recipeapp.src.signIn.`interface`.SignInActivityView
import com.recipe.android.recipeapp.src.signIn.models.SignInResponse

// 로그인 액티비티
class SignInActivity : BaseActivity<ActivitySignInBinding>(ActivitySignInBinding::inflate),
    SignInActivityView {

    val TAG = "SignInActivity"

    //firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    //google client
    private lateinit var googleSignInClient: GoogleSignInClient
    // 구글 로그인 상태 판별 코드
    private val RC_SIGN_IN = 99

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


        // 구글 로그인 버튼 클릭
        binding.btnGoogleSignIn.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)


        //firebase auth 객체 초기화
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onPostSignInSuccess(response: SignInResponse) {
        // 로그인 동작 추후 수정 필요
    }

    override fun onPostSignInFailure(message: String) {

    }

    // onStart. 유저가 앱에 이미 구글 로그인을 했는지 확인
    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account!==null){ // 이미 로그인이 되어있는 경우
            //toMainActivity(firebaseAuth.currentUser)
        }
    } //onStart End

    // 구글 로그인 인증을 요청했을 때 결과값을 되돌려받는 곳
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    } // onActivityResult End

    // firebaseAuthWithGoogle
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.id!!)
        Log.d(TAG, acct.idToken.toString())

        //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    
                    Log.w(TAG, "firebaseAuthWithGoogle 성공", task.exception)

                    //toMainActivity(firebaseAuth?.currentUser)
                } else {
                    Log.w(TAG, "firebaseAuthWithGoogle 실패", task.exception)
                    Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }// firebaseAuthWithGoogle END
}
