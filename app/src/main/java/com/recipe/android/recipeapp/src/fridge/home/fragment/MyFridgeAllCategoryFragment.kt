package com.recipe.android.recipeapp.src.fridge.home.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentMyFridgeAllCategoryBinding
import com.recipe.android.recipeapp.src.fridge.FridgeFragment
import com.recipe.android.recipeapp.src.fridge.home.`interface`.IngredientUpdateView
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeAllIngredientRecyclerviewAdapter
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult

class MyFridgeAllCategoryFragment(private val resultList : ArrayList<GetFridgeResult>, val updateView: IngredientUpdateView)
    : BaseFragment<FragmentMyFridgeAllCategoryBinding>(FragmentMyFridgeAllCategoryBinding::bind, R.layout.fragment_my_fridge_all_category) {

    val TAG = "MyFridgeAllCategoryFragment"
    lateinit var myFridgeAllCategoryAdapter : MyFridgeAllIngredientRecyclerviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myFridgeAllCategoryAdapter = MyFridgeAllIngredientRecyclerviewAdapter(requireContext(), updateView)
        binding.rvAll.adapter = myFridgeAllCategoryAdapter
        binding.rvAll.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        myFridgeAllCategoryAdapter.submitList(resultList)
    }
}