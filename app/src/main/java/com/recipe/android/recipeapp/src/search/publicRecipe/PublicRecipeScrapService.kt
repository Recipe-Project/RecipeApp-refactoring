package com.recipe.android.recipeapp.src.search.publicRecipe

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeScrapInterface
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.PublicRecipeScrapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PublicRecipeScrapService(val view : PublicRecipeScrapView) {

    val TAG = "PublicRecipeScrapService"

    fun tryPostAddingScrap(request : PublicRecipeScrapRequest) {
        val publicRecipeScrapInterface = ApplicationClass.sRetrofit.create(PublicRecipeScrapInterface::class.java)
        publicRecipeScrapInterface.postAddingScrap(request).enqueue(object : Callback<PublicRecipeScrapResponse>{
            override fun onResponse(call: Call<PublicRecipeScrapResponse>, response: Response<PublicRecipeScrapResponse>) {
                Log.d(TAG, "PublicRecipeScrapService - onResponse() : 공공레시피 스크랩 성공")
                view.onPostPublicRecipeScrapSuccess(response.body() as PublicRecipeScrapResponse)

            }

            override fun onFailure(call: Call<PublicRecipeScrapResponse>, t: Throwable) {
                view.onPostPublicRecipeScrapFailure(t.message ?: "통신오류")
            }
        })
    }
}