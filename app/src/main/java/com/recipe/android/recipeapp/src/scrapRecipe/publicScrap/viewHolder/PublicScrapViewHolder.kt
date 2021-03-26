package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemScrapPublicRecipeBinding
import com.recipe.android.recipeapp.databinding.ItemScrapRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrap
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrap

class PublicScrapViewHolder(val binding: ItemScrapPublicRecipeBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindWithView(publicScrapItem: PublicScrap) {
        binding.tvTitle.text = publicScrapItem.title
        if (publicScrapItem.thumbnail != null) {
            Glide.with(ApplicationClass.instance).load(publicScrapItem.thumbnail).transform(
                CenterCrop(), RoundedCorners(20)
            ).into(binding.imgThumbnail)
        }
        binding.tvScrapCnt.text = publicScrapItem.scrapCount.toString()
        binding.tvContent.text = publicScrapItem.content
    }
}