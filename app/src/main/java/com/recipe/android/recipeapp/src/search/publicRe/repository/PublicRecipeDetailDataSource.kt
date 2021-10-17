package com.recipe.android.recipeapp.src.search.publicRe.repository

import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.search.publicRe.model.PublicRecipeDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object PublicRecipeDetailDataSource {

    private val publicRecipeDetailService = ApplicationClass.sRetrofit.create(PublicRecipeDetailService::class.java)

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
}