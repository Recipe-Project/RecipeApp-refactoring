package com.recipe.android.recipeapp.src.fridge.home.`interface`


import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientRequest
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientResponse
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP

interface FridgeInterface {

    @GET("/fridges")
    fun getFridge() : Call<GetFridgeResponse>

    @HTTP(method = "DELETE", path = "/fridges/ingredient", hasBody = true)
    fun deleteIngredient(@Body params : DeleteIngredientRequest) : Call<DeleteIngredientResponse>

}