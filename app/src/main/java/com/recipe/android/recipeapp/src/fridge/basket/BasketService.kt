package com.recipe.android.recipeapp.src.fridge.basket

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.fridge.basket.`interface`.BasketActivityView
import com.recipe.android.recipeapp.src.fridge.basket.`interface`.BasketRetrofitInterface
import com.recipe.android.recipeapp.src.fridge.basket.models.BasketResponse
import com.recipe.android.recipeapp.src.fridge.basket.models.FridgeBasket
import com.recipe.android.recipeapp.src.fridge.basket.models.PostFridge
import com.recipe.android.recipeapp.src.fridge.basket.models.PostFridgeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasketService(val view: BasketActivityView) {
    val TAG = "BasketService"

    // 냉장고 바구니 조회
    fun onGetBasket() {
        val basketRetrofitInterface =
            ApplicationClass.sRetrofit.create(BasketRetrofitInterface::class.java)
        basketRetrofitInterface.getBasket().enqueue(object : Callback<BasketResponse> {
            override fun onResponse(
                call: Call<BasketResponse>,
                response: Response<BasketResponse>
            ) {
                Log.d(TAG, "BasketService - onResponse() : 냉장고 바구니 조회 api 호출 성공")
                if (response.body() == null) {
                    view.onBasketServiceFailure("response is null")
                } else {
                    view.onGetBasketSuccess(response.body() as BasketResponse)
                }
            }

            override fun onFailure(call: Call<BasketResponse>, t: Throwable) {
                Log.d(TAG, "BasketService - onFailure() : 냉장고 바구니 조회 api 호출 실패")
                view.onBasketServiceFailure(t.message ?: "통신오류")
            }

        })
    }

    // 냉장고 채우기
    fun onPostFridge(fridgeBasketList: ArrayList<FridgeBasket>){

        //val param = PostFridge(fridgeBasketList)

        val params = HashMap<String, Any>()
        params["fridgeBasketList"] = fridgeBasketList

        val basketRetrofitInterface =
            ApplicationClass.sRetrofit.create(BasketRetrofitInterface::class.java)
        basketRetrofitInterface.postFridge(params).enqueue(object : Callback<PostFridgeResponse> {
            override fun onResponse(
                call: Call<PostFridgeResponse>,
                response: Response<PostFridgeResponse>
            ) {
                Log.d(TAG, "BasketService - onResponse() : 냉장고 채우기  API 호출 성공")
                if (response.body() == null) {
                    view.onBasketServiceFailure("response is null")
                } else {
                    view.onPostFridgeSuccess(response.body() as PostFridgeResponse)
                }
            }

            override fun onFailure(call: Call<PostFridgeResponse>, t: Throwable) {
                Log.d(TAG, "BasketService - onFailure() : 냉장고 채우기  API 호출 실패")
                view.onBasketServiceFailure(t.message ?: "통신오류")
            }

        })
    }
}