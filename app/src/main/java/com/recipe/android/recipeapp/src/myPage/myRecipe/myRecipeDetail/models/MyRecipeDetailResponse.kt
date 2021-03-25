package com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeDetail.models


import com.google.gson.annotations.SerializedName

data class MyRecipeDetailResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: MyRecipeDetailResult
)