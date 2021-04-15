package com.recipe.android.recipeapp.src.fridge.receipt.`interface`

import com.google.gson.annotations.SerializedName
import com.recipe.android.recipeapp.src.fridge.receipt.models.PostReceiptIngredientResponse
import com.recipe.android.recipeapp.src.receipt.models.PostNewReceiptRequest
import com.recipe.android.recipeapp.src.receipt.models.PostNewReceiptResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class PostReceiptIngredientRequest(
    @SerializedName("receipt")
    val receipt : String
)

interface ReceiptIngredientInterface {

    @POST("/receipts/ingredient")
    fun postReceiptIngredient(@Body params : PostReceiptIngredientRequest) : Call<PostReceiptIngredientResponse>

}