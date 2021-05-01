package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`

import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResponse
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse

interface MyRecipeCreateActivityView {
    fun onPostMyRecipeCreateSuccess(response: MyRecipeCreateResponse)
    fun onMyRecipeCreateFailure(message: String)

    fun onGetIngredientMyRecipeSuccess(response: IngredientResponse)

    fun selectAddDirect()
    fun selectPickDirect()

    fun pickItem(ingredient: Ingredient)
    fun removePickItem(ingredient: Int)

    fun pickBtnSaveClick(pickIngredientsMyRecipe: ArrayList<Ingredient>)
}