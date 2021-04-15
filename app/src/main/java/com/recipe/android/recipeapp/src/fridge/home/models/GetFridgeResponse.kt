package com.recipe.android.recipeapp.src.fridge.home.models

import com.google.gson.annotations.SerializedName

data class GetFridgeResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result : ArrayList<GetFridgeResult>
)

data class GetFridgeResult(
    @SerializedName("ingredientCategoryIdx")
    val ingredientCategoryIdx : Int,
    @SerializedName("ingredientCategoryName")
    val ingredientCategoryName : String,
    @SerializedName("fridgeList")
    val fridgeList : ArrayList<FridgeItem>
)

data class FridgeItem(
    @SerializedName("ingredientName")
    val ingredientName : String,
    @SerializedName("ingredientIcon")
    val ingredientIcon : String,
    @SerializedName("expiredAt")
    val expiredAt : String,
    @SerializedName("storageMethod")
    val storageMethod : String,
    @SerializedName("count")
    val count : Int,
    @SerializedName("freshness")
    val freshness : Int
)
