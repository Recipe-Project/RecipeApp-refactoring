package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models


import com.google.gson.annotations.SerializedName

data class PublicScrapResult(
    @SerializedName("scrapCount")
    val scrapRecipeCount: Int,
    @SerializedName("scrapList")
    val scrapRecipeList: List<PublicScrap>
)