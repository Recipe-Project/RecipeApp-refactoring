package com.recipe.android.recipeapp.src.search.publicRe.model

import com.google.gson.annotations.SerializedName

data class PublicRecipeDetailResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result: PublicRecipeDetailResult
)

data class PublicRecipeDetailResult(
    @SerializedName("recipeId")
    val recipeId: Int,
    @SerializedName("recipeName")
    val recipeName: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("cookingTime")
    val cookingTime: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("recipeIngredientList")
    val recipeIngredientList: ArrayList<PublicRecipeIngredient>,
    @SerializedName("recipeProcessList")
    val recipeProcessList: ArrayList<PublicRecipeProcess>,
    @SerializedName("userScrapYN")
    var userScrapYN: String,
    @SerializedName("userScrapCnt")
    val userScrapCnt: Int
)

data class PublicRecipeIngredient(
    @SerializedName("recipeIngredientIdx")
    val recipeIngredientIdx: Int,
    @SerializedName("recipeIngredientName")
    val recipeIngredientName: String,
    @SerializedName("recipeIngredientIcon")
    val recipeIngredientIcon : String,
    @SerializedName("recipeIngredientCpcty")
    val recipeIngredientCpcty: String,
    @SerializedName("inFridgeYN")
    val inFridgeYN : String
)

data class PublicRecipeProcess(
    @SerializedName("recipeProcessIdx")
    val recipeProcessIdx: Int,
    @SerializedName("recipeProcessNo")
    val recipeProcessNo: Int,
    @SerializedName("recipeProcessDc")
    val recipeProcessDc: String,
    @SerializedName("recipeProcessImg")
    val recipeProcessImg: String
)