package com.recipe.android.recipeapp.src.search.blogRecipe.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class BlogRecipeScrapRequest(
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
    val thumbnail : String
)
