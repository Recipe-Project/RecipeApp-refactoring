package com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models


import com.google.gson.annotations.SerializedName

data class YoutubeScrap(
    @SerializedName("channelName")
    val channelName: String,
    @SerializedName("heartCount")
    val heartCount: Int,
    @SerializedName("postDate")
    val postDate: String,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("userIdx")
    val userIdx: Int,
    @SerializedName("youtubeIdx")
    val youtubeIdx: Int,
    @SerializedName("youtubeUrl")
    val youtubeUrl: String
)