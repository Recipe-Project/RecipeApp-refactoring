package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`

import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreate
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface MyRecipeCreateRetrofitInterface {

    // 나만의 레시피 생성
    @POST("/my-recipes")
    fun postMyRecipeCreate(
        @Body param: MyRecipeCreate
    ): Call<MyRecipeCreateResponse>
}