package com.recipe.android.recipeapp.src.search.network

import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.src.search.models.PopularKeyword
import com.recipe.android.recipeapp.src.search.network.PopularKeywordMapper
import com.recipe.android.recipeapp.src.search.network.SearchService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val service: SearchService,
    private val mapper: PopularKeywordMapper
) {
    suspend fun getPopularKeyword(): Flow<Result<List<PopularKeyword>>> =
        service.getPopularKeyword().map {
            if (it.isSuccess)
                Result.success(mapper(it.getOrNull()!!))
            else
                Result.failure(it.exceptionOrNull()!!)
        }

    suspend fun postKeyword(keyword: String): Flow<Result<SimpleResponse>> =
        service.postKeyword(keyword).map {
            if (it.isSuccess)
                Result.success(it.getOrNull()!!)
            else
                Result.failure(it.exceptionOrNull()!!)
        }
}