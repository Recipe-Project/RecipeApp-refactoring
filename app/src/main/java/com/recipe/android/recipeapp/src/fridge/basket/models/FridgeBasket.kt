package com.recipe.android.recipeapp.src.fridge.basket.models


import com.google.gson.annotations.SerializedName

data class FridgeBasket(
    @SerializedName("count")
    val count: Int,
    @SerializedName("expiredAt")
    val expiredAt: String?,
    @SerializedName("ingredientCategoryIdx")
    val ingredientCategoryIdx: Int,
    @SerializedName("ingredientIcon")
    val ingredientIcon: Any?,
    @SerializedName("ingredientName")
    val ingredientName: String,
    @SerializedName("storageMethod")
    val storageMethod: String
)