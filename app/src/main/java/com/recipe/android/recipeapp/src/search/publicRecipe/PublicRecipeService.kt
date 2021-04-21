package com.recipe.android.recipeapp.src.search.publicRecipe

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.search.`interface`.SearchKeywordInterface
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeInterface
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PublicRecipeService(val view : PublicRecipeView) {

    val TAG = "PublicRecipeService"

    fun getPublicRecipe(keyword : String) {
        val publicRecipeInterface = ApplicationClass.sRetrofit.create(PublicRecipeInterface::class.java)
        publicRecipeInterface.getPublicRecipe(keyword).enqueue(object :
            Callback<PublicRecipeResponse> {
            override fun onFailure(call: Call<PublicRecipeResponse>, t: Throwable) {
                view.onGetPublicRecipeFailure(t.message ?: "통신오류")
            }

            override fun onResponse(call: Call<PublicRecipeResponse>, response: Response<PublicRecipeResponse>) {
                Log.d(TAG, "PublicRecipeService - onResponse() : 공공레시피 조회 성공")
                view.onGetPublicRecipeSuccess(response.body() as PublicRecipeResponse)

            }

        })
    }
}