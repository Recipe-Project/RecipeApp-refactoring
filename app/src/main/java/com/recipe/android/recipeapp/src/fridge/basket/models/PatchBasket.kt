package com.recipe.android.recipeapp.src.fridge.basket.models

data class PatchBasket(
    val ingredientName: String,
    val ingredientCnt: Int,
    val storageMethod: String,
    val expiredAt: String?
)
