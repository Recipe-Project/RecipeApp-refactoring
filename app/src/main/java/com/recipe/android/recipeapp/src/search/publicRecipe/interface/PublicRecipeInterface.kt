package com.recipe.android.recipeapp.src.search.publicRecipe.`interface`

import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PublicRecipeInterface {
    @GET("/recipes")
    fun getPublicRecipe(
        @Query("keyword") keyword : String
    ): Call<PublicRecipeResponse>
}