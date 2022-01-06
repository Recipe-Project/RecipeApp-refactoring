package com.recipe.android.recipeapp.src.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemRecentKeywordRecyclerviewBinding

class RecentKeywordRecyclerviewAdapter(
    val keywordList: ArrayList<String>,
    private val listener: (String, Int) -> Unit
) : RecyclerView.Adapter<RecentKeywordRecyclerviewAdapter.CustomViewholder>() {

    companion object {
        val SEARCH_KEYWORD = 0
        val DELETE_KEYWORD = 1
        val DELETE_LAST_ONE_KEYWORD = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemRecentKeywordRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomViewholder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        val item = keywordList[position]
        holder.keyword.text = item
        holder.itemView.setOnClickListener { listener(item, SEARCH_KEYWORD) }
        holder.clearBtn.setOnClickListener {
            if (keywordList.size == 1) listener(item, DELETE_LAST_ONE_KEYWORD)
            else listener(item, DELETE_KEYWORD)
            keywordList.removeAt(holder.adapterPosition)
            notifyItemRemoved(holder.adapterPosition)
        }
    }

    override fun getItemCount(): Int = keywordList.size

    class CustomViewholder(val binding: ItemRecentKeywordRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val keyword: TextView = binding.recentKeywordTv
        val clearBtn: ImageView = binding.btnDelete
    }
}
