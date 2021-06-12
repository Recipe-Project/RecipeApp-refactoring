package com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`

import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.GetBasketCntResponse
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResponse
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.PostIngredientsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PickIngredientRetrofitInterface {

    // 재료조회
    @GET("/ingredients")
    fun getIngredients(
        @Query("keyword") keyword: String
    ): Call<IngredientResponse>

    // 재료선택으로 냉장고 바구니 담기
    @POST("/fridges/basket")
    fun postIngredients(
        @Body param: HashMap<String, Any>
    ): Call<PostIngredientsResponse>

    @GET("/fridges/basket/count")
    fun getBasketCnt(): Call<GetBasketCntResponse>
}