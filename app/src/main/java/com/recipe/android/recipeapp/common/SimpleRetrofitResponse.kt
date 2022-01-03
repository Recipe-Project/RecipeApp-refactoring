package com.recipe.android.recipeapp.common

import com.google.gson.annotations.SerializedName

data class SimpleRetrofitResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: Any?
)
