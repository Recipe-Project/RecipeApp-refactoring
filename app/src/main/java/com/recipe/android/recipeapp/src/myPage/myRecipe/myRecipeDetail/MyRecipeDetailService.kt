package com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeDetail

import android.util.Log
import com.google.gson.JsonElement
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeDetail.`interface`.MyRecipeDetailActivityView
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeDetail.`interface`.MyRecipeDetailRetrofitInterface
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeDetail.models.MyRecipeDeleteResponse
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeDetail.models.MyRecipeDetailResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRecipeDetailService(val view: MyRecipeDetailActivityView) {
    val TAG = "MyRecipeDetailService"

    fun getMyRecipeDetail(myRecipeIdx: Int) {
        val myRecipeDetailRetrofitInterface =
            ApplicationClass.sRetrofit.create(MyRecipeDetailRetrofitInterface::class.java)
        myRecipeDetailRetrofitInterface.getMyRecipeDetail(myRecipeIdx)
            .enqueue(object : Callback<MyRecipeDetailResponse> {
                override fun onResponse(
                    call: Call<MyRecipeDetailResponse>,
                    response: Response<MyRecipeDetailResponse>
                ) {
                    Log.d(TAG, "MyRecipeDetailService - onResponse() : 나만의 레시피 상세조회 api 호출 성공")
                    if (response.body() == null) {
                        view.onGetMyRecipeDetailFailure("response is null")
                    } else {
                        view.onGetMyRecipeDetailSuccess(response.body() as MyRecipeDetailResponse)
                    }
                }

                override fun onFailure(call: Call<MyRecipeDetailResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }

    // 나만의 레시피 삭제
    fun deleteMyRecipe(myRecipeIdx: Int) {
        val myRecipeDetailRetrofitInterface =
            ApplicationClass.sRetrofit.create(MyRecipeDetailRetrofitInterface::class.java)
        myRecipeDetailRetrofitInterface.deleteMyRecipeDetail(myRecipeIdx).enqueue(object :
            Callback<MyRecipeDeleteResponse> {
            override fun onResponse(call: Call<MyRecipeDeleteResponse>, response: Response<MyRecipeDeleteResponse>) {
                Log.d(TAG, "MyRecipeDetailService - onResponse() : 나만의 레시피 삭제 api 호출 성공")
                if (response.body() == null) {
                    //
                } else {
                    view.onDeleteMyRecipeSuccess(response.body() as MyRecipeDeleteResponse)
                }
            }

            override fun onFailure(call: Call<MyRecipeDeleteResponse>, t: Throwable) {
                Log.d(TAG, "MyRecipeDetailService - onFailure() : 나만의 레시피 삭제 api 호출 삭제")

            }

        })
    }

}