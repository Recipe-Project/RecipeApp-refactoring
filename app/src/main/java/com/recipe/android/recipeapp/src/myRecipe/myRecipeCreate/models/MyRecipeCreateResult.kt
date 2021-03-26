package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models


import com.google.gson.annotations.SerializedName

data class MyRecipeCreateResult(
    @SerializedName("content")
    val content: String,
    @SerializedName("ingredientList")
    val ingredientList: List<Int>,
    @SerializedName("myRecipeIdx")
    val myRecipeIdx: Int?,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("title")
    val title: String
)