package com.recipe.android.recipeapp.src.fridge.basket

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityBasketBinding
import com.recipe.android.recipeapp.src.fridge.basket.`interface`.BasketActivityView
import com.recipe.android.recipeapp.src.fridge.basket.adapter.BasketRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.basket.models.BasketResponse
import com.recipe.android.recipeapp.src.fridge.basket.models.FridgeBasket
import com.recipe.android.recipeapp.src.fridge.basket.models.PostFridgeResponse
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient

class BasketActivity: BaseActivity<ActivityBasketBinding>(ActivityBasketBinding::inflate), BasketActivityView {

    val TAG = "BasketActivity"

    var basketItemList = ArrayList<Ingredient>()
    private lateinit var basketRecyclerViewAdapter: BasketRecyclerViewAdapter

    var fridgeBasketList = ArrayList<FridgeBasket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 냉장고 바구니 조회
        BasketService(this).onGetBasket()

        basketRecyclerViewAdapter = BasketRecyclerViewAdapter(this)
        binding.rvBasket.apply {
            adapter = basketRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@BasketActivity, RecyclerView.VERTICAL, false)
        }

        // 저장 버튼
        binding.btnSave.setOnClickListener {

            fridgeBasketList.add(FridgeBasket(22, null, 2, null, "토마토", "냉장"))
            BasketService(this).onPostFridge(fridgeBasketList)
        }
    }

    // 냉장고 바구니 조회 성공
    override fun onGetBasketSuccess(response: BasketResponse) {
        if (response.isSuccess) {
            binding.tvBasketCnt.text = response.result.ingredientCount.toString()
            response.result.ingredientList.forEach {
                basketItemList.add(it)
            }
            basketRecyclerViewAdapter.submitList(basketItemList)
        } else {
            showCustomToast(getString(R.string.networkError))
        }
    }

    override fun onPostFridgeSuccess(response: PostFridgeResponse) {

    }

    // 냉장, 냉동, 실온 선택
    override fun onClickStorageMethod() {

    }

    override fun onBasketServiceFailure(message: String) {
        Log.d(TAG, "BasketActivity - onBasketServiceFailure() : $message")
        showCustomToast(getString(R.string.networkError))
    }
}