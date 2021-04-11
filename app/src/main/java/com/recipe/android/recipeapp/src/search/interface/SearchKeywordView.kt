package com.recipe.android.recipeapp.src.search.`interface`

import com.recipe.android.recipeapp.src.search.models.PopularKeywordResponse
import com.recipe.android.recipeapp.src.search.models.PostKeywordResponse
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse

interface SearchKeywordView {
    fun onGetPublicRecipeSuccess(response: PublicRecipeResponse)
    fun onGetPublicRecipeFailure(message: String)

    fun onGetPopularKeywordSuccess(response: PopularKeywordResponse)
    fun onGetPopularKeywordFailure(message: String)

    fun onPostKeywordSuccess(response: PostKeywordResponse)
    fun onPostKeywordFailure(message: String)
}