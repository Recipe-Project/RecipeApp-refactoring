package com.recipe.android.recipeapp.src.fridge.pickIngredient

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityAddDirectBinding
import com.recipe.android.recipeapp.src.fridge.basket.BasketActivity
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientCategoryAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.PickIngredientRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResponse
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResult
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.PostIngredientsResponse

class PickIngredientActivity :
    BaseActivity<ActivityAddDirectBinding>(ActivityAddDirectBinding::inflate),
    PickIngredientActivityView {

    val TAG = "AddDirectActivity"

    // 카테고리 별 리스트
    var beefCategoryItem = ArrayList<Ingredient>()
    var vegetableCategoryItem = ArrayList<Ingredient>()
    var fruitCategoryItem = ArrayList<Ingredient>()
    var aquaticProductsCategoryItem = ArrayList<Ingredient>()
    var seasoningCategoryItem = ArrayList<Ingredient>()
    var productCategoryItem = ArrayList<Ingredient>()

    var ingredients = ArrayList<IngredientResult>()

    lateinit var pickIngredientRecyclerViewAdapter: PickIngredientRecyclerViewAdapter
    var pickIngredientRecyclerViewItemList = ArrayList<Ingredient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showLoadingDialog()
        // 재료조회 api 호출
        PickIngredientService(this).getIngredients("")

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        // 재료 선택
        pickIngredientRecyclerViewAdapter = PickIngredientRecyclerViewAdapter(this)
        binding.rvPick.apply {
            adapter = pickIngredientRecyclerViewAdapter
            layoutManager =
                LinearLayoutManager(this@PickIngredientActivity, RecyclerView.HORIZONTAL, false)
        }

        // 다음 버튼 - 냉장고 바구니 담기
        binding.btnNext.setOnClickListener {
            var pickIdxList = ArrayList<Int>()
            pickIngredientRecyclerViewItemList.forEach {
                pickIdxList.add(it.ingredientIdx)
            }
            PickIngredientService(this).postIngredients(pickIdxList)
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        }

    }

    // 재료조회 성공
    override fun onGetIngredientSuccess(response: IngredientResponse) {

        dismissLoadingDialog()

        var tabLayoutTextArray = ArrayList<String>()
        tabLayoutTextArray.add(getString(R.string.all))

        lateinit var tabLayout: TabLayout
        lateinit var viewPager: ViewPager2
        lateinit var ingredientCategoryAdapter: IngredientCategoryAdapter

        if (response.isSuccess) {
            val ingredientResult = response.result

            ingredientResult.forEach {
                ingredients.add(it)
                tabLayoutTextArray.add(it.ingredientCategoryName)
            }

            // 카테고리 탭 설정
            tabLayout = binding.tabLayout
            viewPager = binding.viewPager
            ingredientCategoryAdapter = IngredientCategoryAdapter(this, this)
            viewPager.adapter = ingredientCategoryAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabLayoutTextArray[position]
            }.attach()

            // 리사이클러뷰
            ingredientCategoryAdapter.submitList(ingredients)

        } else {
            showCustomToast(getString(R.string.networkError))
            Log.d(TAG, "AddDirectActivity - onGetIngredientSuccess() : ${response.message}")
        }


    }

    // 재료 선택 후 냉장고 바구니에 담기 성공
    override fun onPostIngredientSuccess(response: PostIngredientsResponse) {

    }

    override fun addDirectFailure(message: String) {
        Log.d(TAG, "AddDirectActivity - addDirectFailure() : $message")
        showCustomToast(getString(R.string.networkError))
    }

    // 재료 선택
    override fun pickItem(ingredient: Ingredient) {
        pickIngredientRecyclerViewItemList.add(ingredient)
        pickIngredientRecyclerViewAdapter.submitList(pickIngredientRecyclerViewItemList)
        binding.btnNext.backgroundTintList = ColorStateList.valueOf(getColor(R.color.sky_blue))
    }

    // x 버튼으로 재료 빼기
    override fun removePickItem(position: Int) {
        pickIngredientRecyclerViewItemList.removeAt(position)
        pickIngredientRecyclerViewAdapter.notifyDataSetChanged()
        if (pickIngredientRecyclerViewItemList.size == 0) {
            binding.btnNext.backgroundTintList = ColorStateList.valueOf(getColor(R.color.gray_000))
        }
    }
}