package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models


import com.google.gson.annotations.SerializedName

data class PostPublicScrapResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: PostPublicScrapResult
)