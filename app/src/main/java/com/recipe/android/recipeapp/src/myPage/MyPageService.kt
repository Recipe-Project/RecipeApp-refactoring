package com.recipe.android.recipeapp.src.myPage

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.myPage.`interface`.MyPageFragmentView
import com.recipe.android.recipeapp.src.myPage.`interface`.MyPageRetrofitInterface
import com.recipe.android.recipeapp.src.myPage.models.ModifyUserInfoResponse
import com.recipe.android.recipeapp.src.myPage.models.UserInfoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPageService(val view: MyPageFragmentView) {
    val TAG = "MyPageService"

    fun getUserInfo(userIdx: Int){
        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.getUserInfo(userIdx).enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(
                call: Call<UserInfoResponse>,
                response: Response<UserInfoResponse>
            ) {
                Log.d(TAG, "MyPageService - onResponse() : 마이페이지 조회 성공")
                if(response.body() == null) {
                    view.onGetUserInfoFailure("response is null")
                } else {
                    view.onGetUserInfoSuccess(response.body() as UserInfoResponse)
                }
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
                Log.d(TAG, "MyPageService - onFailure() : 마이페이지 조회 실패")
                view.onGetUserInfoFailure(t.message ?: "통신오류")
            }

        })
    }

    fun patchUserInfo(userIdx: Int, profilePhoto: String){

        val param = HashMap<String, Any>()
        param["profilePhoto"] = profilePhoto

        val myPageRetrofitInterface = ApplicationClass.sRetrofit.create(MyPageRetrofitInterface::class.java)
        myPageRetrofitInterface.patchUserInfo(userIdx, param).enqueue(object : Callback<ModifyUserInfoResponse>{
            override fun onResponse(
                call: Call<ModifyUserInfoResponse>,
                response: Response<ModifyUserInfoResponse>
            ) {
                Log.d(TAG, "MyPageService - onResponse() : 회원 정보 수정 호출 성공")
                if(response.body() == null) {
                    view.onGetUserInfoFailure("response is null")
                } else {
                    view.onPatchUserInfoSuccess(response.body() as ModifyUserInfoResponse)
                }
            }

            override fun onFailure(call: Call<ModifyUserInfoResponse>, t: Throwable) {
                Log.d(TAG, "MyPageService - onFailure() : 회원 정보 수정 실패")
                view.onGetUserInfoFailure(t.message ?: "통신오류")
            }

        })
    }
}