package com.recipe.android.recipeapp.src.search.searchBlog.api

import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.src.search.searchBlog.model.BlogRecipeResponse
import com.recipe.android.recipeapp.src.search.searchResult.blogRecipe.models.BlogRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.searchResult.blogRecipe.models.BlogRecipeScrapResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchBlogService {
    @GET("/recipes/blog")
    fun getBlogRecipe(
        @Query("keyword") keyword : String,
        @Query("display") display : Int,
        @Query("start") start : Int
    ): Call<BlogRecipeResponse>

    @POST("/scraps/blog")
    suspend fun postAddingScrap(@Body params : BlogRecipeScrapRequest) : SimpleResponse
}