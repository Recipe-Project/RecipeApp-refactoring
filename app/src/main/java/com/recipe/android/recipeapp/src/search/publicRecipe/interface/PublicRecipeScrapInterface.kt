package com.recipe.android.recipeapp.src.search.publicRecipe.`interface`

import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface PublicRecipeScrapInterface {
    @POST("/scraps/recipe")
    fun postAddingScrap(@Body params : PublicRecipeScrapRequest) : Call<PublicRecipeScrapResponse>
}