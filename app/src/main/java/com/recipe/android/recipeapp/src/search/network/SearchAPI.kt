package com.recipe.android.recipeapp.src.search.network

import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.src.search.models.PopularKeywordResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchAPI {
    @GET("/recipes/best-keyword")
    suspend fun getPopularKeyword() : PopularKeywordResponse

    @POST("/recipes")
    suspend fun postKeyword(@Query("keyword") keyword : String): SimpleResponse
}