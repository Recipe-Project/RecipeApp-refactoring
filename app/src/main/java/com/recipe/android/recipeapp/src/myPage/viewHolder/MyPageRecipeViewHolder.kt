package com.recipe.android.recipeapp.src.myPage.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemMyPageRecipeBinding
import com.recipe.android.recipeapp.src.myPage.models.MyRecipe

class MyPageRecipeViewHolder(val binding: ItemMyPageRecipeBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindWithView(myRecipeItem: MyRecipe) {
        if (myRecipeItem.myRecipeThumbnail != "") {
            Glide.with(ApplicationClass.instance).load(myRecipeItem.myRecipeThumbnail).into(binding.imgThumbnail)
        }
        binding.tvRecipeTitle.text = myRecipeItem.myRecipeTitle
        binding.tvDate.text = myRecipeItem.myRecipeDate
    }
}