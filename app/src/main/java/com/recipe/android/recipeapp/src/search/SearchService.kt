package com.recipe.android.recipeapp.src.search

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.search.`interface`.SearchKeywordInterface
import com.recipe.android.recipeapp.src.search.`interface`.SearchKeywordView
import com.recipe.android.recipeapp.src.search.models.PopularKeywordResponse
import com.recipe.android.recipeapp.src.search.models.PostKeywordResponse
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService(val view: SearchKeywordView) {
    val TAG = "SearchService"

    fun getPublicRecipe(keyword : String) {
        val searchKeywordInterface = ApplicationClass.sRetrofit.create(SearchKeywordInterface::class.java)
        searchKeywordInterface.getPublicRecipe(keyword).enqueue(object : Callback<PublicRecipeResponse>{
            override fun onFailure(call: Call<PublicRecipeResponse>, t: Throwable) {
                view.onGetPublicRecipeFailure(t.message ?: "통신오류")
            }

            override fun onResponse(call: Call<PublicRecipeResponse>, response: Response<PublicRecipeResponse>) {
                Log.d(TAG, "SearchService - onResponse() : 공공레시피 조회 성공")
                view.onGetPublicRecipeSuccess(response.body() as PublicRecipeResponse)

// 검색결과가 하나도 없을 때는 어떻게 처리해줘야할지 고민 중입니다...
//                if(response.body() == null) {
//                    view.onGetUserInfoFailure("response is null")
//                } else {
//                    view.onGetUserInfoSuccess(response.body() as UserInfoResponse)
//                }
            }

        })
    }

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