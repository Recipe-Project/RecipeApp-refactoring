package com.recipe.android.recipeapp.src.myPage.myRecipe.models


import com.google.gson.annotations.SerializedName

data class MyRecipeResult(
    @SerializedName("content")
    val content: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("userRecipeIdx")
    val userRecipeIdx: Int
)