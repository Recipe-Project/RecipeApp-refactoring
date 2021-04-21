package com.recipe.android.recipeapp.src.search.publicRecipe.`interface`

import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapResponse

interface PublicRecipeScrapView {
    fun onPostPublicRecipeScrapSuccess(response: PublicRecipeScrapResponse)
    fun onPostPublicRecipeScrapFailure(message: String)
}