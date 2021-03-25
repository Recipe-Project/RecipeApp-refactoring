package com.recipe.android.recipeapp.src.search.`interface`

import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchKeywordInterface {
    @GET("/recipes")
    fun getPublicRecipe(
        @Query("keyword") keyword : String
    ): Call<PublicRecipeResponse>
}