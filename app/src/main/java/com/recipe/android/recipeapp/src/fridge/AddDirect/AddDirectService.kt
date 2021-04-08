package com.recipe.android.recipeapp.src.fridge.AddDirect

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.fridge.AddDirect.`interface`.AddDirectActivityView
import com.recipe.android.recipeapp.src.fridge.AddDirect.`interface`.AddDirectRetrofitInterface
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.IngredientResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddDirectService(val view: AddDirectActivityView) {
    val TAG = "AddDirectService"

    fun getIngredients(keyword: String){
        val addDirectRetrofitInterface =
            ApplicationClass.sRetrofit.create(AddDirectRetrofitInterface::class.java)
        addDirectRetrofitInterface.getIngredients(keyword).enqueue(object :
            Callback<IngredientResponse> {
            override fun onResponse(
                call: Call<IngredientResponse>,
                response: Response<IngredientResponse>
            ) {
                Log.d(TAG, "AddDirectService - onResponse() : 재료 조회 api 호출 성공")
                if (response.body() == null) {
                    view.addDirectFailure("response is null")
                } else {
                    view.onGetIngredientSuccess(response.body() as IngredientResponse)
                }
            }

            override fun onFailure(call: Call<IngredientResponse>, t: Throwable) {
                Log.d(TAG, "AddDirectService - onFailure() : 재료 조회 api 호출 실패")
                view.addDirectFailure(t.message ?: "통신오류")
            }

        })
    }
}