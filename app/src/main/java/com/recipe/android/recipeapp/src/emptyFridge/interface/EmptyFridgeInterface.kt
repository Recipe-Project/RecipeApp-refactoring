package com.recipe.android.recipeapp.src.emptyFridge.`interface`

import com.recipe.android.recipeapp.src.emptyFridge.models.EmptyFridgeResponse
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EmptyFridgeInterface {
    @GET("/fridges/recipe")
    fun getEmptyFridge(
        @Query("start") start : Int,
        @Query("display") display : Int
    ) : Call<EmptyFridgeResponse>
}