package com.recipe.android.recipeapp.src.fcm

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.fcm.`interface`.FcmInterface
import com.recipe.android.recipeapp.src.fcm.models.FcmResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FcmService {

    val TAG = "FcmService"

    fun patchFcm(newToken: String){

        val param = HashMap<String, Any>()
        param["fcmToken"] = newToken

        val fcmInterface =
            ApplicationClass.sRetrofit.create(FcmInterface::class.java)
        fcmInterface.patchToken(param).enqueue(object : Callback<FcmResponse> {
            override fun onResponse(call: Call<FcmResponse>, response: Response<FcmResponse>) {
                if (response.body() == null) {

                } else {
                    Log.d(TAG, "FcmService - onResponse() : ${response.body()}")
                }
            }

            override fun onFailure(call: Call<FcmResponse>, t: Throwable) {
                Log.d(TAG, "FcmService - onFailure() : ${t.message}")
            }

        })
    }
}