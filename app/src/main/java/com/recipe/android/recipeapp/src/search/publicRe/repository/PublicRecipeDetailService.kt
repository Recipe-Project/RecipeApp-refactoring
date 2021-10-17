package com.recipe.android.recipeapp.src.search.publicRe.repository

import com.recipe.android.recipeapp.src.search.publicRe.model.PublicRecipeDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PublicRecipeDetailService {
    @GET("/recipes/{recipeIdx}")
    fun getPublicRecipeDetail(@Path("recipeIdx") recipeIdx : Int) : Call<PublicRecipeDetailResponse>
}