package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DirectIngredientList(
    val ingredientName: String,
    val ingredientIcon: String?
): Parcelable
