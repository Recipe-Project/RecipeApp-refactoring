package com.recipe.android.recipeapp.src.fridge.basket.models

import com.google.gson.annotations.SerializedName

data class BasketIngredient(
    @SerializedName("ingredientIcon")
    var ingredientIcon: Any?,
    @SerializedName("ingredientIdx")
    var ingredientIdx: Int,
    @SerializedName("ingredientName")
    var ingredientName: String,
    @SerializedName("ingredientCategoryIdx")
    var ingredientCategoryIdx: Int,
    @SerializedName("ingredientCnt")
    var ingredientCnt: Int,
    @SerializedName("storageMethod")
    var storageMethod: String,
    @SerializedName("expiredAt")
    var expiredAt: String?
)
