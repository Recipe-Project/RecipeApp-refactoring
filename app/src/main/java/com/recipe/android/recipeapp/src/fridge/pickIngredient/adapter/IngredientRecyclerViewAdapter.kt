package com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemIngredientBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.viewHolder.IngredientViewHolder

class IngredientRecyclerViewAdapter(val view: PickIngredientActivityView) :
    RecyclerView.Adapter<IngredientViewHolder>() {

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
        holder.itemView.setOnClickListener {
            view.pickItem(ingredientList[position])
        }
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun submitList(ingredientList: ArrayList<Ingredient>?) {
        if (ingredientList != null) {
            this.ingredientList = ingredientList
        }
        notifyDataSetChanged()
    }

}