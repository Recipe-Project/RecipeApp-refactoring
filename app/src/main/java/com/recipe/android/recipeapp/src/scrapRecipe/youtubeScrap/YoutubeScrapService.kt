package com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.`interface`.YoutubeScrapFragmentView
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.`interface`.YoutubeScrapRetrofitInterface
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.PostYoutubeScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrapResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeScrapService(val view: YoutubeScrapFragmentView) {

    val TAG = "YoutubeScrapService"

    fun getYoutubeScrap(sort: Int?) {
        val youtubeScrapRetrofitInterface =
            ApplicationClass.sRetrofit.create(YoutubeScrapRetrofitInterface::class.java)
        youtubeScrapRetrofitInterface.getYoutubeScrap(sort).enqueue(object :
            Callback<YoutubeScrapResponse> {
            override fun onResponse(
                call: Call<YoutubeScrapResponse>,
                response: Response<YoutubeScrapResponse>
            ) {
                Log.d(TAG, "ScrapRecipeService - onResponse() : 유튜브 스크랩 조회 api 호출 성공")
                if (response.body() == null) {
                    view.onGetYoutubeScrapFailure("response is null")
                } else {
                    view.onGetYoutubeScrapSuccess(response.body() as YoutubeScrapResponse)
                }
            }

            override fun onFailure(call: Call<YoutubeScrapResponse>, t: Throwable) {
                Log.d(TAG, "ScrapRecipeService - onFailure() : 유튜브 스크랩 조회 api 호출 실패")
                view.onGetYoutubeScrapFailure(t.message ?: "통신오류")
            }

        })
    }

    fun postYoutubeScrap(params: HashMap<String, Any>) {
        val youtubeScrapRetrofitInterface =
            ApplicationClass.sRetrofit.create(YoutubeScrapRetrofitInterface::class.java)
        youtubeScrapRetrofitInterface.postYoutubeScrap(params).enqueue(object :
            Callback<PostYoutubeScrapResponse> {
            override fun onResponse(
                call: Call<PostYoutubeScrapResponse>,
                response: Response<PostYoutubeScrapResponse>
            ) {
                Log.d(TAG, "ScrapRecipeService - onResponse() : 유튜브 스크랩 조회 api 호출 성공")
                if (response.body() == null) {
                    view.onGetYoutubeScrapFailure("response is null")
                } else {
                    view.onPostYoutubeScrapSuccess(response.body() as PostYoutubeScrapResponse)
                }
            }

            override fun onFailure(call: Call<PostYoutubeScrapResponse>, t: Throwable) {
                Log.d(TAG, "ScrapRecipeService - onFailure() : 유튜브 스크랩 조회 api 호출 실패")
                view.onGetYoutubeScrapFailure(t.message ?: "통신오류")
            }

        })
    }
}