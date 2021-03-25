package com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeCreate.`interface`

import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse

interface MyRecipeCreateActivityView {
    fun onPostMyRecipeCreateSuccess(response: MyRecipeCreateResponse)
    fun onPostMyRecipeCreateFailure(message: String)
}