package com.recipe.android.recipeapp.src.fridge

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResponse
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeInterface
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeView
import com.recipe.android.recipeapp.src.fridge.home.models.PatchFridgeRequest
import com.recipe.android.recipeapp.src.fridge.home.models.PatchFridgeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FridgeService(val view : FridgeView) {

    val TAG = "FridgeService"

    fun tryGetFridge() {
        val fridgeInterface = ApplicationClass.sRetrofit.create(FridgeInterface::class.java)
        fridgeInterface.getFridge().enqueue(object : Callback<GetFridgeResponse>{
            override fun onResponse(call: Call<GetFridgeResponse>, response: Response<GetFridgeResponse>) {
                if(response.isSuccessful) {
                    Log.d(TAG, "FridgeService - onResponse() : 냉장고 조회 성공" )
                    view.onGetFridgeSuccess(response.body() as GetFridgeResponse)
                }
            }

            override fun onFailure(call: Call<GetFridgeResponse>, t: Throwable) {
                view.onGetFridgeFailure(t.message ?: "통신오류")
            }

        })
    }

    fun tryPatchFridge(request: PatchFridgeRequest) {
        val fridgeInterface = ApplicationClass.sRetrofit.create(FridgeInterface::class.java)
        fridgeInterface.patchFridge(request).enqueue(object : Callback<PatchFridgeResponse> {
            override fun onResponse(call: Call<PatchFridgeResponse>, response: Response<PatchFridgeResponse>) {
                if(response.isSuccessful) {
                    Log.d(TAG, "FridgeService - onResponse() : 냉장고 수정 성공" )
                    view.onPatchFridgeSuccess(response.body() as PatchFridgeResponse)
                }
            }

            override fun onFailure(call: Call<PatchFridgeResponse>, t: Throwable) {
                view.onPatchFridgeFailure(t.message ?: "통신오류")
            }

        })
    }
}