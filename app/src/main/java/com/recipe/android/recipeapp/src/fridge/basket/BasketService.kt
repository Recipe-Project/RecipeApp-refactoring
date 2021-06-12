package com.recipe.android.recipeapp.src.fridge.basket

import android.util.Log
import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.fridge.basket.`interface`.BasketActivityView
import com.recipe.android.recipeapp.src.fridge.basket.`interface`.BasketRetrofitInterface
import com.recipe.android.recipeapp.src.fridge.basket.models.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasketService(val view: BasketActivityView) {
    val TAG = "BasketService"

    // 냉장고 바구니 조회
    fun getBasket() {
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
    fun postFridge(fridgeBasketList: ArrayList<FridgeBasket>){

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

    // 냉장고 바구니에서 재료 삭제
    fun deleteBasket(ingredient: String){
        val basketRetrofitInterface =
            ApplicationClass.sRetrofit.create(BasketRetrofitInterface::class.java)
        basketRetrofitInterface.deleteBasket(ingredient).enqueue(object :
            Callback<DeleteBasketResponse> {
            override fun onResponse(
                call: Call<DeleteBasketResponse>,
                response: Response<DeleteBasketResponse>
            ) {
                Log.d(TAG, "BasketService - onResponse() : 냉장고 바구니에서 재료 삭제 api 호출 성공")
                if (response.body() == null) {
                    Log.d(TAG, "BasketService - onResponse() : response is null")
                } else {
                    view.onDeleteBasketSuccess(response.body() as DeleteBasketResponse, ingredient)
                }
            }

            override fun onFailure(call: Call<DeleteBasketResponse>, t: Throwable) {
                Log.d(TAG, "BasketService - onFailure() : 냉장고 바구니에서 재료 삭제 api 호출 실패")
                Log.d(TAG, "BasketService - onFailure() : ${t.message}")
            }

        })
    }

    // 냉장고 바구니 수정
    fun patchBasket(param: HashMap<String, Any>){
        val basketRetrofitInterface =
            ApplicationClass.sRetrofit.create(BasketRetrofitInterface::class.java)
        basketRetrofitInterface.patchBasket(param).enqueue(object :
            Callback<SimpleResponse> {
            override fun onResponse(
                call: Call<SimpleResponse>,
                response: Response<SimpleResponse>
            ) {
                if (response.body() == null) Log.d(
                    TAG,
                    "BasketService - onResponse() : response is null"
                )
                else Log.d(TAG, "BasketService - onResponse() : 냉장고 바구니 수정 성공")
            }

            override fun onFailure(call: Call<SimpleResponse>, t: Throwable) {
                Log.d(TAG, "BasketService - onFailure() : 냉장고 바구니 수정 실패")
            }

        })
    }
}