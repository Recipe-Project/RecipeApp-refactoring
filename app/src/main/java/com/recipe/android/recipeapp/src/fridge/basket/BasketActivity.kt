package com.recipe.android.recipeapp.src.fridge.basket

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityBasketBinding
import com.recipe.android.recipeapp.src.MainActivity
import com.recipe.android.recipeapp.src.fridge.FridgeFragment
import com.recipe.android.recipeapp.src.fridge.basket.`interface`.BasketActivityView
import com.recipe.android.recipeapp.src.fridge.basket.adapter.BasketRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.basket.models.*

class BasketActivity : BaseActivity<ActivityBasketBinding>(ActivityBasketBinding::inflate),
    BasketActivityView {

    val TAG = "BasketActivity"

    var basketItemList = ArrayList<BasketIngredient>()
    private lateinit var basketRecyclerViewAdapter: BasketRecyclerViewAdapter

    // 냉장고 채우기 Body
    var fridgeBasketList = ArrayList<FridgeBasket>()

    var year = 0
    var month = 0
    var day = 0

    var deletePosition = 0

    val patchFridgeBasketList = ArrayList<PatchBasket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 냉장고 바구니 조회
        BasketService(this).getBasket()

        basketRecyclerViewAdapter = BasketRecyclerViewAdapter(this, this)
        binding.rvBasket.apply {
            adapter = basketRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@BasketActivity, RecyclerView.VERTICAL, false)
        }

        // 저장 버튼
        binding.btnSave.setOnClickListener {
            saveBasket()
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun saveBasket(){
        Log.d(TAG, "BasketActivity - onCreate() : $basketItemList")

        fridgeBasketList.clear()
        basketItemList.forEach {
            fridgeBasketList.add(
                FridgeBasket(
                    it.ingredientCnt,
                    it.expiredAt,
                    it.ingredientCategoryIdx,
                    it.ingredientIcon.toString(),
                    it.ingredientName,
                    it.storageMethod
                )
            )
        }

        BasketService(this).postFridge(fridgeBasketList)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        basketItemList.forEach {
            patchFridgeBasketList.add(
                PatchBasket(
                    it.ingredientName,
                    it.ingredientCnt,
                    it.storageMethod,
                    it.expiredAt
                )
            )
        }

        val param = HashMap<String, Any>()
        param["fridgeBasketList"] = patchFridgeBasketList
        BasketService(this).patchBasket(param)
        finish()
    }

    // 냉장고 바구니 조회 성공
    override fun onGetBasketSuccess(response: BasketResponse) {
        if (response.isSuccess) {
            basketItemList.clear()
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
        if (response.isSuccess) {
            FridgeFragment.patchFridgeList.clear()
            showCustomToast(getString(R.string.fridgeSuccessMessage))
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        } else {
            when (response.code) {
                2075 -> {
                    // 냉장고에 이미 있는 재료일 경우
                    showCustomToast(response.message)
                }
                2073 -> {
                    showCustomToast("냉장고 바구니가 비어있습니다.")
                }
                else -> onBasketServiceFailure(response.message)
            }
        }
    }

    // 냉장, 냉동, 실온 선택
    override fun onClickStorageMethod(method: String, position: Int) {
        basketItemList[position].storageMethod = method
    }

    // 개수 조절
    override fun onClickCount(cnt: Int, position: Int) {
        basketItemList[position].ingredientCnt = cnt
    }

    // 유통기한
    override fun onSetExpiredAt(date: String, position: Int) {
        basketItemList[position].expiredAt = date
    }

    override fun onClickExpiredAt(position: Int, expiredAt: String?) {
        basketItemList[position].expiredAt = expiredAt
    }

    // 냉장고 바구니에서 재료 삭제 호출
    override fun onClickPickRemove(position: Int) {
        deletePosition = position
        BasketService(this).deleteBasket(basketItemList[position].ingredientName)
    }

    // 삭제 성공
    override fun onDeleteBasketSuccess(
        deleteBasketResponse: DeleteBasketResponse,
        ingredient: String
    ) {
        if (deleteBasketResponse.isSuccess) {
            when (deleteBasketResponse.code) {
                1000 -> {

                    for (i in basketItemList.indices) {
                        if (basketItemList[i].ingredientName == ingredient) {
                            basketItemList.removeAt(i)
                            break
                        }
                    }

                    Log.d(TAG, "BasketActivity - onDeleteBasketSuccess() : $basketItemList")
                    basketRecyclerViewAdapter.submitList(basketItemList)
                    binding.tvBasketCnt.text = basketItemList.size.toString()
                }
                else -> {
                    showCustomToast(getString(R.string.networkError))
                }
            }
        }
    }

    override fun onBasketServiceFailure(message: String) {
        Log.d(TAG, "BasketActivity - onBasketServiceFailure() : $message")
        showCustomToast(getString(R.string.networkError))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            405 -> if (resultCode == 4500) {
                val bundle = data?.extras
                year = bundle?.get("year") as Int
                month = bundle.get("monthOfYear") as Int
                day = bundle.get("dayOfMonth") as Int
            }
        }
    }
}