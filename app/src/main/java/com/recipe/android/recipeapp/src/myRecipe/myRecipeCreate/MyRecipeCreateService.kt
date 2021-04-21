package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResponse
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`.MyRecipeCreateActivityView
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`.MyRecipeCreateRetrofitInterface
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreate
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRecipeCreateService(val view: MyRecipeCreateActivityView) {
    val TAG = "MyRecipeCreateService"

    // 나만의 레시피 생성 api
    fun postMyRecipeCreate(param: MyRecipeCreate) {
        val myRecipeCreateRetrofitInterface =
            ApplicationClass.sRetrofit.create(MyRecipeCreateRetrofitInterface::class.java)
        myRecipeCreateRetrofitInterface.postMyRecipeCreate(param).enqueue(object :
            Callback<MyRecipeCreateResponse> {
            override fun onResponse(
                call: Call<MyRecipeCreateResponse>,
                response: Response<MyRecipeCreateResponse>
            ) {
                Log.d(TAG, "MyRecipeService - onResponse() : 나만의 레시피 생성 api 호출 성공")
                if (response.body() == null) {
                    view.onMyRecipeCreateFailure("response is null")
                } else {
                    view.onPostMyRecipeCreateSuccess(response.body() as MyRecipeCreateResponse)
                }
            }

            override fun onFailure(call: Call<MyRecipeCreateResponse>, t: Throwable) {
                Log.d(TAG, "MyRecipeService - onFailure() : 나만의 레시피 생성 api 호출 실패")
                view.onMyRecipeCreateFailure(t.message ?: "통신오류")
            }

        })
    }

    // 재료 조회
    fun getIngredients(keyword: String) {
        val myRecipeCreateRetrofitInterface =
            ApplicationClass.sRetrofit.create(MyRecipeCreateRetrofitInterface::class.java)
        myRecipeCreateRetrofitInterface.getIngredients(keyword).enqueue(object :
            Callback<IngredientResponse> {
            override fun onResponse(
                call: Call<IngredientResponse>,
                response: Response<IngredientResponse>
            ) {
                Log.d(TAG, "AddDirectService - onResponse() : 재료 조회 api 호출 성공")
                if (response.body() == null) {
                    view.onMyRecipeCreateFailure("response is null")
                } else {
                    view.onGetIngredientMyRecipeSuccess(response.body() as IngredientResponse)
                }
            }

            override fun onFailure(call: Call<IngredientResponse>, t: Throwable) {
                Log.d(TAG, "AddDirectService - onFailure() : 재료 조회 api 호출 실패")
                view.onMyRecipeCreateFailure(t.message ?: "통신오류")
            }

        })
    }
}