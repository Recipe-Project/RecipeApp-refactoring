package com.recipe.android.recipeapp.src.myPage.myRecipe.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemMyRecipeListBinding
import com.recipe.android.recipeapp.src.myPage.myRecipe.models.MyRecipeResult

class MyRecipeViewHolder(val binding: ItemMyRecipeListBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindWithView(myRecipeItem: MyRecipeResult) {
        Glide.with(ApplicationClass.instance).load(myRecipeItem.thumbnail).into(binding.imgRecipe)
        binding.tvRecipeTitle.text = myRecipeItem.title
        binding.tvRecipeExplain.text = myRecipeItem.content
    }
}