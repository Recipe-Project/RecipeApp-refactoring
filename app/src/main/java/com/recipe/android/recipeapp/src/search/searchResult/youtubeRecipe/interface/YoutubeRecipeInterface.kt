 package com.recipe.android.recipeapp.src.search.searchResult.youtubeRecipe.`interface`

import com.recipe.android.recipeapp.src.search.searchResult.blogRecipe.models.BlogRecipeResponse
import com.recipe.android.recipeapp.src.search.searchResult.blogRecipe.models.BlogRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.searchResult.blogRecipe.models.BlogRecipeScrapResponse
import com.recipe.android.recipeapp.src.search.searchResult.youtubeRecipe.models.YoutubeRecipeResponse
import com.recipe.android.recipeapp.src.search.searchResult.youtubeRecipe.models.YoutubeRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.searchResult.youtubeRecipe.models.YoutubeRecipeScrapResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface YoutubeRecipeInterface {
    @GET("/youtube/v3/search")
    fun getYoutubeRecipe(
        @Query("part") part : String,
        @Query("type") type : String,
        @Query("maxResults") maxResults : Int,
        @Query("key") key : String,
        @Query("q") q : String,
        @Query("pageToken") pageToken : String
    ):Call<YoutubeRecipeResponse>

    @POST("/scraps/youtube")
    fun postAddingScrap(@Body params : YoutubeRecipeScrapRequest) : Call<YoutubeRecipeScrapResponse>
}