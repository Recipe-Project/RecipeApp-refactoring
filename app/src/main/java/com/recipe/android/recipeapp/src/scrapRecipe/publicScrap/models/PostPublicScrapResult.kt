package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models


import com.google.gson.annotations.SerializedName

data class PostPublicScrapResult(
    @SerializedName("recipeId")
    val recipeId: Int,
    @SerializedName("userIdx")
    val userIdx: Int
)