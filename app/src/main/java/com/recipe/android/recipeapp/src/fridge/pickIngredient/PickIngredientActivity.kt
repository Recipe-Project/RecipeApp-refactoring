package com.recipe.android.recipeapp.src.fridge.pickIngredient

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityPickIngredientBinding
import com.recipe.android.recipeapp.src.fridge.addDirect.AddDirectActivity
import com.recipe.android.recipeapp.src.fridge.basket.BasketActivity
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientViewPagerAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.PickIngredientRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.*

class PickIngredientActivity :
    BaseActivity<ActivityPickIngredientBinding>(ActivityPickIngredientBinding::inflate),
    PickIngredientActivityView {

    val TAG = "AddDirectActivity"

    // 카테고리 별 리스트
    var beefCategoryItem = ArrayList<Ingredient>()
    var vegetableCategoryItem = ArrayList<Ingredient>()
    var fruitCategoryItem = ArrayList<Ingredient>()
    var aquaticProductsCategoryItem = ArrayList<Ingredient>()
    var seasoningCategoryItem = ArrayList<Ingredient>()
    var productCategoryItem = ArrayList<Ingredient>()

    var ingredients = ArrayList<CategoryIngrediets>()

    lateinit var pickIngredientRecyclerViewAdapter: PickIngredientRecyclerViewAdapter
    var pickIngredientRecyclerViewItemList = ArrayList<Ingredient>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showLoadingDialog()
        // 재료조회 api 호출
        PickIngredientService(this).getIngredients("")

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.rvPick.visibility = View.GONE

        // 재료 선택
        pickIngredientRecyclerViewAdapter = PickIngredientRecyclerViewAdapter(this)
        binding.rvPick.apply {
            adapter = pickIngredientRecyclerViewAdapter
            layoutManager =
                LinearLayoutManager(this@PickIngredientActivity, RecyclerView.HORIZONTAL, false)
        }

        // 다음 버튼 - 냉장고 바구니 담기
        binding.btnNext.setOnClickListener {
            showLoadingDialog()
            val pickIdxList = ArrayList<Int>()
            pickIngredientRecyclerViewItemList.forEach {
                pickIdxList.add(it.ingredientIdx)
            }
            PickIngredientService(this).postIngredients(pickIdxList)
        }

        // 직접 입력 액티비티 이동
        binding.btnDirectAdd.setOnClickListener {
            val intent = Intent(this, AddDirectActivity::class.java)
            intent.putParcelableArrayListExtra("ingredientList", ingredients)
            Log.d(TAG, "PickIngredientActivity - onCreate() : $ingredients")
            startActivity(intent)
        }

        // 재료 검색
        binding.etSearch.setOnTouchListener { v, event ->
            v.backgroundTintList = ColorStateList.valueOf(getColor(R.color.green))
            binding.tabLineSearch.setBackgroundColor(getColor(R.color.green))
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val imm =
                        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                    binding.etSearch.requestFocus()

                    imm.showSoftInput(binding.etSearch, 0)

                    binding.etSearch.setOnKeyListener { v, keyCode, event ->
                        if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
                            binding.etSearch.clearFocus()
                            showLoadingDialog()
                            PickIngredientService(this).getIngredients(binding.etSearch.text.toString())
                            true
                        } else false
                    }

                }
            }
            true
        }

        binding.btnSearch.setOnClickListener {
            showLoadingDialog()
            PickIngredientService(this).getIngredients(binding.etSearch.text.toString())
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
            binding.etSearch.clearFocus()
        }

        // 바구니 액티비티
        binding.btnBasket.setOnClickListener {
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
        lateinit var ingredientViewPagerAdapter: IngredientViewPagerAdapter

        if (response.isSuccess) {

            val ingredientResult = response.result.ingredients

            ingredients.clear()
            ingredientResult.forEach {
                ingredients.add(it)
                tabLayoutTextArray.add(it.ingredientCategoryName)
            }

            // 카테고리 탭 설정
            tabLayout = binding.tabLayout
            viewPager = binding.viewPager
            ingredientViewPagerAdapter = IngredientViewPagerAdapter(this, this)
            viewPager.adapter = ingredientViewPagerAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabLayoutTextArray[position]
            }.attach()

            // 리사이클러뷰
            ingredientViewPagerAdapter.submitList(ingredients)
        } else {
            showCustomToast(getString(R.string.networkError))
            Log.d(TAG, "AddDirectActivity - onGetIngredientSuccess() : ${response.message}")
        }
    }

    override fun onResume() {
        super.onResume()
        pickIngredientRecyclerViewItemList.clear()
        pickIngredientRecyclerViewAdapter.submitList(pickIngredientRecyclerViewItemList)
        if (pickIngredientRecyclerViewItemList.size == 0) {
            binding.btnNext.backgroundTintList = ColorStateList.valueOf(getColor(R.color.gray_000))
            binding.rvPick.visibility = View.GONE
        }

        PickIngredientService(this).getBasketCnt()
    }

    // 재료 선택 후 냉장고 바구니에 담기 성공
    override fun onPostIngredientSuccess(response: PostIngredientsResponse) {
        dismissLoadingDialog()

        if (response.isSuccess) {
            val intent = Intent(this, BasketActivity::class.java)
            startActivity(intent)
        } else {
            when (response.code) {
                3069 -> showCustomToast(response.message)
                2068 -> showCustomToast(response.message)
                2070 -> showCustomToast("재료를 선택해주세요.")
                else -> {
                    Log.d(
                        TAG,
                        "PickIngredientActivity - onPostIngredientSuccess() : ${response.message}"
                    )
                    showCustomToast(getString(R.string.networkError))
                }
            }
        }
    }

    override fun addDirectFailure(message: String) {
        Log.d(TAG, "AddDirectActivity - addDirectFailure() : $message")
        dismissLoadingDialog()
        showCustomToast(getString(R.string.networkError))
    }

    override fun removePickMyIngredients(ingredient: Ingredient) {

    }

    // 재료 선택
    override fun pickItem(ingredient: Ingredient) {
        if (!pickIngredientRecyclerViewItemList.contains(ingredient)) {
            pickIngredientRecyclerViewItemList.add(ingredient)
            pickIngredientRecyclerViewAdapter.submitList(pickIngredientRecyclerViewItemList)
            binding.btnNext.backgroundTintList = ColorStateList.valueOf(getColor(R.color.green))
            binding.btnNext.setTextColor(getColor(R.color.white))
            binding.rvPick.visibility = View.VISIBLE
            binding.rvPick.scrollToPosition(pickIngredientRecyclerViewItemList.size - 1)
        } else {
            pickIngredientRecyclerViewItemList.remove(ingredient)
            pickIngredientRecyclerViewAdapter.submitList(pickIngredientRecyclerViewItemList)
            if (pickIngredientRecyclerViewItemList.size == 0) {
                binding.btnNext.backgroundTintList =
                    ColorStateList.valueOf(getColor(R.color.gray_000))
                binding.rvPick.visibility = View.GONE
            }
        }
    }

    // x 버튼으로 재료 빼기
    override fun removePickItem(position: Int) {
        pickIngredientRecyclerViewItemList.removeAt(position)
        pickIngredientRecyclerViewAdapter.notifyDataSetChanged()
        if (pickIngredientRecyclerViewItemList.size == 0) {
            binding.btnNext.backgroundTintList = ColorStateList.valueOf(getColor(R.color.gray_000))
            binding.rvPick.visibility = View.GONE
        }
    }

    override fun getBasketCntSuccess(response: GetBasketCntResponse) {
        if (response.isSuccess) {
            binding.tvBasketCnt.text = response.result.fridgesBasketCount.toString()
        }
    }
}