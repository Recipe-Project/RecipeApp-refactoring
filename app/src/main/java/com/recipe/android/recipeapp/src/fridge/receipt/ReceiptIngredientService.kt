package com.recipe.android.recipeapp.src.fridge.receipt

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.PostReceiptIngredientRequest
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.ReceiptIngredientInterface
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.ReceiptIngredientView
import com.recipe.android.recipeapp.src.fridge.receipt.models.PostReceiptIngredientResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceiptIngredientService(val view : ReceiptIngredientView) {

    val TAG = "ReceiptIngredientService"

    fun postReceiptIngredient(request : PostReceiptIngredientRequest) {
        val receiptIngredientInterface = ApplicationClass.sRetrofit.create(ReceiptIngredientInterface::class.java)
        receiptIngredientInterface.postReceiptIngredient(request).enqueue(object : Callback<PostReceiptIngredientResponse> {
            override fun onResponse(call: Call<PostReceiptIngredientResponse>, response: Response<PostReceiptIngredientResponse>) {
                if(response.isSuccessful) {
                    Log.d(TAG, "ReceiptIngredientService - onResponse() : 영수증 입력 성공")
                    view.onPostReceiptIngredientSuccess(response.body() as PostReceiptIngredientResponse)
                }
            }

            override fun onFailure(call: Call<PostReceiptIngredientResponse>, t: Throwable) {
                view.onPostReceiptIngredientFailure(t.message ?: "통신오류")
            }
        })
    }
}