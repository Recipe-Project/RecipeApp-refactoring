package com.recipe.android.recipeapp.src.myPage.`interface`

import com.recipe.android.recipeapp.src.myPage.models.ModifyUserInfoResponse
import com.recipe.android.recipeapp.src.myPage.models.UserInfoResponse

interface MyPageFragmentView {
    fun onGetUserInfoSuccess(response: UserInfoResponse)
    fun onPatchUserInfoSuccess(response: ModifyUserInfoResponse)
    fun onGetUserInfoFailure(message: String)
}