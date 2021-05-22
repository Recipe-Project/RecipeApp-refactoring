package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.`interface`

import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PostPublicScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrapResponse

interface PublicScrapFragmentView {
    fun onGetPublicScrapSuccess(response: PublicScrapResponse)
    fun onGetPublicScrapFailure(message: String)

    fun onPostPublicScrapSuccess(postPublicScrapResponse: PostPublicScrapResponse)
}