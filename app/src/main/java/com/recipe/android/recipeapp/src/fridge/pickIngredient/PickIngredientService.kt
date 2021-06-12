package com.recipe.android.recipeapp.src.fridge.pickIngredient

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientRetrofitInterface
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.GetBasketCntResponse
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResponse
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.PostIngredientsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PickIngredientService(val view: PickIngredientActivityView) {
    val TAG = "AddDirectService"

    // 재료 조회
    fun getIngredients(keyword: String) {
        val addDirectRetrofitInterface =
            ApplicationClass.sRetrofit.create(PickIngredientRetrofitInterface::class.java)
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

    // 재료 선택으로 냉장고 바구니 담기
    fun postIngredients(ingredientList: ArrayList<Int>) {
        val param = HashMap<String, Any>()
        param["ingredientList"] = ingredientList as List<Int>
        val addDirectRetrofitInterface =
            ApplicationClass.sRetrofit.create(PickIngredientRetrofitInterface::class.java)
        addDirectRetrofitInterface.postIngredients(param).enqueue(object :
            Callback<PostIngredientsResponse> {
            override fun onResponse(
                call: Call<PostIngredientsResponse>,
                response: Response<PostIngredientsResponse>
            ) {
                Log.d(TAG, "AddDirectService - onResponse() : 재료 선택으로 냉장고 바구니 담기 API 호출 성공")
                if (response.body() == null) {
                    view.addDirectFailure("response is null")
                } else {
                    view.onPostIngredientSuccess(response.body() as PostIngredientsResponse)
                }
            }

            override fun onFailure(call: Call<PostIngredientsResponse>, t: Throwable) {
                Log.d(TAG, "AddDirectService - onFailure() : 재료 선택으로 냉장고 바구니 담기 API 실패")
                view.addDirectFailure(t.message ?: "통신오류")
            }

        })
    }

    fun getBasketCnt() {
        val pickIngredientRetrofitInterface =
            ApplicationClass.sRetrofit.create(PickIngredientRetrofitInterface::class.java)
        pickIngredientRetrofitInterface.getBasketCnt().enqueue(object :
            Callback<GetBasketCntResponse> {
            override fun onResponse(
                call: Call<GetBasketCntResponse>,
                response: Response<GetBasketCntResponse>
            ) {
                if (response.body() == null) {
                    Log.d(TAG, "PickIngredientService - onResponse() : response is null")
                } else {
                    view.getBasketCntSuccess(response.body() as GetBasketCntResponse)
                }
            }

            override fun onFailure(call: Call<GetBasketCntResponse>, t: Throwable) {
                Log.d(TAG, "PickIngredientService - onFailure() : ${t.message}")
            }
        })
    }
}