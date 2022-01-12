package com.recipe.android.recipeapp.src.search.network

import android.util.Log
import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.src.search.models.PopularKeywordResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchService @Inject constructor(
    private val api: SearchAPI
) {
    val TAG = "SearchService"

    suspend fun getPopularKeyword(): Flow<Result<PopularKeywordResponse>> {
        return flow {
            emit(Result.success(api.getPopularKeyword()))
        }.catch { exception ->
            emit(Result.failure(RuntimeException(exception)))
        }
    }

    suspend fun postKeyword(keyword : String): Flow<Result<SimpleResponse>> {
        return flow {
            emit(Result.success(api.postKeyword(keyword)))
        }.catch { exception ->
            emit(Result.failure(RuntimeException(exception)))
        }
//        val searchKeywordInterface = AplicationClass.sRetrofit.create(SearchKeywordInterface::class.java)
//        searchKeywordInterface.postKeyword(keyword).enqueue(object : Callback<PostKeywordResponse> {
//            override fun onResponse(call: Call<PostKeywordResponse>, response: Response<PostKeywordResponse>) {
//                Log.d(TAG, "SearchService - onResponse() : 레시피 검색어 저장 성공")
//                view.onPostKeywordSuccess(response.body() as PostKeywordResponse)
//            }
//
//            override fun onFailure(call: Call<PostKeywordResponse>, t: Throwable) {
//                view.onPostKeywordFailure(t.message ?: "통신오류")
//            }
//        })
    }



}