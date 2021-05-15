package com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.models


import com.google.gson.annotations.SerializedName
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.DirectIngredientList

data class MyRecipeDetailResult(
    @SerializedName("content")
    val content: String,
    @SerializedName("ingredientList")
    val ingredientList: List<DirectIngredientList>,
    @SerializedName("thumbnail")
    val thumbnail: Any?,
    @SerializedName("title")
    val title: String
)