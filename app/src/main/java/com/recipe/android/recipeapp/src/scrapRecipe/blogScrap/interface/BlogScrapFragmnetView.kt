package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.`interface`

import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResponse

interface BlogScrapFragmnetView {
    fun onGetBlogScrapSuccess(response: BlogScrapResponse)
    fun onGetBlogScrapFailure(message: String)
}