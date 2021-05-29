package com.recipe.android.recipeapp.src.setting.openSource

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemOpenSourceBinding

class OpenSourceRecyclerViewAdapter:
    RecyclerView.Adapter<OpenSourceRecyclerViewAdapter.OpenSourceViewHolder>() {

    var openSourceList = ArrayList<OpenSource>()

    inner class OpenSourceViewHolder (val binding: ItemOpenSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(openSource: OpenSource) {
            binding.tvTitle.text = openSource.title
            binding.tvLink.text = openSource.link
            binding.tvCopy.text = openSource.copy
            binding.tvLicense.text = openSource.license
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpenSourceViewHolder {
        return OpenSourceViewHolder(
            ItemOpenSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OpenSourceViewHolder, position: Int) {
        holder.bindWithView(openSourceList[position])
    }

    override fun getItemCount(): Int = openSourceList.size

    fun submitList(openSourceList: ArrayList<OpenSource>) {
        this.openSourceList = openSourceList
        notifyDataSetChanged()
    }
}