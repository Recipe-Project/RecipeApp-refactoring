package com.recipe.android.recipeapp.src.fridge.home.`interface`


import com.recipe.android.recipeapp.src.fridge.home.models.*
import retrofit2.Call
import retrofit2.http.*

interface FridgeInterface {

    @GET("/fridges")
    fun getFridge() : Call<GetFridgeResponse>

    @HTTP(method = "DELETE", path = "/fridges/ingredient", hasBody = true)
    fun deleteIngredient(@Body params : DeleteIngredientRequest) : Call<DeleteIngredientResponse>

    @PATCH("/fridges/ingredient")
    fun patchFridge(@Body params: PatchFridgeRequest) : Call<PatchFridgeResponse>

}