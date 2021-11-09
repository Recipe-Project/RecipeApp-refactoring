package com.recipe.android.recipeapp.src.search.publicRe.repository

import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.common.SimpleRetrofitResponse
import com.recipe.android.recipeapp.src.search.publicRe.model.PublicRecipeDetailResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PublicRecipeDetailService {
    @GET("/recipes/{recipeIdx}")
    fun getPublicRecipeDetail(@Path("recipeIdx") recipeIdx : Int) : Call<PublicRecipeDetailResponse>

    @POST("/scraps/recipe")
    fun scrapRecipe(@Body param: HashMap<String, Any>): Call<SimpleRetrofitResponse>
}