package com.recipe.android.recipeapp.src.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemRecentKeywordRecyclerviewBinding

class RecentKeywordRecyclerviewAdapter() : RecyclerView.Adapter<RecentKeywordRecyclerviewAdapter.CustomViewholder>(){

    interface RecentKeywordItemClick{
        fun onClick(view: View, position: Int)
    }
    var recentKeywordItemClick : RecentKeywordItemClick? = null

    interface KeywordClearItemClick {
        fun onClick(view: View, position: Int)
    }
    var keywordClearItemClick : KeywordClearItemClick? = null

    companion object{
        var list = mutableListOf<String>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemRecentKeywordRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.keyword.text = list[position]

        if(recentKeywordItemClick != null) {
            holder.layout.setOnClickListener {
                recentKeywordItemClick?.onClick(it, position)
            }
        }

        if(keywordClearItemClick != null) {
            holder.clearBtn.setOnClickListener {
                keywordClearItemClick?.onClick(it, position)

                list.removeAt(position)
                notifyItemRemoved(list.size)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class CustomViewholder(val binding: ItemRecentKeywordRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)  {
        val keyword : TextView = binding.recentKeywordTv
        val clearBtn : ImageView = binding.recentKeywordClearBtn
        val layout : ConstraintLayout = binding.recentKeywordLayout
    }
}
