package com.recipe.android.recipeapp.src.fridge.home.models

import com.google.gson.annotations.SerializedName

data class DeleteIngredientRequest(
    @SerializedName("ingredientList")
    val ingredientList : ArrayList<String>
)
