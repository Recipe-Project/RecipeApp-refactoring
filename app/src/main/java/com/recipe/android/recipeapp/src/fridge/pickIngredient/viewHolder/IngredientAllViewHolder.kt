package com.recipe.android.recipeapp.src.fridge.pickIngredient.viewHolder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemAllIngredientsBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.CategoryIngrediets
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient

class IngredientAllViewHolder(
    val binding: ItemAllIngredientsBinding,
    val view: PickIngredientActivityView
) : RecyclerView.ViewHolder(binding.root) {
    fun bindWithView(ingredientResult: CategoryIngrediets) {

        // 카테고리 네임
        binding.tvCategory.text = ingredientResult.ingredientCategoryName

        // 리스트
        val ingredientRecyclerViewAdapter = IngredientRecyclerViewAdapter(view)
        binding.rvIngredient.apply {
            adapter = ingredientRecyclerViewAdapter
            layoutManager = GridLayoutManager(context, 4)
        }

        ingredientRecyclerViewAdapter.submitList(ingredientResult.ingredientList as ArrayList<Ingredient>)
    }
}