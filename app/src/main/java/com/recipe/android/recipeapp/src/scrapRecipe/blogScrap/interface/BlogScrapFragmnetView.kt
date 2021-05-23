package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.`interface`

import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResponse

interface BlogScrapFragmnetView {
    fun onGetBlogScrapSuccess(response: BlogScrapResponse)
    fun onBlogScrapFailure(message: String)

    fun onPostBlogScrapSuccess(response: SimpleResponse)
}