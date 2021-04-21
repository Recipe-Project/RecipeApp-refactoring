package com.recipe.android.recipeapp.src.fridge.basket.models


import com.google.gson.annotations.SerializedName

data class PostFridge(
    @SerializedName("fridgeBasketList")
    val fridgeBasketList: List<FridgeBasket>
)