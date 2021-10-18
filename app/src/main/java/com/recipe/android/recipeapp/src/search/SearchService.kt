package com.recipe.android.recipeapp.src.search

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.search.`interface`.SearchKeywordInterface
import com.recipe.android.recipeapp.src.search.models.PopularKeywordResponse
import com.recipe.android.recipeapp.src.search.models.PostKeywordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val view: SearchKeywordView) {
    val TAG = "SearchService"

    fun getPopularKeyword() {
        val searchKeywordInterface = ApplicationClass.sRetrofit.create(SearchKeywordInterface::class.java)
        searchKeywordInterface.getPopularKeyword().enqueue(object : Callback<PopularKeywordResponse> {
            override fun onResponse(call: Call<PopularKeywordResponse>, response: Response<PopularKeywordResponse>) {
                Log.d(TAG, "SearchService - onResponse() : 인기 검색어 조회 성공")
                view.onGetPopularKeywordSuccess(response.body() as PopularKeywordResponse)
            }

            override fun onFailure(call: Call<PopularKeywordResponse>, t: Throwable) {
                view.onGetPopularKeywordFailure(t.message ?: "통신오류")
            }
        })
    }

    fun postKeyword(keyword : String) {
        val searchKeywordInterface = ApplicationClass.sRetrofit.create(SearchKeywordInterface::class.java)
        searchKeywordInterface.postKeyword(keyword).enqueue(object : Callback<PostKeywordResponse> {
            override fun onResponse(call: Call<PostKeywordResponse>, response: Response<PostKeywordResponse>) {
                Log.d(TAG, "SearchService - onResponse() : 레시피 검색어 저장 성공")
                view.onPostKeywordSuccess(response.body() as PostKeywordResponse)
            }

            override fun onFailure(call: Call<PostKeywordResponse>, t: Throwable) {
                view.onPostKeywordFailure(t.message ?: "통신오류")
            }
        })
    }



}