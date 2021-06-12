package com.recipe.android.recipeapp.src.fridge.pickIngredient.models


import com.google.gson.annotations.SerializedName

data class GetBasketCntResult(
    @SerializedName("fridgesBasketCount")
    val fridgesBasketCount: Int
)