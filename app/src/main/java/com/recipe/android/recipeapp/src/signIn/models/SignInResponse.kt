package com.recipe.android.recipeapp.src.signIn.models


import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: SignInResult
)