package com.recipe.android.recipeapp.src.fridge.pickIngredient.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentCategoryBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.CategoryIngrediets
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResult

class CategoryFragment(val ingredientResult: CategoryIngrediets, val addview: PickIngredientActivityView) :
    BaseFragment<FragmentCategoryBinding>(
        FragmentCategoryBinding::bind,
        R.layout.fragment_category
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 카테고리 리스트
        val ingredientRecyclerViewAdapter = IngredientRecyclerViewAdapter(addview)
        binding.rvIngredient.apply {
            adapter = ingredientRecyclerViewAdapter
            layoutManager = GridLayoutManager(context, 4)
        }
        ingredientRecyclerViewAdapter.submitList(ingredientResult.ingredientList as ArrayList<Ingredient>)

        // 카테고리 이름
        binding.tvCategory.text = ingredientResult.ingredientCategoryName
    }
}