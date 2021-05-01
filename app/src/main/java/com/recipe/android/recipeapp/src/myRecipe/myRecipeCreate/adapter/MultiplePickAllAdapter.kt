package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.FragmentCategoryBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.CategoryIngrediets
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.viewHolder.IngredientAllViewHolder

class MultiplePickAllAdapter(val view: PickIngredientActivityView) :
    RecyclerView.Adapter<MultiplePickAllAdapter.MultipleAllViewHolder>() {

    var ingredientsList = ArrayList<CategoryIngrediets>()

    inner class MultipleAllViewHolder(
        val binding: FragmentCategoryBinding,
        val view: PickIngredientActivityView
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(ingredientResult: CategoryIngrediets) {

            // 카테고리 네임
            binding.tvCategory.text = ingredientResult.ingredientCategoryName

            // 리스트
            val multiplePickCategoryAdapter = MultiplePickCategoryAdapter(view)
            binding.rvIngredient.apply {
                adapter = multiplePickCategoryAdapter
                layoutManager = GridLayoutManager(context, 4)
            }

            multiplePickCategoryAdapter.submitList(ingredientResult.ingredientList as ArrayList<Ingredient>)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleAllViewHolder {
        return MultipleAllViewHolder(
            FragmentCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            view
        )
    }

    override fun onBindViewHolder(holder: MultipleAllViewHolder, position: Int) {
        holder.bindWithView(ingredientsList[position])
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun submitList(ingredientsList: ArrayList<CategoryIngrediets>) {
        this.ingredientsList = ingredientsList
        notifyDataSetChanged()
    }
}