package com.recipe.android.recipeapp.src.fridge.receipt.`interface`

import com.recipe.android.recipeapp.src.fridge.receipt.models.PostReceiptIngredientResponse

interface ReceiptIngredientView {

    fun onPostReceiptIngredientSuccess(response : PostReceiptIngredientResponse)
    fun onPostReceiptIngredientFailure(message : String)
}