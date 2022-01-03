package com.recipe.android.recipeapp.src.fridge.pickIngredient.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentCategoryBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.CategoryIngrediets
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient

class CategoryFragment :
    BaseFragment<FragmentCategoryBinding>(
        FragmentCategoryBinding::bind,
        R.layout.fragment_category
    ) {

    private var pickIngredientActivityView: PickIngredientActivityView? = null

    var ingredientResult: CategoryIngrediets? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey("ingredients") }?.apply {
            ingredientResult = getParcelable("ingredients")!!
        }

        // 카테고리 리스트
        val ingredientRecyclerViewAdapter = IngredientRecyclerViewAdapter(pickIngredientActivityView!!)
        binding.rvIngredient.apply {
            adapter = ingredientRecyclerViewAdapter
            layoutManager = GridLayoutManager(context, 4)
        }
        ingredientRecyclerViewAdapter.submitList(ingredientResult?.ingredientList as ArrayList<Ingredient>)

        // 카테고리 이름
        binding.tvCategory.text = ingredientResult?.ingredientCategoryName
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is PickIngredientActivityView) {
            pickIngredientActivityView = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        pickIngredientActivityView = null
    }

}