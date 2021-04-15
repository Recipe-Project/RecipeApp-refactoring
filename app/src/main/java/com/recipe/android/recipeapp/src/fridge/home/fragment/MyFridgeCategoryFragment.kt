package com.recipe.android.recipeapp.src.fridge.home.fragment

import android.os.Bundle
import android.view.View
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentMyFridgeCategoryBinding
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeIngredientRecyclerviewAdapter
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult

class MyFridgeCategoryFragment(val result : GetFridgeResult)
    : BaseFragment<FragmentMyFridgeCategoryBinding>(FragmentMyFridgeCategoryBinding::bind, R.layout.fragment_my_fridge_category) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fridgeItemList = result.fridgeList
        val myFridgeIngredientRecyclerviewAdapter = MyFridgeIngredientRecyclerviewAdapter(requireContext())
        binding.rvIngredient.adapter = myFridgeIngredientRecyclerviewAdapter
        myFridgeIngredientRecyclerviewAdapter.submitList(fridgeItemList)
        // 카테고리 이름
        binding.tvCategory.text = result.ingredientCategoryName
    }
}