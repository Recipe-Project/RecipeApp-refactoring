package com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemIngredientBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.viewHolder.IngredientViewHolder

class IngredientRecyclerViewAdapter(val view: PickIngredientActivityView) :
    RecyclerView.Adapter<IngredientViewHolder>() {

    var ingredientList = ArrayList<Ingredient>()

    var clickPosition = -1

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

        if (clickPosition == position) {
            holder.binding.circleGreen.visibility = View.VISIBLE
        } else {
            holder.binding.circleGreen.visibility = View.INVISIBLE
        }

        holder.itemView.setOnClickListener {
            clickPosition = position
            view.pickItem(ingredientList[position])
            notifyDataSetChanged()
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