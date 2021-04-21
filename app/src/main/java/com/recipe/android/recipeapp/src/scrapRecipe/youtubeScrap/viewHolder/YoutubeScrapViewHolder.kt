package com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.viewHolder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemScrapRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrap

class YoutubeScrapViewHolder(val binding: ItemScrapRecipeBinding): RecyclerView.ViewHolder(binding.root) {
    fun bindWithView(youtubeScrapItem: YoutubeScrap) {
        binding.tvTitle.text = youtubeScrapItem.title
        if (youtubeScrapItem.thumbnail != null) {
            Glide.with(ApplicationClass.instance).load(youtubeScrapItem.thumbnail).transform(
                CenterCrop(), RoundedCorners(20)
            ).into(binding.imgThumbnail)
        }
        binding.tvScrapCnt.text = youtubeScrapItem.heartCount.toString()
        binding.tvDate.text = youtubeScrapItem.postDate
        binding.tvSource.text = youtubeScrapItem.channelName
    }
}