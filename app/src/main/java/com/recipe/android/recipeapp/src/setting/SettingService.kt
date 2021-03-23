package com.recipe.android.recipeapp.src.setting

import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.setting.`interface`.SettingRetrofitInterface
import com.recipe.android.recipeapp.src.setting.models.DeleteIdResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingService {

    val TAG = "SettingService"

    fun deleteId(userIdx: Int) {
        val settingRetrofitInterface = ApplicationClass.sRetrofit.create(SettingRetrofitInterface::class.java)
        settingRetrofitInterface.deleteId(userIdx).enqueue(object : Callback<DeleteIdResponse> {
            override fun onResponse(
                call: Call<DeleteIdResponse>,
                response: Response<DeleteIdResponse>
            ) {

            }

            override fun onFailure(call: Call<DeleteIdResponse>, t: Throwable) {

            }

        })
    }
}