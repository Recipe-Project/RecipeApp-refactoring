package com.recipe.android.recipeapp.src.myPage.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemMyPageRecipeBinding
import com.recipe.android.recipeapp.src.myPage.models.MyRecipe

class MyPageRecipeViewHolder(val binding: ItemMyPageRecipeBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindWithView(myRecipeItem: MyRecipe, position: Int, myRecipeTotalSize: Int) {
        if (myRecipeItem.myRecipeThumbnail != "") {
            Glide.with(ApplicationClass.instance).load(myRecipeItem.myRecipeThumbnail).into(binding.imgThumbnail)
        }
        // 6 번째 아이템이면
        if (position == 5) {
            binding.colorBlack.visibility = View.VISIBLE
            binding.tvRecipePlusCnt.text = "+${myRecipeTotalSize-6}"
            binding.tvRecipePlusCnt.visibility = View.VISIBLE
        } else {
            binding.colorBlack.visibility = View.INVISIBLE
            binding.tvRecipePlusCnt.visibility = View.INVISIBLE
        }
    }
}