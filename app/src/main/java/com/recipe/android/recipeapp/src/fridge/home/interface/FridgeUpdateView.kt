package com.recipe.android.recipeapp.src.fridge.home.`interface`

import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientResponse
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResponse

interface FridgeUpdateView {
    fun onDeleteIngredientSuccess(response : DeleteIngredientResponse)
    fun onDeleteIngredientFailure(message : String)
}