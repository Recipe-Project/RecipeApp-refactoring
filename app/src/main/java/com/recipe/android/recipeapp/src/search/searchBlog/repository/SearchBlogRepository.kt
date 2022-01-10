package com.recipe.android.recipeapp.src.search.searchBlog.repository

import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.search.searchBlog.api.SearchBlogService
import com.recipe.android.recipeapp.src.search.searchBlog.model.BlogRecipe
import retrofit2.awaitResponse

class SearchBlogRepository {
    suspend fun getBlogRecipe(keyword: String, display: Int, start: Int): List<BlogRecipe>? {
        val response = ApplicationClass.sRetrofit.create(SearchBlogService::class.java)
            .getBlogRecipe(keyword, display, start)
            .awaitResponse()
            .body()

        return response?.result?.blogList
    }
}