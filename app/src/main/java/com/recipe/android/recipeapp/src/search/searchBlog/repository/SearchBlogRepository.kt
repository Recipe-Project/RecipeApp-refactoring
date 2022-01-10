package com.recipe.android.recipeapp.src.search.searchBlog.repository

import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.search.searchBlog.api.SearchBlogService
import com.recipe.android.recipeapp.src.search.searchBlog.model.BlogRecipeResult
import retrofit2.awaitResponse

class SearchBlogRepository {
    var totalCnt = 0

    suspend fun getBlogRecipe(keyword: String, display: Int, start: Int): BlogRecipeResult? {
        val response = ApplicationClass.sRetrofit.create(SearchBlogService::class.java)
            .getBlogRecipe(keyword, display, start)
            .awaitResponse()
            .body()
        return response?.result
    }
}