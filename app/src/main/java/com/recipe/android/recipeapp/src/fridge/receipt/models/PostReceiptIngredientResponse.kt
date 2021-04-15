package com.recipe.android.recipeapp.src.fridge.receipt.models

import com.google.gson.annotations.SerializedName

data class PostReceiptIngredientResponse (
    @SerializedName("isSuccess")
    val isSuccess: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("result")
    val result : ArrayList<PostReceiptIngredientResult>
)

data class PostReceiptIngredientResult(
    @SerializedName("ingredientIdx")
    val ingredientIdx : Int,
    @SerializedName("ingredientName")
    val ingredientName : String,
    @SerializedName("ingredientIcon")
    val ingredientIcon : String,
    @SerializedName("ingredientCategoryIdx")
    val ingredientCategoryIdx : Int
)