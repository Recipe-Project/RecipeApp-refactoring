package com.recipe.android.recipeapp.src.myPage.`interface`

import com.recipe.android.recipeapp.src.myPage.models.UserInfoResponse

interface MyPageFragmentView {
    fun onGetUserInfoSuccess(response: UserInfoResponse)
    fun onGetUserInfoFailure(message: String)
}