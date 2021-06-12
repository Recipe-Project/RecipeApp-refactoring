package com.recipe.android.recipeapp.src.fridge.home.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class GetFridgeResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result : FridgeObject
)

data class FridgeObject (
    @SerializedName("fridgeBasketCount")
    val fridgeBasketCount : Int,
    @SerializedName("fridges")
    val fridges : ArrayList<GetFridgeResult>
)

@Parcelize
data class GetFridgeResult(
    @SerializedName("ingredientCategoryIdx")
    val ingredientCategoryIdx : Int,
    @SerializedName("ingredientCategoryName")
    val ingredientCategoryName : String,
    @SerializedName("ingredientList")
    val ingredientList : ArrayList<FridgeItem>
): Parcelable

@Parcelize
data class FridgeItem(
    @SerializedName("ingredientName")
    val ingredientName : String,
    @SerializedName("ingredientIcon")
    val ingredientIcon : String,
    @SerializedName("expiredAt")
    var expiredAt : String,
    @SerializedName("storageMethod")
    var storageMethod : String,
    @SerializedName("count")
    var count : Int,
    @SerializedName("freshness")
    val freshness : Int
): Parcelable
