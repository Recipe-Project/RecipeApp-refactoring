package com.recipe.android.recipeapp.src.fridge.AddDirect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.FragmentCategoryBinding
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.IngredientResult
import com.recipe.android.recipeapp.src.fridge.AddDirect.viewHolder.IngredientAllViewHolder

class IngredientAllRecyclerViewAdapter: RecyclerView.Adapter<IngredientAllViewHolder>() {

    var ingredientsList = ArrayList<IngredientResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientAllViewHolder {
        return IngredientAllViewHolder(
            FragmentCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: IngredientAllViewHolder, position: Int) {
        holder.bindWithView(ingredientsList[position])
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun submitList(ingredientsList: ArrayList<IngredientResult>) {
        this.ingredientsList = ingredientsList
        notifyDataSetChanged()
    }
}