package com.recipe.android.recipeapp.src.myRecipe.`interface`

import com.recipe.android.recipeapp.src.myRecipe.models.MyRecipeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyRecipeRetrofitInterface {

    @GET("/my-recipes")
    fun getMyRecipe(
        @Query("size") size: Int,
        @Query("page") page: Int
    ): Call<MyRecipeResponse>


}