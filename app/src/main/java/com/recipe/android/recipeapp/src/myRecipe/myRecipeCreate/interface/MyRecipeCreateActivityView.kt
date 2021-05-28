package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`

import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResponse
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse

interface MyRecipeCreateActivityView {
    fun onPostMyRecipeCreateSuccess(response: MyRecipeCreateResponse)
    fun onMyRecipeCreateFailure(message: String)

    fun onPatchMyRecipeSuccess(response: MyRecipeCreateResponse)
    fun onPatchMyRecipeFailure(message: String)

    fun onGetIngredientMyRecipeSuccess(response: IngredientResponse)

    fun selectAddDirect()
    fun selectPickDirect()

    fun pickItem(ingredient: Ingredient)
    fun removePickItem(ingredient: Int)

    fun cancelCreateRecipe()

    fun pickBtnSaveClick(pickIngredientsMyRecipe: ArrayList<Ingredient>)
}