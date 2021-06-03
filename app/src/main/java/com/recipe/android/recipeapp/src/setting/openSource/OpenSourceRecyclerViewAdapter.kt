package com.recipe.android.recipeapp.src.setting.openSource

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.databinding.ItemOpenSourceBinding

class OpenSourceRecyclerViewAdapter:
    RecyclerView.Adapter<OpenSourceRecyclerViewAdapter.OpenSourceViewHolder>() {

    var openSourceList = ArrayList<OpenSource>()

    var isLicenseClick = false

    inner class OpenSourceViewHolder (val binding: ItemOpenSourceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(openSource: OpenSource) {
            binding.tvLicense.visibility = View.GONE
            binding.tvTitle.text = openSource.title
            binding.tvLink.text = openSource.link
            binding.tvLicense.text = openSource.copy

            binding.lvLicense.setOnClickListener {
                isLicenseClick = !isLicenseClick
                if (isLicenseClick) {
                    binding.icLicense.setImageResource(R.drawable.ic_arrow_drop_up)
                    binding.tvLicense.visibility = View.VISIBLE
                } else {
                    binding.icLicense.setImageResource(R.drawable.ic_arrow_drop_down)
                    binding.tvLicense.visibility = View.GONE
                }
            }
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