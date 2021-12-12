package com.recipe.android.recipeapp.src.myRecipe

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.myRecipe.`interface`.MyRecipeRetrofitInterface
import com.recipe.android.recipeapp.src.myRecipe.models.MyRecipeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRecipeService(val view: MyRecipeActivityView) {

    val TAG = "MyRecipeService"

    fun getMyRecipe(size: Int, page: Int){
        val myRecipeRetrofitInterface =
            ApplicationClass.sRetrofit.create(MyRecipeRetrofitInterface::class.java)
        myRecipeRetrofitInterface.getMyRecipe(size, page).enqueue(object :
            Callback<MyRecipeResponse> {
            override fun onResponse(
                call: Call<MyRecipeResponse>,
                response: Response<MyRecipeResponse>
            ) {
                Log.d(TAG, "MyRecipeService - onResponse() : 나만의 레시피 전체 조회 api 호출 성공")
                if (response.body() == null) {
                    view.onGetMyRecipeFailure("response is null")
                } else {
                    view.onGetMyRecipeSuccess(response.body() as MyRecipeResponse)
                }
            }

            override fun onFailure(call: Call<MyRecipeResponse>, t: Throwable) {
                Log.d(TAG, "MyRecipeService - onFailure() : 나만의 레시피 전체 조회 api 호출 실패")
                view.onGetMyRecipeFailure(t.message ?: "통신오류")
            }

        })
    }


}