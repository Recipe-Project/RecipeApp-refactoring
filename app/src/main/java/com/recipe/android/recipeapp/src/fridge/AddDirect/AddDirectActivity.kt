package com.recipe.android.recipeapp.src.fridge.AddDirect

import android.os.Bundle
import android.util.Log
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityAddDirectBinding
import com.recipe.android.recipeapp.src.fridge.AddDirect.`interface`.AddDirectActivityView
import com.recipe.android.recipeapp.src.fridge.AddDirect.adapter.IngredientCategoryAdapter
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.IngredientResponse
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.IngredientResult

class AddDirectActivity : BaseActivity<ActivityAddDirectBinding>(ActivityAddDirectBinding::inflate),
    AddDirectActivityView {

    val TAG = "AddDirectActivity"

    // 카테고리 별 리스트
    var beefCategoryItem = ArrayList<Ingredient>()
    var vegetableCategoryItem = ArrayList<Ingredient>()
    var fruitCategoryItem = ArrayList<Ingredient>()
    var aquaticProductsCategoryItem = ArrayList<Ingredient>()
    var seasoningCategoryItem = ArrayList<Ingredient>()
    var productCategoryItem = ArrayList<Ingredient>()

    var ingredients = ArrayList<IngredientResult>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showLoadingDialog()
        // 재료조회 api 호출
        AddDirectService(this).getIngredients("")

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

    }

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
            ingredientCategoryAdapter = IngredientCategoryAdapter(this)
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

    override fun addDirectFailure(message: String) {
        Log.d(TAG, "AddDirectActivity - addDirectFailure() : $message")
        showCustomToast(getString(R.string.networkError))
    }
}