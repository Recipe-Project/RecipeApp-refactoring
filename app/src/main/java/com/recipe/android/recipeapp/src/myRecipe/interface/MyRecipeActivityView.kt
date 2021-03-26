package com.recipe.android.recipeapp.src.myRecipe.`interface`

import com.recipe.android.recipeapp.src.myRecipe.models.MyRecipeResponse

interface MyRecipeActivityView {
    fun onGetMyRecipeSuccess(response: MyRecipeResponse)
    fun onGetMyRecipeFailure(message: String)


}