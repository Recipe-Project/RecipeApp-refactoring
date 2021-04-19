package com.recipe.android.recipeapp.src.search.models

import com.google.gson.annotations.SerializedName

data class PostKeywordResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)
