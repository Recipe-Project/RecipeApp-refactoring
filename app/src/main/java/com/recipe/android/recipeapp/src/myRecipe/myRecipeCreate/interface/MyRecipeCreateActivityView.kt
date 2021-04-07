package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`

import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse

interface MyRecipeCreateActivityView {
    fun onPostMyRecipeCreateSuccess(response: MyRecipeCreateResponse)
    fun onPostMyRecipeCreateFailure(message: String)
}