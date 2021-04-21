package com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.`interface`

import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrapResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeScrapRetrofitInterface {
    @GET("/scraps/youtube")
    fun getYoutubeScrap(
        @Query("sort") sort: Int?
    ): Call<YoutubeScrapResponse>
}