package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.`interface`.BlogScrapFragmnetView
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.`interface`.BlogScrapRetrofitInterface
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlogScrapService(val view: BlogScrapFragmnetView) {
    val TAG = "BlogScrapService"

    fun getBlogScrap(sort: Int?) {
        val blogScrapRetrofitInterface =
            ApplicationClass.sRetrofit.create(BlogScrapRetrofitInterface::class.java)
        blogScrapRetrofitInterface.getBlogScrap(sort).enqueue(object : Callback<BlogScrapResponse> {
            override fun onResponse(
                call: Call<BlogScrapResponse>,
                response: Response<BlogScrapResponse>
            ) {
                Log.d(TAG, "BlogScrapService - onResponse() : 블로그 스크랩 조회 api 호출 성공")
                if (response.body() == null) {
                    view.onGetBlogScrapFailure("response is null")
                } else {
                    view.onGetBlogScrapSuccess(response.body() as BlogScrapResponse)
                }
            }

            override fun onFailure(call: Call<BlogScrapResponse>, t: Throwable) {
                Log.d(TAG, "BlogScrapService - onFailure() : 블로그 스크랩 조회 api 호출 실패")
                view.onGetBlogScrapFailure(t.message ?: "통신오류")
            }

        })
    }
}