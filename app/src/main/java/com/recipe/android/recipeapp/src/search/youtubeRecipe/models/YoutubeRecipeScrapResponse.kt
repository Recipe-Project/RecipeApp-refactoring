package com.recipe.android.recipeapp.src.search.youtubeRecipe.models

import com.google.gson.annotations.SerializedName

data class YoutubeRecipeScrapResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result : YoutubeRecipeScrapResult
)

data class YoutubeRecipeScrapResult(
    @SerializedName("userIdx")
    val userIdx : Int,
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