package com.recipe.android.recipeapp.src.emptyFridge.`interface`

import com.recipe.android.recipeapp.src.emptyFridge.models.EmptyFridgeResponse
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeScrapResponse

interface EmptyFridgeView {

    fun onGetEmptyFridgeSuccess(response : EmptyFridgeResponse)
    fun onGetEmptyFridgeFailure(message : String)

    fun getPublicRecipeDetail(id : Int)
    fun getBlogRecipe(keyword : String)
    fun getYoutubeRecipe(keyword : String)
}