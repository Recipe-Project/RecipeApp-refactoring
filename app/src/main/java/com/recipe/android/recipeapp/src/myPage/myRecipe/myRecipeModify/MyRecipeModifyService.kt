package com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeModify

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeCreate.models.MyRecipeCreate
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeModify.`interface`.MyRecipeModifyActivityView
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeModify.`interface`.MyRecipeModifyRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyRecipeModifyService(val view: MyRecipeModifyActivityView) {
    val TAG = "MyRecipeModifyService"

    fun patchMyRecipe(param: MyRecipeCreate, myRecipeIdx: Int) {
        val myRecipeModifyRetrofitInterface =
            ApplicationClass.sRetrofit.create(MyRecipeModifyRetrofitInterface::class.java)
        myRecipeModifyRetrofitInterface.patchMyRecipe(param, myRecipeIdx).enqueue(object :
            Callback<MyRecipeCreateResponse> {
            override fun onResponse(
                call: Call<MyRecipeCreateResponse>,
                response: Response<MyRecipeCreateResponse>
            ) {
                Log.d(TAG, "MyRecipeModifyService - onResponse() : 나만의 레시피 수정 api 호출 성공")
                if (response.body() == null) {
                    view.onPatchMyRecipeFailure("response is null")
                } else {
                    view.onPatchMyRecipeSuccess(response.body() as MyRecipeCreateResponse)
                }
            }

            override fun onFailure(call: Call<MyRecipeCreateResponse>, t: Throwable) {
                Log.d(TAG, "MyRecipeModifyService - onFailure() : 나만의 레시피 수정 api 호출 실패")
                view.onPatchMyRecipeFailure(t.message ?: "통신오류")
            }

        })
    }
}