package com.recipe.android.recipeapp.src.fridge.basket.`interface`

import com.bumptech.glide.load.resource.SimpleResource
import com.recipe.android.recipeapp.src.fridge.basket.models.BasketResponse
import com.recipe.android.recipeapp.src.fridge.basket.models.DeleteBasketResponse
import com.recipe.android.recipeapp.src.fridge.basket.models.PostFridge
import com.recipe.android.recipeapp.src.fridge.basket.models.PostFridgeResponse
import retrofit2.Call
import retrofit2.http.*

interface BasketRetrofitInterface {
    @GET("/fridges/basket")
    fun getBasket(): Call<BasketResponse>

    @POST("/fridges")
    fun postFridge(
        @Body param: HashMap<String, Any>
    ): Call<PostFridgeResponse>

    @DELETE("/fridges/basket")
    fun deleteBasket(
        @Query("ingredient") ingredient: String
    ): Call<DeleteBasketResponse>
}