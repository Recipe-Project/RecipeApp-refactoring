package com.recipe.android.recipeapp.src.myRecipe.myRecipeModify.`interface`

import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse

interface MyRecipeModifyActivityView {
    fun onPatchMyRecipeSuccess(response: MyRecipeCreateResponse)
    fun onPatchMyRecipeFailure(message: String)
}