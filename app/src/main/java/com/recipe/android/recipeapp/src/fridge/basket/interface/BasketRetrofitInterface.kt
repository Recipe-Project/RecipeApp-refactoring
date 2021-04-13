package com.recipe.android.recipeapp.src.fridge.basket.`interface`

import com.recipe.android.recipeapp.src.fridge.basket.models.BasketResponse
import com.recipe.android.recipeapp.src.fridge.basket.models.PostFridge
import com.recipe.android.recipeapp.src.fridge.basket.models.PostFridgeResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BasketRetrofitInterface {
    @GET("/fridges/basket")
    fun getBasket(): Call<BasketResponse>

    @POST("/fridges")
    fun postFridge(
        @Body param: HashMap<String, Any>
    ): Call<PostFridgeResponse>
}