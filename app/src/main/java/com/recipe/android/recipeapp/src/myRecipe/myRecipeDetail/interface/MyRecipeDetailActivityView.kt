package com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.`interface`

import com.google.gson.JsonElement
import com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.models.MyRecipeDeleteResponse
import com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.models.MyRecipeDetailResponse

interface MyRecipeDetailActivityView {
    fun onGetMyRecipeDetailSuccess(response: MyRecipeDetailResponse)
    fun onGetMyRecipeDetailFailure(message: String)

    fun onDeleteMyRecipeSuccess(response: MyRecipeDeleteResponse)
}