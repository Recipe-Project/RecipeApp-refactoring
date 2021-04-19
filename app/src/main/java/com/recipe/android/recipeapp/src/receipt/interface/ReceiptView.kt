package com.recipe.android.recipeapp.src.receipt.`interface`

import com.recipe.android.recipeapp.src.receipt.models.GetAllReceiptResponse
import com.recipe.android.recipeapp.src.receipt.models.PostNewReceiptResponse

interface ReceiptView {
    fun onPostNewReceiptSuccess(response : PostNewReceiptResponse)
    fun onPostNewReceiptFailure(message : String)

    fun onGetAllReceiptSuccess(response : GetAllReceiptResponse)
    fun onGetAllReceiptFailure(message : String)
}