package com.recipe.android.recipeapp.src.search.publicRecipe

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeDetailInterface
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.PublicRecipeDetailView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PublicRecipeDetailService(val view : PublicRecipeDetailView) {

    val TAG = "PublicRecipeService"

    fun getPublicRecipeDetail(index : Int) {
        val publicRecipeDetailInterface = ApplicationClass.sRetrofit.create(PublicRecipeDetailInterface::class.java)
        publicRecipeDetailInterface.getPublicRecipeDetail(index).enqueue(object : Callback<PublicRecipeDetailResponse>{
            override fun onResponse(call: Call<PublicRecipeDetailResponse>, response: Response<PublicRecipeDetailResponse>) {
                Log.d(TAG, "PublicRecipeService - onResponse() : 공공레시피 상세 조회 성공")
                view.onGetPublicRecipeDetailSuccess(response.body() as PublicRecipeDetailResponse)
            }

            override fun onFailure(call: Call<PublicRecipeDetailResponse>, t: Throwable) {
                view.onGetPublicRecipeDetailFailure(t.message ?: "통신오류")
            }

        })
    }
}
