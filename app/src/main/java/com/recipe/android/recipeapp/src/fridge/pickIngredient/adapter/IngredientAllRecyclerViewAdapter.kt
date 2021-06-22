package com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.FragmentCategoryBinding
import com.recipe.android.recipeapp.databinding.ItemAllIngredientsBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.CategoryIngrediets
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResult
import com.recipe.android.recipeapp.src.fridge.pickIngredient.viewHolder.IngredientAllViewHolder

class IngredientAllRecyclerViewAdapter(val view: PickIngredientActivityView) :
    RecyclerView.Adapter<IngredientAllViewHolder>() {

    var ingredientsList = ArrayList<CategoryIngrediets>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientAllViewHolder {
        return IngredientAllViewHolder(
            ItemAllIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            view
        )
    }

    override fun onBindViewHolder(holder: IngredientAllViewHolder, position: Int) {
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