package com.recipe.android.recipeapp.src.search.publicRecipe.`interface`

import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse

interface PublicRecipeView {
    fun onGetPublicRecipeSuccess(response: PublicRecipeResponse)
    fun onGetPublicRecipeFailure(message: String)
}