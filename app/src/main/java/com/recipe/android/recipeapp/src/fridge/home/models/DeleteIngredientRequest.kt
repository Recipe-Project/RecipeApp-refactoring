package com.recipe.android.recipeapp.src.fridge.home.models

import com.google.gson.annotations.SerializedName

data class DeleteIngredientRequest(
    @SerializedName("ingredientName")
    val ingredientName : String
)
