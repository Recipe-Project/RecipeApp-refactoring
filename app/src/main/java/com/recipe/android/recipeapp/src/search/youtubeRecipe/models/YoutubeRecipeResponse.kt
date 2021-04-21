package com.recipe.android.recipeapp.src.search.youtubeRecipe.models

import com.google.gson.annotations.SerializedName

data class YoutubeRecipeResponse(
    @SerializedName("kind")
    val kind : String,
    @SerializedName("etag")
    val etag : String,
    @SerializedName("nextPageToken")
    val nextPageToken : String,
    @SerializedName("regionCode")
    val regionCode : String,
    @SerializedName("pageInfo")
    val pageInfo : PageInfoResult,
    @SerializedName("items")
    val items : ArrayList<YoutubeRecipeResult>
)

data class PageInfoResult(
    @SerializedName("totalResults")
    val totalResults : Int,
    @SerializedName("resultsPerPage")
    val resultsPerPage : Int
)

data class YoutubeRecipeResult(
    @SerializedName("kind")
    val kind : String,
    @SerializedName("etag")
    val etag : String,
    @SerializedName("id")
    val id : YoutubeRecipeId,
    @SerializedName("snippet")
    val snippet : YoutubeRecipeSnippet
)

data class YoutubeRecipeId(
    @SerializedName("kind")
    val kind : String,
    @SerializedName("videoId")
    val videoId : String
)

data class YoutubeRecipeSnippet(
    @SerializedName("publishedAt")
    val publishedAt : String,
    @SerializedName("channelId")
    val channelId : String,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("thumbnails")
    val thumbnails : YoutubeRecipeThumbnails,
    @SerializedName("channelTitle")
    val channelTitle : String,
    @SerializedName("liveBroadcastContent")
    val liveBroadcastContent : String,
    @SerializedName("publishTime")
    val publishTime : String
)

data class YoutubeRecipeThumbnails(
    @SerializedName("default")
    val default : ThumbnailInfo,
    @SerializedName("medium")
    val medium : ThumbnailInfo,
    @SerializedName("high")
    val high : ThumbnailInfo
)

data class ThumbnailInfo(
    @SerializedName("url")
    val url : String,
    @SerializedName("width")
    val width : Int,
    @SerializedName("height")
    val height : Int
)


