package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.`interface`

import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrap
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrapResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PublicScrapRetrofitInterface {
    @GET("/scraps/recipe")
    fun getPublicScrap(
        @Query("sort") sort: Int?
    ): Call<PublicScrapResponse>
}