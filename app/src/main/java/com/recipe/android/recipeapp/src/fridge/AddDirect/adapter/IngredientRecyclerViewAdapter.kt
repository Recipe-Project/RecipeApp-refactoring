package com.recipe.android.recipeapp.src.fridge.AddDirect.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemIngredientBinding
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.AddDirect.viewHolder.IngredientViewHolder

class IngredientRecyclerViewAdapter : RecyclerView.Adapter<IngredientViewHolder>() {

    var ingredientList = ArrayList<Ingredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bindWithView(ingredientList[position])
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun submitList(ingredientList: ArrayList<Ingredient>) {
        this.ingredientList = ingredientList
        notifyDataSetChanged()
    }

}