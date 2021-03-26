package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.`interface`.PublicScrapFragmentView
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.`interface`.PublicScrapRetrofitInterface
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrap
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrapResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PublicScrapService(val view: PublicScrapFragmentView) {
    val TAG = "PublicScrapService"

    fun getPublicScrap(sort: Int?) {
        val publicScrapRetrofitInterface =
            ApplicationClass.sRetrofit.create(PublicScrapRetrofitInterface::class.java)
        publicScrapRetrofitInterface.getPublicScrap(sort).enqueue(object : Callback<PublicScrapResponse> {
            override fun onResponse(call: Call<PublicScrapResponse>, response: Response<PublicScrapResponse>) {
                Log.d(TAG, "PublicScrapService - onResponse() : 공공레시피 조회 api 호출 성공")
                if (response.body() == null) {
                    view.onGetPublicScrapFailure("response is null")
                } else {
                    view.onGetPublicScrapSuccess(response.body() as PublicScrapResponse)
                }
            }

            override fun onFailure(call: Call<PublicScrapResponse>, t: Throwable) {
                Log.d(TAG, "PublicScrapService - onFailure() :  공공레시피 조회 api 호출 실패")
                view.onGetPublicScrapFailure(t.message ?: "통신오류")
            }

        })
    }
}