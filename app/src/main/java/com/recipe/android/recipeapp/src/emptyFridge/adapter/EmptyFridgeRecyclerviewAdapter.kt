package com.recipe.android.recipeapp.src.emptyFridge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemEmptyFridgeRecyclerviewBinding
import com.recipe.android.recipeapp.src.emptyFridge.EmptyFridgeView
import com.recipe.android.recipeapp.src.emptyFridge.models.EmptyFridgeResult

class EmptyFridgeRecyclerviewAdapter(val view : EmptyFridgeView) : RecyclerView.Adapter<EmptyFridgeRecyclerviewAdapter.CustomViewholder>() {

    var emptyFridgeList = mutableListOf<EmptyFridgeResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemEmptyFridgeRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding, view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        val realPosition = holder.adapterPosition
        if (emptyFridgeList.size > 0 && realPosition < emptyFridgeList.size) {
            holder.bindWithView(emptyFridgeList[realPosition])
            holder.public.setOnClickListener {
                view.getPublicRecipeDetail(emptyFridgeList[realPosition].recipeId)
            }
            holder.blog.setOnClickListener {
                view.getBlogRecipe(emptyFridgeList[realPosition].title)
            }
            holder.youtube.setOnClickListener {
                view.getYoutubeRecipe(emptyFridgeList[realPosition].title)
            }
        }
    }

    override fun getItemCount(): Int = emptyFridgeList.size

    class CustomViewholder(val binding : ItemEmptyFridgeRecyclerviewBinding, val view : EmptyFridgeView) : RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(result : EmptyFridgeResult) {
            Glide.with(ApplicationClass.instance).load(result.thumbnail).transform(CenterCrop(), RoundedCorners(20)).into(binding.thumbnailIv)
            binding.titleTv.text = result.title
            binding.contentTv.text = result.content
            binding.scrapCountTv.text = result.scrapCount.toString()
            binding.cookingTimeTv.text = result.cookingTime
        }
        val public : ConstraintLayout = binding.publicRecipeLayout
        val blog : ConstraintLayout = binding.blogRecipeLayout
        val youtube : ConstraintLayout = binding.youtubeRecipeLayout
    }

    fun submitList(emptyFridgeList : MutableList<EmptyFridgeResult>) {
        this.emptyFridgeList = emptyFridgeList
        notifyDataSetChanged()
    }
}