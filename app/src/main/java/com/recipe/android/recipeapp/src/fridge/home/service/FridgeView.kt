package com.recipe.android.recipeapp.src.fridge.home.service

import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResponse

interface FridgeView {
    fun onGetFridgeSuccess(response : GetFridgeResponse)
    fun onGetFridgeFailure(message : String)
}