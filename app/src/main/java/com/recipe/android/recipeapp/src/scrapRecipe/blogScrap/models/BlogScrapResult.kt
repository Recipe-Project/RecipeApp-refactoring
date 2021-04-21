package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models


import com.google.gson.annotations.SerializedName

data class BlogScrapResult(
    @SerializedName("blogUrl")
    val blogUrl: String,
    @SerializedName("bloggerName")
    val bloggerName: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("postDate")
    val postDate: String,
    @SerializedName("scrapBlogIdx")
    val scrapBlogIdx: Int,
    @SerializedName("thumbnail")
    val thumbnail: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("userScrapCnt")
    val userScrapCnt: Int
)