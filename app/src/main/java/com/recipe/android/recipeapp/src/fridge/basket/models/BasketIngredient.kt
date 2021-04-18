package com.recipe.android.recipeapp.src.fridge.basket.models

import com.google.gson.annotations.SerializedName

data class BasketIngredient(
    @SerializedName("ingredientIcon")
    val ingredientIcon: Any?,
    @SerializedName("ingredientIdx")
    val ingredientIdx: Int,
    @SerializedName("ingredientName")
    val ingredientName: String,
    @SerializedName("ingredientCategoryIdx")
    val ingredientCategoryIdx: Int
)
