package com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.`interface`

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.models.MyRecipeDeleteResponse
import com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.models.MyRecipeDetailResponse
import org.json.JSONArray
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MyRecipeDetailRetrofitInterface {
    @GET("/my-recipes/{myRecipeIdx}")
    fun getMyRecipeDetail(
        @Path("myRecipeIdx") myRecipeIdx: Int
    ): Call<MyRecipeDetailResponse>

    // 나만의 레시피 삭제
    @DELETE("/my-recipes/{myRecipeIdx}")
    fun deleteMyRecipeDetail(
        @Path("myRecipeIdx") myRecipeIdx: Int
    ): Call<MyRecipeDeleteResponse>
}