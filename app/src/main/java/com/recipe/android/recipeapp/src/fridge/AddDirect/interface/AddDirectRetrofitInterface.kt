package com.recipe.android.recipeapp.src.fridge.AddDirect.`interface`

import com.recipe.android.recipeapp.src.fridge.AddDirect.models.IngredientResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AddDirectRetrofitInterface {
    @GET("/ingredients")
    fun getIngredients(
        @Query("keyword") keyword: String
    ): Call<IngredientResponse>
}