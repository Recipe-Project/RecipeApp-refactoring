package com.recipe.android.recipeapp.src.splash

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.signIn.models.SignInResponse
import com.recipe.android.recipeapp.src.splash.`interface`.SplashActivityView
import com.recipe.android.recipeapp.src.splash.`interface`.SplashRetrofitInterface
import com.recipe.android.recipeapp.src.splash.models.AutoLoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashSerivce(val view: SplashActivityView) {

    val TAG = "SplashSerivce"

    fun postAutoLogin(){
        val splashRetrofitInterface = ApplicationClass.sRetrofit.create(SplashRetrofitInterface::class.java)
        splashRetrofitInterface.postAutoLogin().enqueue(object : Callback<AutoLoginResponse> {
            override fun onResponse(
                call: Call<AutoLoginResponse>,
                response: Response<AutoLoginResponse>
            ) {
                Log.d(TAG, "SplashSerivce - onResponse() : 자동로그인 api 호출 성공")
                if (response.body() == null) {
                    view.onPostAutoLoginFailure("response is null")
                } else {
                    view.onPostAutoLoginSuccess(response.body() as AutoLoginResponse)
                }
            }

            override fun onFailure(call: Call<AutoLoginResponse>, t: Throwable) {
                Log.d(TAG, "SplashSerivce - onFailure() : 자동로그인 api 호출 실패")
                view.onPostAutoLoginFailure(t.message ?: "통신오류")
            }

        })
    }
}