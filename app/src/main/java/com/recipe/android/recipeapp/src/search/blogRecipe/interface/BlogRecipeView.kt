package com.recipe.android.recipeapp.src.search.blogRecipe.`interface`

import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeResponse
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeScrapResponse

interface BlogRecipeView {

    fun onGetBlogRecipeSuccess(response: BlogRecipeResponse)
    fun onGetBlogRecipeFailure(message: String)


    fun onPostBlogRecipeScrapSuccess(response: BlogRecipeScrapResponse)
    fun onPostBlogRecipeScrapFailure(message: String)
}