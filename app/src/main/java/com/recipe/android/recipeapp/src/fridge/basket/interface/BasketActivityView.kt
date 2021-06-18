package com.recipe.android.recipeapp.src.fridge.basket.`interface`

import com.recipe.android.recipeapp.src.fridge.basket.models.BasketResponse
import com.recipe.android.recipeapp.src.fridge.basket.models.DeleteBasketResponse
import com.recipe.android.recipeapp.src.fridge.basket.models.PostFridgeResponse

interface BasketActivityView {
    fun onGetBasketSuccess(response: BasketResponse)
    fun onPostFridgeSuccess(response: PostFridgeResponse)

    fun onClickStorageMethod(string: String, position: Int)
    fun onClickCount(cnt: Int, position: Int)
    fun onSetExpiredAt(date: String, position: Int)

    fun onClickExpiredAt(position: Int, expiredAt: String?)

    fun onClickPickRemove(position: Int)
    fun onDeleteBasketSuccess(deleteBasketResponse: DeleteBasketResponse, ingredient: String)

    fun onBasketServiceFailure(message: String)

    fun onUpCnt(position: Int, cnt:Int)
    fun onDownCnt(position: Int, cnt:Int)
}