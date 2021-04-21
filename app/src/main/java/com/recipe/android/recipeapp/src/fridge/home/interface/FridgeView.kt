package com.recipe.android.recipeapp.src.fridge.home.`interface`

import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResponse
import com.recipe.android.recipeapp.src.fridge.home.models.PatchFridgeResponse

interface FridgeView {
    fun onGetFridgeSuccess(response : GetFridgeResponse)
    fun onGetFridgeFailure(message : String)

    fun onPatchFridgeSuccess(response : PatchFridgeResponse)
    fun onPatchFridgeFailure(message : String)


}