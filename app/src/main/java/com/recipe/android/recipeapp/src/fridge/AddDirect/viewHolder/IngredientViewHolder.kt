package com.recipe.android.recipeapp.src.fridge.AddDirect.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemIngredientBinding
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.Ingredient

class IngredientViewHolder(val binding: ItemIngredientBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindWithView(ingredient: Ingredient) {
        binding.tvIngredientName.text = ingredient.ingredientName
        if (ingredient.ingredientIcon != null) {
            Glide.with(ApplicationClass.instance).load(ingredient.ingredientIcon).into(binding.icIngredient)
        }
    }
}