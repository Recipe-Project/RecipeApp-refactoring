package com.recipe.android.recipeapp.src.myPage.`interface`

import com.recipe.android.recipeapp.src.myPage.models.UserInfoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MyPageRetrofitInterface {
    @GET("/users/{userIdx}")
    fun getUserInfo(
        @Path("userIdx") userIdx: Int
    ): Call<UserInfoResponse>
}