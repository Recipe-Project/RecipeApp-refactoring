package com.recipe.android.recipeapp.src.receipt.models

import com.google.gson.annotations.SerializedName

data class PostNewReceiptRequest(
    @SerializedName("title")
    val title : String,
    @SerializedName("receiptDate")
    val receiptDate : String,
    @SerializedName("buyList")
    val buyList : ArrayList<BuyItem>
)

data class BuyItem(
    @SerializedName("buyName")
    val buyName : String
)
