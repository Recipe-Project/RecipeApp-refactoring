package com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.`interface`

import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.PostYoutubeScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrapResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface YoutubeScrapRetrofitInterface {
    @GET("/scraps/youtube")
    fun getYoutubeScrap(
        @Query("sort") sort: Int?
    ): Call<YoutubeScrapResponse>

    @POST("/scraps/youtube")
    fun postYoutubeScrap(
        @Body params: HashMap<String, Any>
    ): Call<PostYoutubeScrapResponse>
}