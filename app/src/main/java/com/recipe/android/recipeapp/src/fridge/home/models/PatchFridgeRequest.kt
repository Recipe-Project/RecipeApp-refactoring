package com.recipe.android.recipeapp.src.fridge.home.models

import com.google.gson.annotations.SerializedName

data class PatchFridgeRequest(
    @SerializedName("patchFridgeList")
    val patchFridgeList : ArrayList<PatchFridgeObject>
)

data class PatchFridgeObject(
    @SerializedName("ingredientName")
    val ingredientName : String,
    @SerializedName("expiredAt")
    val expiredAt : String,
    @SerializedName("storageMethod")
    val storageMethod : String,
    @SerializedName("count")
    val count : Int
)
