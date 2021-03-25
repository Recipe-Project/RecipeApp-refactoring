package com.recipe.android.recipeapp.src.search.`interface`

import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse

interface SearchKeywordView {
    fun onGetPublicRecipeSuccess(response: PublicRecipeResponse)
    fun onGetPublicRecipeFailure(message: String)
}