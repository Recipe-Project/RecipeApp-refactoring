package com.recipe.android.recipeapp.common


import com.google.gson.annotations.SerializedName

data class SimpleResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String
)