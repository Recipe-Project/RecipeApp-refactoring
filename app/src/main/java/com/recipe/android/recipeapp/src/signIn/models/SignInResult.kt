package com.recipe.android.recipeapp.src.signIn.models


import com.google.gson.annotations.SerializedName

data class SignInResult(
    @SerializedName("jwt")
    val jwt: String,
    @SerializedName("userIdx")
    val userIdx: Int
)