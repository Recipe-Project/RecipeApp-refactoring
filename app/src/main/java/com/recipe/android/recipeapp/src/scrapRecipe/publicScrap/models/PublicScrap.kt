package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models


import com.google.gson.annotations.SerializedName

data class PublicScrap(
    @SerializedName("content")
    val content: String,
    @SerializedName("recipeId")
    val recipeId: Int,
    @SerializedName("scrapCount")
    val scrapCount: Int,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String
)