package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.`interface`

import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BlogScrapRetrofitInterface {
    @GET("/scraps/blog")
    fun getBlogScrap(
        @Query("sort") sort: Int?
    ): Call<BlogScrapResponse>

    @POST("/scraps/blog")
    fun postBlogScrap(
        @Body params: HashMap<String, Any>
    ): Call<SimpleResponse>
}