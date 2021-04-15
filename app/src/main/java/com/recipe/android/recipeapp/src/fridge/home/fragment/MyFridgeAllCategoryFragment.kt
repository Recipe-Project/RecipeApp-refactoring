package com.recipe.android.recipeapp.src.fridge.home.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentMyFridgeAllCategoryBinding
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeAllIngredientRecyclerview
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult

class MyFridgeAllCategoryFragment(val resultList : ArrayList<GetFridgeResult>)
    : BaseFragment<FragmentMyFridgeAllCategoryBinding>(FragmentMyFridgeAllCategoryBinding::bind, R.layout.fragment_my_fridge_all_category) {

    val TAG = "MyFridgeAllCategoryFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myFridgeAllIngredientRecyclerview = MyFridgeAllIngredientRecyclerview(requireContext())
        binding.rvAll.adapter = myFridgeAllIngredientRecyclerview
        binding.rvAll.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        myFridgeAllIngredientRecyclerview.submitList(resultList)
    }
}