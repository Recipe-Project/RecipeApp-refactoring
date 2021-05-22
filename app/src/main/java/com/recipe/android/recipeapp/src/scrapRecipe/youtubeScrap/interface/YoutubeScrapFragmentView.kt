package com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.`interface`

import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.PostYoutubeScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrapResponse

interface YoutubeScrapFragmentView {
    fun onGetYoutubeScrapSuccess(response: YoutubeScrapResponse)
    fun onGetYoutubeScrapFailure(message: String)

    fun onPostYoutubeScrapSuccess(response: PostYoutubeScrapResponse)
}