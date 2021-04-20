package com.recipe.android.recipeapp.src.search.`interface`

import com.recipe.android.recipeapp.src.search.models.PopularKeywordResponse
import com.recipe.android.recipeapp.src.search.models.PostKeywordResponse
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchKeywordInterface {

    @GET("/recipes/best-keyword")
    fun getPopularKeyword() : Call<PopularKeywordResponse>

    @POST("/recipes")
    fun postKeyword(
        @Query("keyword") keyword : String
    ): Call<PostKeywordResponse>
}