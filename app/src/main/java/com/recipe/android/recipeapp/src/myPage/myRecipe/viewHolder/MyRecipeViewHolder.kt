package com.recipe.android.recipeapp.src.myPage.myRecipe.viewHolder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemMyRecipeListBinding
import com.recipe.android.recipeapp.src.myPage.myRecipe.models.MyRecipeResult

class MyRecipeViewHolder(val binding: ItemMyRecipeListBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindWithView(myRecipeItem: MyRecipeResult) {
        Glide.with(ApplicationClass.instance).load(myRecipeItem.thumbnail).transform(CenterCrop(), RoundedCorners(20)).into(binding.imgRecipe)
        binding.tvRecipeTitle.text = myRecipeItem.title
        binding.tvRecipeExplain.text = myRecipeItem.content
    }
}