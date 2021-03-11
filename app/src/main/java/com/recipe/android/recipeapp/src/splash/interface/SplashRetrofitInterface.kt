package com.recipe.android.recipeapp.src.splash.`interface`

import com.recipe.android.recipeapp.src.splash.models.AutoLoginResponse
import retrofit2.Call
import retrofit2.http.POST

interface SplashRetrofitInterface {
    @POST("/users/auto-login")
    fun postAutoLogin(): Call<AutoLoginResponse>
}