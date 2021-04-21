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
    val result : ArrayList<PopularKeywordResult>
)

data class PopularKeywordResult(
    @SerializedName("bestKeyword")
    val bestKeyword : String
)
