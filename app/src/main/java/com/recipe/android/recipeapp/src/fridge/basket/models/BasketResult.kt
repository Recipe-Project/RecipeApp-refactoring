package com.recipe.android.recipeapp.src.fridge.basket.models


import com.google.gson.annotations.SerializedName
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient

data class BasketResult(
    @SerializedName("ingredientCount")
    val ingredientCount: Int,
    @SerializedName("ingredientList")
    val ingredientList: List<Ingredient>
)