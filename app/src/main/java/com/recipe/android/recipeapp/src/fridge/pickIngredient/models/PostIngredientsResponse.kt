package com.recipe.android.recipeapp.src.fridge.pickIngredient.models


import com.google.gson.annotations.SerializedName

data class PostIngredientsResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: PostIngredientsResult
)