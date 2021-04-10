package com.recipe.android.recipeapp.src.fridge.basket.models


import com.google.gson.annotations.SerializedName

data class BasketResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: BasketResult
)