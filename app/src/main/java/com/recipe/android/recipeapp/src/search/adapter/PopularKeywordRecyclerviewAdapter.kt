package com.recipe.android.recipeapp.src.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemPopularKeywordRecyclerviewBinding
import com.recipe.android.recipeapp.src.search.models.PopularKeywordResult

class PopularKeywordRecyclerviewAdapter(val list : ArrayList<PopularKeywordResult>) : RecyclerView.Adapter<PopularKeywordRecyclerviewAdapter.CustomViewholder>() {

    interface PopularKeywordItemClick {
        fun onClick(view: View, position: Int)
    }
    var popularKeywordItemClick : PopularKeywordItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemPopularKeywordRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.index.text = (position + 1).toString()
        holder.keyword.text = list[position].bestKeyword

        if(popularKeywordItemClick != null) {
            holder.layout.setOnClickListener {
                popularKeywordItemClick?.onClick(it, position)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class CustomViewholder(val binding: ItemPopularKeywordRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val index : TextView = binding.popularKeywordIndex
        val keyword : TextView = binding.popularKeywordTv
        val layout : ConstraintLayout = binding.popularKeywordLayout
    }
}