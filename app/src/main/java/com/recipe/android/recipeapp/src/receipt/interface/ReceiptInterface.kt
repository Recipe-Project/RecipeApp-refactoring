package com.recipe.android.recipeapp.src.receipt.`interface`

import com.recipe.android.recipeapp.src.receipt.models.GetAllReceiptResponse
import com.recipe.android.recipeapp.src.receipt.models.PostNewReceiptRequest
import com.recipe.android.recipeapp.src.receipt.models.PostNewReceiptResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ReceiptInterface {
    @POST("/receipts")
    fun postNewReceipt(@Body params : PostNewReceiptRequest) : Call<PostNewReceiptResponse>

    @GET("/receipts")
    fun getAllReceipt() : Call<GetAllReceiptResponse>
}