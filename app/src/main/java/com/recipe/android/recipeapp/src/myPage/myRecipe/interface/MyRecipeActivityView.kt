package com.recipe.android.recipeapp.src.myPage.myRecipe.`interface`

import com.recipe.android.recipeapp.src.myPage.myRecipe.models.MyRecipeResponse
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse

interface MyRecipeActivityView {
    fun onGetMyRecipeSuccess(response: MyRecipeResponse)
    fun onGetMyRecipeFailure(message: String)


}