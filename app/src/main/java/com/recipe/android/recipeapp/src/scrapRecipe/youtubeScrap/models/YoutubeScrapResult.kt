package com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models


import com.google.gson.annotations.SerializedName

data class YoutubeScrapResult(
    @SerializedName("scrapYoutubeCount")
    val scrapYoutubeCount: Int,
    @SerializedName("scrapYoutubeList")
    val scrapYoutubeList: List<YoutubeScrap>?
)