package com.recipe.android.recipeapp.src.fridge.pickIngredient.models


import com.google.gson.annotations.SerializedName

data class Ingredient(
    @SerializedName("ingredientIcon")
    val ingredientIcon: Any?,
    @SerializedName("ingredientIdx")
    val ingredientIdx: Int,
    @SerializedName("ingredientName")
    val ingredientName: String
)