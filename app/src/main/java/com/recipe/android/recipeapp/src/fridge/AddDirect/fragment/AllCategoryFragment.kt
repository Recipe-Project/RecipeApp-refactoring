package com.recipe.android.recipeapp.src.fridge.AddDirect.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentCategoryAllBinding
import com.recipe.android.recipeapp.src.fridge.AddDirect.adapter.IngredientAllRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.IngredientResult

class AllCategoryFragment(val ingredients: ArrayList<IngredientResult>) : BaseFragment<FragmentCategoryAllBinding>(FragmentCategoryAllBinding::bind, R.layout.fragment_category_all) {

    val TAG = "AllCategoryFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ingredientAllRecyclerViewAdapter = IngredientAllRecyclerViewAdapter()

        binding.rvAll.apply {
            adapter = ingredientAllRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        ingredientAllRecyclerViewAdapter.submitList(ingredients)

    }
}