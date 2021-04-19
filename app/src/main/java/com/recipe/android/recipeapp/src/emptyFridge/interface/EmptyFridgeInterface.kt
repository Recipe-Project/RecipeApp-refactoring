package com.recipe.android.recipeapp.src.emptyFridge.`interface`

import com.recipe.android.recipeapp.src.emptyFridge.models.EmptyFridgeResponse
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResponse
import retrofit2.Call
import retrofit2.http.GET

interface EmptyFridgeInterface {
    @GET("/fridges/recipe")
    fun getEmptyFridge() : Call<EmptyFridgeResponse>
}