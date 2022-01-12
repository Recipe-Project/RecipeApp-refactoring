package com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.models

import com.google.gson.annotations.SerializedName

data class PublicRecipeScrapResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: ScrapInfoResult
)

data class ScrapInfoResult(
    @SerializedName("recipeId") val recipeId : Int,
    @SerializedName("userIdx") val userIdx : Int
)
