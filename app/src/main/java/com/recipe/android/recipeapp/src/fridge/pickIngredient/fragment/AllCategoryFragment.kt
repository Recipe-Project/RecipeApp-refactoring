package com.recipe.android.recipeapp.src.fridge.pickIngredient.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentCategoryAllBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientAllRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.CategoryIngrediets
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResult

class AllCategoryFragment(
    val ingredients: ArrayList<CategoryIngrediets>,
    val addview: PickIngredientActivityView
) : BaseFragment<FragmentCategoryAllBinding>(
    FragmentCategoryAllBinding::bind,
    R.layout.fragment_category_all
) {

    val TAG = "AllCategoryFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ingredientAllRecyclerViewAdapter = IngredientAllRecyclerViewAdapter(addview)

        // 카테고리 전체보기
        binding.rvAll.apply {
            adapter = ingredientAllRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        ingredientAllRecyclerViewAdapter.submitList(ingredients)

    }
}