package com.recipe.android.recipeapp.src.signIn.`interface`

import com.recipe.android.recipeapp.src.signIn.models.SignInResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface SignnInRetrofitInterface {
    @POST("/users/kakao-login")
    fun postKaKaoLogin(
        @Header("KAKAO-ACCESS-TOKEN") token: String
    ): Call<SignInResponse>

    @POST("/users/naver-login")
    fun postNaverLogin(
        @Header("NAVER-ACCESS-TOKEN") token: String
    ): Call<SignInResponse>

    @POST("/users/google-login")
    fun postGoogleLogin(
        @Header("GOOGLE-ACCESS-TOKEN") token: String
    ): Call<SignInResponse>
}