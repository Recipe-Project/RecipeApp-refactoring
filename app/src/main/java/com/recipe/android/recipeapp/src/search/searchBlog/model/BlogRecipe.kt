package com.recipe.android.recipeapp.src.search.searchBlog.model

import com.google.gson.annotations.SerializedName

data class BlogRecipeResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result : BlogRecipeResult
)

data class BlogRecipeResult(
    @SerializedName("total")
    val total : Int,
    @SerializedName("blogList")
    val blogList : List<BlogRecipe>
)

data class BlogRecipe(
    @SerializedName("title")
    val title : String,
    @SerializedName("blogUrl")
    val blogUrl : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("bloggerName")
    val blogName : String,
    @SerializedName("postDate")
    val postDate : String,
    @SerializedName("thumbnail")
    val thumbnail : String,
    @SerializedName("userScrapCnt")
    val userScrapCnt : Int,
    @SerializedName("userScrapYN")
    var userScrapYN : String
)
