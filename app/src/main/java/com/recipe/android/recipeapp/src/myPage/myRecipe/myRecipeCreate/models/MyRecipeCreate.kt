package com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeCreate.models

data class MyRecipeCreate(
    val thumbnail: String?,
    val title: String,
    val content: String,
    val ingredientList: List<Int>
)