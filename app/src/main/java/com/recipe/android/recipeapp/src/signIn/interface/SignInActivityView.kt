package com.recipe.android.recipeapp.src.signIn.`interface`

import com.recipe.android.recipeapp.src.signIn.models.SignInResponse

interface SignInActivityView {
    fun onPostSignInSuccess(response: SignInResponse)
    fun onPostSignInFailure(message: String)
}