package com.recipe.android.recipeapp.src.splash.`interface`

import com.recipe.android.recipeapp.src.splash.models.AutoLoginResponse

interface SplashActivityView {
    fun onPostAutoLoginSuccess(response: AutoLoginResponse)
    fun onPostAutoLoginFailure(message: String)
}