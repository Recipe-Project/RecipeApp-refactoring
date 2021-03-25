package com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeModify.`interface`

import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse

interface MyRecipeModifyActivityView {
    fun onPatchMyRecipeSuccess(response: MyRecipeCreateResponse)
    fun onPatchMyRecipeFailure(message: String)
}