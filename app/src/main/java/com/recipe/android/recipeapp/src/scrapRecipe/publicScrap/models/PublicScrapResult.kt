package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models


import com.google.gson.annotations.SerializedName

data class PublicScrapResult(
    @SerializedName("scrapRecipeCount")
    val scrapRecipeCount: Int,
    @SerializedName("scrapRecipeList")
    val scrapRecipeList: List<PublicScrap>?
)