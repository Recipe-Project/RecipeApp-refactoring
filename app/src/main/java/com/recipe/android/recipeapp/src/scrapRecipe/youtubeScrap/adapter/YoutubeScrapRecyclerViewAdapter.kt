package com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemScrapRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrap
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.viewHolder.YoutubeScrapViewHolder

class YoutubeScrapRecyclerViewAdapter: RecyclerView.Adapter<YoutubeScrapViewHolder>() {

    private var scrapRecipeItemList = ArrayList<YoutubeScrap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeScrapViewHolder {
        return YoutubeScrapViewHolder(
            ItemScrapRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: YoutubeScrapViewHolder, position: Int) {
        holder.bindWithView(scrapRecipeItemList[position])
    }

    override fun getItemCount(): Int {
        return scrapRecipeItemList.size
    }

    fun submitList(scrapRecipeItemList: ArrayList<YoutubeScrap>) {
        this.scrapRecipeItemList = scrapRecipeItemList
        notifyDataSetChanged()
    }
}