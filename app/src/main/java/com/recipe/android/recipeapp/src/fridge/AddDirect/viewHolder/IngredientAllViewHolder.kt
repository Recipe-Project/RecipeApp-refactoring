package com.recipe.android.recipeapp.src.fridge.AddDirect.viewHolder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.FragmentCategoryBinding
import com.recipe.android.recipeapp.src.fridge.AddDirect.adapter.IngredientRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.IngredientResult

class IngredientAllViewHolder(val binding: FragmentCategoryBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindWithView(ingredientResult: IngredientResult){

        // 카테고리 네임
        binding.tvCategory.text = ingredientResult.ingredientCategoryName

        // 리스트
        val ingredientRecyclerViewAdapter = IngredientRecyclerViewAdapter()
        binding.rvIngredient.apply {
            adapter = ingredientRecyclerViewAdapter
            layoutManager = GridLayoutManager(context, 4)
        }
        ingredientRecyclerViewAdapter.submitList(ingredientResult.ingredientList as ArrayList<Ingredient>)
    }
}