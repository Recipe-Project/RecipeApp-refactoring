package com.recipe.android.recipeapp.src.fridge.home.models

import com.google.gson.annotations.SerializedName

data class DeleteIngredientResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String
)
