package com.recipe.android.recipeapp.src.fridge.pickIngredient.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryIngrediets(
    @SerializedName("ingredientCategoryIdx")
    val ingredientCategoryIdx: Int,
    @SerializedName("ingredientCategoryName")
    val ingredientCategoryName: String,
    @SerializedName("ingredientList")
    val ingredientList: List<Ingredient>?
): Parcelable
