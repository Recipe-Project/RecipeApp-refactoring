package com.recipe.android.recipeapp.src.emptyFridge

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.emptyFridge.`interface`.EmptyFridgeInterface
import com.recipe.android.recipeapp.src.emptyFridge.`interface`.EmptyFridgeView
import com.recipe.android.recipeapp.src.emptyFridge.models.EmptyFridgeResponse
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmptyFridgeService(val view : EmptyFridgeView) {

    val TAG = "EmptyFridgeService"

    fun tryGetEmptyFridge(start : Int, display : Int) {
        val emptyFridgeInterface = ApplicationClass.sRetrofit.create(EmptyFridgeInterface::class.java)
        emptyFridgeInterface.getEmptyFridge(start, display).enqueue(object :
            Callback<EmptyFridgeResponse> {
            override fun onResponse(
                call: Call<EmptyFridgeResponse>,
                response: Response<EmptyFridgeResponse>
            ) {
                Log.d(TAG, "EmptyFridgeService - onResponse() : 냉장고비우기 조회 성공")
                if (response.body() == null) {
                    Log.d(TAG, "EmptyFridgeService - onResponse() : response is null")
                } else {
                    view.onGetEmptyFridgeSuccess(response.body() as EmptyFridgeResponse)
                }
            }

            override fun onFailure(call: Call<EmptyFridgeResponse>, t: Throwable) {
                view.onGetEmptyFridgeFailure(t.message ?: "통신오류")
            }
        })
    }
}