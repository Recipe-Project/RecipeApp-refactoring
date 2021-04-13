package com.recipe.android.recipeapp.src.fridge.basket.`interface`

import com.recipe.android.recipeapp.src.fridge.basket.models.BasketResponse
import com.recipe.android.recipeapp.src.fridge.basket.models.PostFridgeResponse

interface BasketActivityView {
    fun onGetBasketSuccess(response: BasketResponse)
    fun onPostFridgeSuccess(response: PostFridgeResponse)

    fun onClickStorageMethod()

    fun onBasketServiceFailure(message: String)
}