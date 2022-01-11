package com.recipe.android.recipeapp.src.search.searchBlog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.HeaderSearchBlogBinding
import com.recipe.android.recipeapp.databinding.ItemBlogResultFragRecyclerviewBinding
import com.recipe.android.recipeapp.src.search.searchBlog.model.BlogRecipe
import com.recipe.android.recipeapp.src.search.searchBlog.repository.SearchBlogRepository
import com.recipe.android.recipeapp.utils.StringEscapeUtils

class SearchAdapter(private val repository: SearchBlogRepository, private val clickListener: (BlogRecipe) -> Unit) :
    PagingDataAdapter<BlogRecipe, RecyclerView.ViewHolder>(PostComparator()) {

    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    var returnViewType = 0

    val context = ApplicationClass.instance

    inner class SearchViewHolder(val binding: ItemBlogResultFragRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var clickListener: ((BlogRecipe) -> Unit)? = null

        fun bindWithView(item: BlogRecipe) {
            binding.title = StringEscapeUtils.escapeHtml(item.title)
            binding.item = item
            setScrapIcon(item.userScrapYN == "Y")

            binding.blogThumbnailImg.setOnClickListener {
                clickListener(item)
                when (item.userScrapYN) {
                    "Y" -> {
                        item.userScrapYN = "N"
                        Toast.makeText(context, R.string.scrapCancel, Toast.LENGTH_SHORT).show()
                    }
                    "N" -> {
                        item.userScrapYN = "Y"
                        Toast.makeText(context, R.string.scrapComplete, Toast.LENGTH_SHORT).show()
                    }
                }
                setScrapIcon(item.userScrapYN == "Y")
            }
        }

        private fun setScrapIcon(isScrap: Boolean) {
            binding.btnScrap.setImageResource(
                if (isScrap) R.drawable.ic_favorite_full_white
                else R.drawable.ic_favorite_empty_white
            )
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