package com.recipe.android.recipeapp.src.search.searchBlog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.HeaderSearchBlogBinding
import com.recipe.android.recipeapp.databinding.ItemBlogResultFragRecyclerviewBinding
import com.recipe.android.recipeapp.src.search.searchBlog.model.BlogRecipe
import com.recipe.android.recipeapp.src.search.searchBlog.repository.SearchBlogRepository
import com.recipe.android.recipeapp.utils.StringEscapeUtils

class SearchAdapter(private val repository: SearchBlogRepository) :
    PagingDataAdapter<BlogRecipe, RecyclerView.ViewHolder>(PostComparator()) {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    var returnViewType = 0

    inner class SearchViewHolder(val binding: ItemBlogResultFragRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(item: BlogRecipe) {
            binding.title = StringEscapeUtils.escapeHtml(item.title)
            binding.item = item
        }
    }

    inner class HeaderViewHolder(val binding: HeaderSearchBlogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindWithView() {
            binding.tvCntAll.text =
                repository.totalCnt.let {
                    if (it > 100) "100+"
                    else it.toString()
                }
        }
    }

    override fun getItemViewType(position: Int): Int {
        returnViewType = when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_ITEM
        }
        return returnViewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(if (position != 0) position - 1 else 0)
        if (item != null) {
            when (returnViewType) {
                TYPE_HEADER -> (holder as HeaderViewHolder).bindWithView()
                else -> (holder as SearchViewHolder).bindWithView(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                HeaderSearchBlogBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            else -> SearchViewHolder(
                ItemBlogResultFragRecyclerviewBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
        }
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