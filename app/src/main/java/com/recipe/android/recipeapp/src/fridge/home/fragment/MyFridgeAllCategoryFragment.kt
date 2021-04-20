package com.recipe.android.recipeapp.src.fridge.home.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentMyFridgeAllCategoryBinding
import com.recipe.android.recipeapp.src.fridge.home.SwipeToDeleteCallback
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeAllCategoryAdapter
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult

class MyFridgeAllCategoryFragment(private val resultList : ArrayList<GetFridgeResult>)
    : BaseFragment<FragmentMyFridgeAllCategoryBinding>(FragmentMyFridgeAllCategoryBinding::bind, R.layout.fragment_my_fridge_all_category) {

    val TAG = "MyFridgeAllCategoryFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myFridgeAllCategoryAdapter = MyFridgeAllCategoryAdapter(requireContext())
        binding.rvAll.adapter = myFridgeAllCategoryAdapter
        binding.rvAll.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        myFridgeAllCategoryAdapter.submitList(resultList)

    }
}