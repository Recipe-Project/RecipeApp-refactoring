package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models


import com.google.gson.annotations.SerializedName

data class BlogScrapResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<BlogScrapResult>?
)