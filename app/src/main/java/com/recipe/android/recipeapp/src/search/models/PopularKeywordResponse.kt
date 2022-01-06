package com.recipe.android.recipeapp.src.search.models

import com.google.gson.annotations.SerializedName

data class PopularKeywordResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result : List<PopularKeyword>
)

data class PopularKeyword(
    @SerializedName("bestKeyword")
    val bestKeyword : String
)
