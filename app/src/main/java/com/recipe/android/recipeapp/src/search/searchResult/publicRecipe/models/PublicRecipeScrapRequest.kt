package com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.models

import com.google.gson.annotations.SerializedName

data class PublicRecipeScrapRequest(
    @SerializedName("recipeId") val recipeId : Int
)
