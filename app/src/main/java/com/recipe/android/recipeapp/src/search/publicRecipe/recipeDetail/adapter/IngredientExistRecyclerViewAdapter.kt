package com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemIngredientBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResult
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeIngredient

class IngredientExistRecyclerViewAdapter : RecyclerView.Adapter<IngredientExistRecyclerViewAdapter.CustomViewholder>() {

    var ingredientList = ArrayList<PublicRecipeIngredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.bindWithView(ingredientList[position])
    }

    override fun getItemCount(): Int = ingredientList.size

    class CustomViewholder (val binding: ItemIngredientBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(ingredient: PublicRecipeIngredient) {
            binding.tvIngredientName.text = ingredient.recipeIngredientName
            if (ingredient.recipeIngredientIcon != null) {
                Glide.with(ApplicationClass.instance).load(ingredient.recipeIngredientIcon)
                    .into(binding.icIngredient)
            }
        }
    }

    fun submitList(ingredientList: ArrayList<PublicRecipeIngredient>) {
        this.ingredientList = ingredientList
        notifyDataSetChanged()
    }
}