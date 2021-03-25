package com.recipe.android.recipeapp.src.setting.`interface`

import com.recipe.android.recipeapp.src.setting.models.DeleteIdResponse
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.Path

interface SettingRetrofitInterface {
    @DELETE("/users/{userIdx}")
    fun deleteId(
        @Path("userIdx") userIdx: Int
    ): Call<DeleteIdResponse>
}