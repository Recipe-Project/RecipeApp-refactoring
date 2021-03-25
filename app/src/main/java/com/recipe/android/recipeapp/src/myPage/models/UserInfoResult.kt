package com.recipe.android.recipeapp.src.myPage.models


import com.google.gson.annotations.SerializedName

data class UserInfoResult(
    @SerializedName("blogScrapCnt")
    val blogScrapCnt: Int,
    @SerializedName("myRecipeList")
    val myRecipeList: List<MyRecipe>,
    @SerializedName("myRecipeListSize")
    val myRecipeListSize: Int,
    @SerializedName("profilePhoto")
    val profilePhoto: String,
    @SerializedName("recipeScrapCnt")
    val recipeScrapCnt: Int,
    @SerializedName("userIdx")
    val userIdx: Int,
    @SerializedName("userName")
    val userName: String,
    @SerializedName("youtubeScrapCnt")
    val youtubeScrapCnt: Int
)