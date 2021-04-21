package com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.models


import com.google.gson.annotations.SerializedName

data class MyRecipeDetailResult(
    @SerializedName("content")
    val content: String,
    @SerializedName("ingredientList")
    val ingredientList: List<Any>,
    @SerializedName("thumbnail")
    val thumbnail: Any?,
    @SerializedName("title")
    val title: String
)