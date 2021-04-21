package com.recipe.android.recipeapp.src.fridge.addDirect

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.PostIngredientsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddDirectRetrofitInterface {
    @POST("/fridges/direct-basket")
    fun addDirect(
        @Body param: HashMap<String, Any>
    ): Call<PostIngredientsResponse>
}
class AddDirectService(val view: AddDirectActivityView) {
    val TAG = "AddDirectService"
    fun addDirectService(ingredientName: String, ingredientIcon: String, ingredientCategoryIdx: Int){

        var param = HashMap<String, Any>()
        param["ingredientName"] = ingredientName
        param["ingredientIcon"] = ingredientIcon
        param["ingredientCategoryIdx"] = ingredientCategoryIdx

        val addDirectRetrofitInterface =
            ApplicationClass.sRetrofit.create(AddDirectRetrofitInterface::class.java)
        addDirectRetrofitInterface.addDirect(param).enqueue(object :
            Callback<PostIngredientsResponse> {
            override fun onResponse(
                call: Call<PostIngredientsResponse>,
                response: Response<PostIngredientsResponse>
            ) {
                Log.d(TAG, "AddDirectService - onResponse() : 직접 입력으로 냉장고 바구니 담기 api 호출 성공")
                if (response.body() == null) {
                    view.onAddDirectFailure("response is null")
                } else {
                    view.onAddDirectSuccess(response.body() as PostIngredientsResponse)
                }
            }

            override fun onFailure(call: Call<PostIngredientsResponse>, t: Throwable) {
                Log.d(TAG, "AddDirectService - onFailure() : 직접 입력으로 냉장고 바구니 담기 api 호출 실패")
                view.onAddDirectFailure(t.message ?: "통신오류")
            }

        })
    }
}