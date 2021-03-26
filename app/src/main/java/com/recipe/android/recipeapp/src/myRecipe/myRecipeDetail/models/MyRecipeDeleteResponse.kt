package com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.models


import com.google.gson.annotations.SerializedName

data class MyRecipeDeleteResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String
)