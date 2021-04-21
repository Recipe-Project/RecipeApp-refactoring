package com.recipe.android.recipeapp.src.search.models

import com.google.gson.annotations.SerializedName
import com.recipe.android.recipeapp.src.myPage.models.UserInfoResult

data class PublicRecipeResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: ArrayList<PublicRecipeResult>
)

data class PublicRecipeResult(
    @SerializedName("recipeId")
    val recipeId : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("description")
    val description : String,
    @SerializedName("thumbnail")
    val thumbnail : String,
    @SerializedName("userScrapCnt")
    val userScrapCnt : Int,
    @SerializedName("userScrapYN")
    var userScrapYN : String
)
