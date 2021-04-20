package com.recipe.android.recipeapp.src.fridge.home

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeInterface
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeUpdateView
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientRequest
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FridgeUpdateService(val view : FridgeUpdateView) {

    val TAG = "FridgeUpdateService"

    fun tryDeleteIngredient(request: DeleteIngredientRequest) {
        val fridgeInterface = ApplicationClass.sRetrofit.create(FridgeInterface::class.java)
        fridgeInterface.deleteIngredient(request).enqueue(object : Callback<DeleteIngredientResponse> {
            override fun onResponse(call: Call<DeleteIngredientResponse>, response: Response<DeleteIngredientResponse>) {
                if(response.isSuccessful) {
                    Log.d(TAG, "FridgeUpdateService - onResponse() : 냉장고 속 식재료 삭제 성공" )
                    view.onDeleteIngredientSuccess(response.body() as DeleteIngredientResponse)
                }
            }

            override fun onFailure(call: Call<DeleteIngredientResponse>, t: Throwable) {
                view.onDeleteIngredientFailure(t.message ?: "통신오류")
            }
        })
    }
}