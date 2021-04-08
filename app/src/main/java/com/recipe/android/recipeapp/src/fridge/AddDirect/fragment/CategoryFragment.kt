package com.recipe.android.recipeapp.src.fridge.AddDirect.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentCategoryBinding
import com.recipe.android.recipeapp.src.fridge.AddDirect.adapter.IngredientRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.IngredientResult

class CategoryFragment(val ingredientResult: IngredientResult) :
    BaseFragment<FragmentCategoryBinding>(
        FragmentCategoryBinding::bind,
        R.layout.fragment_category
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 리스트
        val ingredientRecyclerViewAdapter = IngredientRecyclerViewAdapter()
        binding.rvIngredient.apply {
            adapter = ingredientRecyclerViewAdapter
            layoutManager = GridLayoutManager(context, 4)
        }
        ingredientRecyclerViewAdapter.submitList(ingredientResult.ingredientList as ArrayList<Ingredient>)

        // 카테고리 이름
        binding.tvCategory.text = ingredientResult.ingredientCategoryName
    }
}