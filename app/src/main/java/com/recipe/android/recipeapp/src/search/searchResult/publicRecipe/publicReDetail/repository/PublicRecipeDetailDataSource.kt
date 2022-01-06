package com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.repository

import com.recipe.android.recipeapp.common.SimpleRetrofitResponse
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.model.PublicRecipeDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PublicRecipeDetailDataSource {

    private val publicRecipeDetailService = ApplicationClass.sRetrofit.create(
        PublicRecipeDetailService::class.java)

    fun getPublicRecipeDetail(index : Int, callback: PublicRecipeDetailRepository.GetDataCallback<PublicRecipeDetailResponse>) {
        publicRecipeDetailService.getPublicRecipeDetail(index).enqueue(object : Callback<PublicRecipeDetailResponse> {
            override fun onResponse(
                call: Call<PublicRecipeDetailResponse>,
                response: Response<PublicRecipeDetailResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body())
                }
            }

            override fun onFailure(call: Call<PublicRecipeDetailResponse>, t: Throwable) {
                callback.onFailure(t)
            }
        })
    }

    fun scrapRecipe(index: Int, callback: PublicRecipeDetailRepository.GetDataCallback<SimpleRetrofitResponse>) {
        val param = HashMap<String, Any>()
        param["recipeId"] = index
        publicRecipeDetailService.scrapRecipe(param).enqueue(object : Callback<SimpleRetrofitResponse>{
            override fun onResponse(
                call: Call<SimpleRetrofitResponse>,
                response: Response<SimpleRetrofitResponse>
            ) {
                if (response.isSuccessful) {
                    callback.onSuccess(response.body())
                }
            }

            override fun onFailure(call: Call<SimpleRetrofitResponse>, t: Throwable) {
                callback.onFailure(t)
            }

        })
    }
}