package com.recipe.android.recipeapp.src.emptyFridge.models

import com.google.gson.annotations.SerializedName

data class EmptyFridgeResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result : ArrayList<EmptyFridgeResult>
)

data class EmptyFridgeResult(
    @SerializedName("recipeId")
    val recipeId: Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("content")
    val content : String,
    @SerializedName("thumbnail")
    val thumbnail : String,
    @SerializedName("cookingTime")
    val cookingTime : String,
    @SerializedName("scrapCount")
    val scrapCount : Long
)