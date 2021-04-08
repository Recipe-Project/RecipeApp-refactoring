package com.recipe.android.recipeapp.src.fridge.AddDirect.models


import com.google.gson.annotations.SerializedName

data class IngredientResult(
    @SerializedName("ingredientCategoryIdx")
    val ingredientCategoryIdx: Int,
    @SerializedName("ingredientCategoryName")
    val ingredientCategoryName: String,
    @SerializedName("ingredientList")
    val ingredientList: List<Ingredient>
)