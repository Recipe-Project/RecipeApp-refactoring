package com.recipe.android.recipeapp.src.fridge.pickIngredient.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemIngredientBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient

class IngredientViewHolder(val binding: ItemIngredientBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val clickItemPosition = 0

    fun bindWithView(ingredient: Ingredient) {
        binding.tvIngredientName.text = ingredient.ingredientName
        if (ingredient.ingredientIcon != "") {
            Glide.with(ApplicationClass.instance).load(ingredient.ingredientIcon)
                .into(binding.icIngredient)
        }
    }
}