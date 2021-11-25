package com.recipe.android.recipeapp.src.search.searchBlog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemBlogResultFragRecyclerviewBinding
import com.recipe.android.recipeapp.src.search.searchBlog.ui.SearchBlogViewModel
import com.recipe.android.recipeapp.src.search.searchBlog.model.BlogRecipe

class SearchAdapter(private val viewModel: SearchBlogViewModel) :
    PagingDataAdapter<BlogRecipe, SearchAdapter.SearchViewHolder>(PostComparator()) {
    inner class SearchViewHolder(val binding: ItemBlogResultFragRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(viewModel: SearchBlogViewModel, item: BlogRecipe) {
            binding.item = item
        }
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bindWithView(this.viewModel, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemBlogResultFragRecyclerviewBinding.inflate(layoutInflater, parent, false)
        return SearchViewHolder(binding)
    }
}

class PostComparator : DiffUtil.ItemCallback<BlogRecipe>() {
    override fun areItemsTheSame(oldItem: BlogRecipe, newItem: BlogRecipe): Boolean {
        return oldItem.blogUrl == newItem.blogUrl
    }

    override fun areContentsTheSame(oldItem: BlogRecipe, newItem: BlogRecipe): Boolean {
        return oldItem == newItem
    }

}