package com.recipe.android.recipeapp.src.search.youtubeRecipe.`interface`

import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeResponse
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeScrapResponse
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeResponse
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeScrapResponse

interface YoutubeRecipeView {

    fun onGetYoutubeRecipeSuccess(response: YoutubeRecipeResponse)
    fun onGetYoutubeRecipeFailure(message: String)

    fun onPostYoutubeRecipeScrapSuccess(response: YoutubeRecipeScrapResponse)
    fun onPostYoutubeRecipeScrapFailure(message: String)
}