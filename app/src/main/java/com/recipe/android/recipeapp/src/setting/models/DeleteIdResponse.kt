package com.recipe.android.recipeapp.src.setting.models


import com.google.gson.annotations.SerializedName

data class DeleteIdResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String
)