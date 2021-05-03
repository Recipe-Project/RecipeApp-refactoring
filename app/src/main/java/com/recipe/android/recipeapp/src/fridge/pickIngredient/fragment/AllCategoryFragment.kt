package com.recipe.android.recipeapp.src.fridge.pickIngredient.fragment

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentCategoryAllBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.*
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientAllRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.CategoryIngrediets


class AllCategoryFragment: BaseFragment<FragmentCategoryAllBinding>(
    FragmentCategoryAllBinding::bind,
    R.layout.fragment_category_all
) {

    val TAG = "AllCategoryFragment"

    private var pickIngredientActivityView: PickIngredientActivityView? = null

    var ingredients = ArrayList<CategoryIngrediets>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey("ingredients") }?.apply {
            ingredients = getParcelableArrayList<Parcelable>("ingredients") as ArrayList<CategoryIngrediets>
        }

        val ingredientAllRecyclerViewAdapter = IngredientAllRecyclerViewAdapter(pickIngredientActivityView!!)

        // 카테고리 전체보기
        binding.rvAll.apply {
            adapter = ingredientAllRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }
        ingredientAllRecyclerViewAdapter.submitList(ingredients)

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