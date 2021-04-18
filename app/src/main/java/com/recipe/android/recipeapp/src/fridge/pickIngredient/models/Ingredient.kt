package com.recipe.android.recipeapp.src.fridge.pickIngredient.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    @SerializedName("ingredientIcon")
    val ingredientIcon: String?,
    @SerializedName("ingredientIdx")
    val ingredientIdx: Int,
    @SerializedName("ingredientName")
    val ingredientName: String
): Parcelable