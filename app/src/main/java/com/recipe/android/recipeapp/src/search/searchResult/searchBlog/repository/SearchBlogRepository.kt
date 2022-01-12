package com.recipe.android.recipeapp.src.search.searchResult.searchBlog.repository

import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sRetrofit
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.api.SearchBlogService
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.model.BlogRecipeResult
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.model.BlogRecipeScrapRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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

    suspend fun scrap(params: BlogRecipeScrapRequest): Flow<Result<SimpleResponse>> {
        val response = sRetrofit.create(SearchBlogService::class.java)
            .postAddingScrap(params)
        return flow {
            emit(Result.success(response))
        }.catch { exception ->
            emit(Result.failure(RuntimeException(exception)))
        }
    }
}