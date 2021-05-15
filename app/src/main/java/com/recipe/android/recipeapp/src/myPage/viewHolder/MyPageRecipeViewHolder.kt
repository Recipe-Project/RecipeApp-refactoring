package com.recipe.android.recipeapp.src.myPage.viewHolder

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemMyPageRecipeBinding
import com.recipe.android.recipeapp.src.myPage.models.MyRecipe
import com.recipe.android.recipeapp.src.myRecipe.MyRecipeActivity

class MyPageRecipeViewHolder(val binding: ItemMyPageRecipeBinding): RecyclerView.ViewHolder(binding.root) {

    val context = ApplicationClass.instance

    fun bindWithView(myRecipeItem: MyRecipe, position: Int, myRecipeTotalSize: Int) {
        if (myRecipeItem.myRecipeThumbnail != null) {
            Glide.with(ApplicationClass.instance).load(myRecipeItem.myRecipeThumbnail).centerCrop().into(binding.imgThumbnail)
        } else {
            binding.imgThumbnail.setImageResource(R.drawable.img_my_recipe_default_small)
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