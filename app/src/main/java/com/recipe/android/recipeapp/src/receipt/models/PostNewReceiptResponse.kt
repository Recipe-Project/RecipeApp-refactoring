package com.recipe.android.recipeapp.src.receipt.models

import com.google.gson.annotations.SerializedName

data class PostNewReceiptResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String
)
