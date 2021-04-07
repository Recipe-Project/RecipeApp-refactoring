package com.recipe.android.recipeapp.src.myRecipe.myRecipeModify.`interface`

import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreate
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MyRecipeModifyRetrofitInterface {

    @PATCH("/my-recipes/{myRecipeIdx}")
    fun patchMyRecipe(
        @Body param: MyRecipeCreate,
        @Path("myRecipeIdx") myRecipeIdx: Int
    ): Call<MyRecipeCreateResponse>
}