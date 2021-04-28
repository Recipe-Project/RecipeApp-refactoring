package com.recipe.android.recipeapp.src.myPage.models


import com.google.gson.annotations.SerializedName

data class ModifyUserInfoResult(
    @SerializedName("profilePhoto")
    val profilePhoto: String,
    @SerializedName("socialId")
    val socialId: String,
    @SerializedName("userIdx")
    val userIdx: Int,
    @SerializedName("userName")
    val userName: String
)