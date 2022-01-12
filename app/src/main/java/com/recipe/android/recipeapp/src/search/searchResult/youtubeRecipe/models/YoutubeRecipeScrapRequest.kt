package com.recipe.android.recipeapp.src.search.searchResult.youtubeRecipe.models

import com.google.gson.annotations.SerializedName

data class YoutubeRecipeScrapRequest(
    @SerializedName("youtubeId")
    val youtubeIdx : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("thumbnail")
    val thumbnail : String,
    @SerializedName("youtubeUrl")
    val youtubeUrl : String,
    @SerializedName("postDate")
    val postDate : String,
    @SerializedName("channelName")
    val channelName : String,
    @SerializedName("playTime")
    val playTime : String
)
