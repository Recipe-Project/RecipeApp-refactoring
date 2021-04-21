package com.recipe.android.recipeapp.src.fridge.pickIngredient.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class IngredientResult(
    @SerializedName("fridgeBasketCount")
    val fridgeBasketCount: Long,
    @SerializedName("ingredients")
    val ingredients: List<CategoryIngrediets>
): Parcelable