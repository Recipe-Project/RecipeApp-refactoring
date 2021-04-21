package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models


import com.google.gson.annotations.SerializedName

data class MyRecipeCreateResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: MyRecipeCreateResult
)