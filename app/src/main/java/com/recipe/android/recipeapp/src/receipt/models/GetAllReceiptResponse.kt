package com.recipe.android.recipeapp.src.receipt.models

import com.google.gson.annotations.SerializedName

data class GetAllReceiptResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result : ArrayList<GetAllReceiptResult>
)

data class GetAllReceiptResult(
    @SerializedName("receiptIdx")
    val receiptIdx : Int,
    @SerializedName("userIdx")
    val userIdx : Int,
    @SerializedName("title")
    val title : String,
    @SerializedName("receiptDate")
    val receiptDate : String,
    @SerializedName("buyList")
    val buyList : ArrayList<BuyItemResult>
)

data class BuyItemResult(
    @SerializedName("buyIdx")
    val buyIdx : Int,
    @SerializedName("buyName")
    val buyName : String
)
