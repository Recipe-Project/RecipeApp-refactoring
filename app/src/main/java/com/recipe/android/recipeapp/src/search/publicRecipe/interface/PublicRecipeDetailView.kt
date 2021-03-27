package com.recipe.android.recipeapp.src.search.publicRecipe.`interface`


import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeDetailResponse

interface PublicRecipeDetailView {
    fun onGetPublicRecipeDetailSuccess(response: PublicRecipeDetailResponse)
    fun onGetPublicRecipeDetailFailure(message: String)
}