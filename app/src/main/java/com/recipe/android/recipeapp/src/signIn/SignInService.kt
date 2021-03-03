package com.recipe.android.recipeapp.src.signIn

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.signIn.`interface`.SignInActivityView
import com.recipe.android.recipeapp.src.signIn.`interface`.SignnInRetrofitInterface
import com.recipe.android.recipeapp.src.signIn.models.SignInResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInService(val view: SignInActivityView) {

    val TAG = "SignInService"

    // 카카오 로그인 API
    fun postKaKaoLogin(kakaoAccessToken: String) {
        val signnInRetrofitInterface =
            ApplicationClass.sRetrofit.create(SignnInRetrofitInterface::class.java)
        signnInRetrofitInterface.postKaKaoLogin(kakaoAccessToken)
            .enqueue(object : Callback<SignInResponse> {
                override fun onResponse(
                    call: Call<SignInResponse>,
                    response: Response<SignInResponse>
                ) {
                    Log.d(TAG, "SignInService - onResponse() : 카카오 로그인 API 호출 성공")
                    if (response.body() == null) {
                        view.onPostSignInFailure("response is null")
                    } else {
                        view.onPostSignInSuccess(response.body() as SignInResponse)
                    }
                }

                override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                    Log.d(TAG, "SignInService - onFailure() : 카카오 로그인 API 호출 실패")
                    view.onPostSignInFailure(t.message ?: "통신오류")
                }

            })
    }

    // 네이버 로그인 API
    fun postNaverLogin(accessToken: String) {
        val signnInRetrofitInterface =
            ApplicationClass.sRetrofit.create(SignnInRetrofitInterface::class.java)
        signnInRetrofitInterface.postNaverLogin(accessToken)
            .enqueue(object : Callback<SignInResponse> {
                override fun onResponse(
                    call: Call<SignInResponse>,
                    response: Response<SignInResponse>
                ) {
                    Log.d(TAG, "SignInService - onResponse() : 네이버 로그인 API 호출 성공")
                    if (response.body() == null) {
                        view.onPostSignInFailure("response is null")
                    } else {
                        view.onPostSignInSuccess(response.body() as SignInResponse)
                    }
                }

                override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                    Log.d(TAG, "SignInService - onFailure() : 네이버 로그인 API 호출 실패")
                    view.onPostSignInFailure(t.message ?: "통신오류")
                }

            })
    }
}