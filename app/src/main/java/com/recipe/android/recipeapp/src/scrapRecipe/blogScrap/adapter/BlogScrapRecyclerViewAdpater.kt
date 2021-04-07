package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemScrapRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResult
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.viewHolder.BlogScrapViewHolder

class BlogScrapRecyclerViewAdpater: RecyclerView.Adapter<BlogScrapViewHolder>() {

    private var blogScrapItemList = ArrayList<BlogScrapResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogScrapViewHolder {
        return BlogScrapViewHolder(
            ItemScrapRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BlogScrapViewHolder, position: Int) {
        holder.bindWithView(blogScrapItemList[position])
    }

    override fun getItemCount(): Int {
        return blogScrapItemList.size
    }

    fun submitList (blogScrapItemList: ArrayList<BlogScrapResult>){
        this.blogScrapItemList = blogScrapItemList
        notifyDataSetChanged()
    }

}