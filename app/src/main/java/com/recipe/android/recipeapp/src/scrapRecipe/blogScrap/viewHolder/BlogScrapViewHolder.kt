package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.viewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemScrapRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResult
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrap

class BlogScrapViewHolder(val binding: ItemScrapRecipeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bindWithView(blogScrapItem: BlogScrapResult) {
        binding.tvTitle.text = blogScrapItem.title
        if (blogScrapItem.thumbnail != null) {
            Glide.with(ApplicationClass.instance).load(blogScrapItem.thumbnail).transform(
                CenterCrop(), RoundedCorners(20)
            ).into(binding.imgThumbnail)
        }
        binding.tvScrapCnt.text = blogScrapItem.userScrapCnt.toString()
        binding.tvDate.text = blogScrapItem.postDate
        binding.tvSource.text = blogScrapItem.bloggerName

        // 재생시간 삭제
        binding.tvPlayTime.visibility = View.GONE
    }
}