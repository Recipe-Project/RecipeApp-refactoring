package com.recipe.android.recipeapp.src.myPage.myRecipe.`interface`

import com.recipe.android.recipeapp.src.myPage.myRecipe.models.MyRecipeResponse

interface MyRecipeActivityView {
    fun onGetMyRecipeSuccess(response: MyRecipeResponse)
    fun onGetMyRecipeFailure(message: String)
}