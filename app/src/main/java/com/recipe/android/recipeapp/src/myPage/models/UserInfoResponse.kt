package com.recipe.android.recipeapp.src.myPage.models


import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: UserInfoResult
)