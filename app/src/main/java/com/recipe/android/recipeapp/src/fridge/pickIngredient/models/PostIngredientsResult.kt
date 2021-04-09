package com.recipe.android.recipeapp.src.fridge.pickIngredient.models


import com.google.gson.annotations.SerializedName

data class PostIngredientsResult(
    @SerializedName("ingredientCategoryIdx")
    val ingredientCategoryIdx: Int,
    @SerializedName("ingredientIcon")
    val ingredientIcon: Any?,
    @SerializedName("ingredientName")
    val ingredientName: String
)