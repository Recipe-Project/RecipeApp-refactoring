package com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`

import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResponse
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.PostIngredientsResponse

interface PickIngredientActivityView {
    fun onGetIngredientSuccess(response: IngredientResponse)
    fun onPostIngredientSuccess(response: PostIngredientsResponse)

    fun pickItem(ingredient: Ingredient)
    fun removePickItem(ingredient: Int)

    fun addDirectFailure(message: String)

    fun removePickMyIngredients(ingredient: Ingredient)
}