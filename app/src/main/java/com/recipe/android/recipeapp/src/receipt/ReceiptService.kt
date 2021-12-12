package com.recipe.android.recipeapp.src.receipt

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.receipt.`interface`.ReceiptInterface
import com.recipe.android.recipeapp.src.receipt.models.GetAllReceiptResponse
import com.recipe.android.recipeapp.src.receipt.models.PostNewReceiptRequest
import com.recipe.android.recipeapp.src.receipt.models.PostNewReceiptResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceiptService(val view : ReceiptView) {

    val TAG = "ReceiptService"

    fun tryPostNewReceipt(request: PostNewReceiptRequest) {
        val receiptInterface = ApplicationClass.sRetrofit.create(ReceiptInterface::class.java)
        receiptInterface.postNewReceipt(request).enqueue(object : Callback<PostNewReceiptResponse>{
            override fun onResponse(call: Call<PostNewReceiptResponse>, response: Response<PostNewReceiptResponse>) {
                Log.d(TAG, "ReceiptService - onResponse() : 영수증 입력 성공")
                view.onPostNewReceiptSuccess(response.body() as PostNewReceiptResponse)
            }

            override fun onFailure(call: Call<PostNewReceiptResponse>, t: Throwable) {
                view.onPostNewReceiptFailure(t.message ?: "통신오류")
            }
        })
    }

    fun tryGetAllReceipt() {
        val receiptInterface = ApplicationClass.sRetrofit.create(ReceiptInterface::class.java)
        receiptInterface.getAllReceipt().enqueue(object : Callback<GetAllReceiptResponse>{
            override fun onResponse(call: Call<GetAllReceiptResponse>, response: Response<GetAllReceiptResponse>) {
                Log.d(TAG, "ReceiptService - onResponse() : 영수증 전체 조회 성공")
                view.onGetAllReceiptSuccess(response.body() as GetAllReceiptResponse)
            }

            override fun onFailure(call: Call<GetAllReceiptResponse>, t: Throwable) {
                view.onGetAllReceiptFailure(t.message ?: "통신오류")
            }
        })
    }
}