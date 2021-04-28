package com.recipe.android.recipeapp.src.myPage.`interface`

import com.recipe.android.recipeapp.src.myPage.models.ModifyUserInfoResponse
import com.recipe.android.recipeapp.src.myPage.models.UserInfoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MyPageRetrofitInterface {
    @GET("/users/{userIdx}")
    fun getUserInfo(
        @Path("userIdx") userIdx: Int
    ): Call<UserInfoResponse>

    @PATCH("/users/{userIdx}")
    fun patchUserInfo(
        @Path("userIdx") userIdx: Int,
        @Body param: HashMap<String, Any>
    ): Call<ModifyUserInfoResponse>
}