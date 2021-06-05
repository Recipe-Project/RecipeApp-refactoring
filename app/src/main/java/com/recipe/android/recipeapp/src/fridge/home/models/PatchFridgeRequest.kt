package com.recipe.android.recipeapp.src.fridge.home.models

import com.google.gson.annotations.SerializedName

data class PatchFridgeRequest(
    @SerializedName("patchFridgeList")
    val patchFridgeList : MutableList<PatchFridgeObject>
)

data class PatchFridgeObject(
    @SerializedName("ingredientName")
    var ingredientName : String,
    @SerializedName("expiredAt")
    var expiredAt : String?,
    @SerializedName("storageMethod")
    var storageMethod : String,
    @SerializedName("count")
    var count : Int
)
