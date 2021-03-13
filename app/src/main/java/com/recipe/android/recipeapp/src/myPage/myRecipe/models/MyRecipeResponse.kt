package com.recipe.android.recipeapp.src.myPage.myRecipe.models


import com.google.gson.annotations.SerializedName

data class MyRecipeResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: List<MyRecipeResult>
)