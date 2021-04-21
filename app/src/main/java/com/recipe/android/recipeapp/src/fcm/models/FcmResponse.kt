package com.recipe.android.recipeapp.src.fcm.models


import com.google.gson.annotations.SerializedName

data class FcmResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String
)