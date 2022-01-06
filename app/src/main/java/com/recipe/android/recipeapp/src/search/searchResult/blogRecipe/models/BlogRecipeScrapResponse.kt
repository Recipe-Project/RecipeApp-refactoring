package com.recipe.android.recipeapp.src.search.searchResult.blogRecipe.models

import com.google.gson.annotations.SerializedName

data class BlogRecipeScrapResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)
