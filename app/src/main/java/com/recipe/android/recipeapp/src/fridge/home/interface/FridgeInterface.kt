package com.recipe.android.recipeapp.src.fridge.home.`interface`


import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResponse
import retrofit2.Call
import retrofit2.http.GET

interface FridgeInterface {

    @GET("/fridges")
    fun getFridge() : Call<GetFridgeResponse>

}