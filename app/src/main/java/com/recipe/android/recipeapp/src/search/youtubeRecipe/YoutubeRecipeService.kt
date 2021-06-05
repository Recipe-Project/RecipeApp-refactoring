package com.recipe.android.recipeapp.src.search.youtubeRecipe

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.myPage.models.UserInfoResponse
import com.recipe.android.recipeapp.src.search.blogRecipe.`interface`.BlogRecipeInterface
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeScrapResponse
import com.recipe.android.recipeapp.src.search.youtubeRecipe.`interface`.YoutubeRecipeInterface
import com.recipe.android.recipeapp.src.search.youtubeRecipe.`interface`.YoutubeRecipeView
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeResponse
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeScrapResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YoutubeRecipeService(val view : YoutubeRecipeView) {

    val TAG = "YoutubeRecipeService"

    fun getYoutubeRecipe(part : String, type : String, maxResults : Int, key : String, q : String, pageToken : String) {
        val youtubeRecipeInterface = ApplicationClass.yRetrofit.create(YoutubeRecipeInterface::class.java)
        youtubeRecipeInterface.getYoutubeRecipe(part, type, maxResults, key, q, pageToken).enqueue(object : Callback<YoutubeRecipeResponse> {
            override fun onResponse(call: Call<YoutubeRecipeResponse>, response: Response<YoutubeRecipeResponse>) {
                if(response.isSuccessful) {
                    Log.d(TAG, "YoutubeRecipeService - onResponse() : 유투브레시피 조회 성공")
                    view.onGetYoutubeRecipeSuccess(response.body() as YoutubeRecipeResponse)
                }
            }

            override fun onFailure(call: Call<YoutubeRecipeResponse>, t: Throwable) {
                view.onGetYoutubeRecipeFailure(t.message ?: "통신오류")
            }
        })
    }

    fun postAddingScrap(request: YoutubeRecipeScrapRequest) {
        val youtubeRecipeInterface = ApplicationClass.sRetrofit.create(YoutubeRecipeInterface::class.java)
        youtubeRecipeInterface.postAddingScrap(request).enqueue(object : Callback<YoutubeRecipeScrapResponse> {
            override fun onResponse(call: Call<YoutubeRecipeScrapResponse>, response: Response<YoutubeRecipeScrapResponse>) {
                //Log.d(TAG, "YoutubeRecipeService - onResponse() : 유투브레시피 스크랩 성공")
                if(response.body() == null) {
                    view.onPostYoutubeRecipeScrapFailure("response is null")
                } else {
                    view.onPostYoutubeRecipeScrapSuccess(response.body() as YoutubeRecipeScrapResponse)
                    Log.d(TAG, "YoutubeRecipeService - onResponse() : 유투브레시피 스크랩 성공")
                }
            }

            override fun onFailure(call: Call<YoutubeRecipeScrapResponse>, t: Throwable) {
                Log.d(TAG, "YoutubeRecipeService - onFailure() : 유투브레시피 스크랩 실패")
                view.onPostYoutubeRecipeScrapFailure(t.message ?: "통신오류")
            }
        })
    }
}
