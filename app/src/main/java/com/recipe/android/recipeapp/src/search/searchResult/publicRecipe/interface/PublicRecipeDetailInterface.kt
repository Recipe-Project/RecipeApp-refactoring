package com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.`interface`

import com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.models.PublicRecipeDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PublicRecipeDetailInterface {
    @GET("/recipes/{recipeIdx}")
    fun getPublicRecipeDetail(@Path("recipeIdx") recipeIdx : Int) : Call<PublicRecipeDetailResponse>
}