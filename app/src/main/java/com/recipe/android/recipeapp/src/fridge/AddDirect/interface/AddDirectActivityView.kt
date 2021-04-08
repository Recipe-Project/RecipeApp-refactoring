package com.recipe.android.recipeapp.src.fridge.AddDirect.`interface`

import com.recipe.android.recipeapp.src.fridge.AddDirect.models.IngredientResponse

interface AddDirectActivityView {
    fun onGetIngredientSuccess(response: IngredientResponse)
    fun addDirectFailure(message: String)
}