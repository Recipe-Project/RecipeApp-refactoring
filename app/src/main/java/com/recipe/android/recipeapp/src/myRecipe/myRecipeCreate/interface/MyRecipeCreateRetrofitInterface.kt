package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`

import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResponse
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreate
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MyRecipeCreateRetrofitInterface {

    // 나만의 레시피 생성
    @POST("/my-recipes")
    fun postMyRecipeCreate(
        @Body param: HashMap<String, Any>
    ): Call<MyRecipeCreateResponse>

    // 재료조회
    @GET("/ingredients")
    fun getIngredients(
        @Query("keyword") keyword: String
    ): Call<IngredientResponse>
}