package com.recipe.android.recipeapp.src.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemPopularKeywordRecyclerviewBinding
import com.recipe.android.recipeapp.src.search.models.PopularKeyword

class PopularKeywordRecyclerviewAdapter(
    val list: List<PopularKeyword>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<PopularKeywordRecyclerviewAdapter.CustomViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemPopularKeywordRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomViewholder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        val item = list[position]
        holder.index.text = (position + 1).toString()
        holder.keyword.text = item.bestKeyword
        holder.itemView.setOnClickListener { listener(item.bestKeyword) }
    }

    override fun getItemCount(): Int = list.size

    class CustomViewholder(val binding: ItemPopularKeywordRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val index: TextView = binding.popularKeywordIndex
        val keyword: TextView = binding.popularKeywordTv
        val layout: ConstraintLayout = binding.popularKeywordLayout
    }
}

