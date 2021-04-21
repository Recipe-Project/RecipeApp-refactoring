package com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemRecipeDetail2FragRecyclerviewBinding
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResult
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeProcess
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeResult
import okhttp3.internal.notifyAll

class RecipeProcessRecyclerviewAdapter(private val list : ArrayList<PublicRecipeProcess>) : RecyclerView.Adapter<RecipeProcessRecyclerviewAdapter.CustomViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemRecipeDetail2FragRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.index.text = "0${position+1}"
        holder.process.text = list[position].recipeProcessDc

        if(list[position].recipeProcessImg != " ") {
            Glide.with(ApplicationClass.instance).load(list[position].recipeProcessImg).transform(
                CenterCrop(), RoundedCorners(5)
            ).into(holder.image)
        } else {
            holder.image.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = list.size

    class CustomViewholder(val binding: ItemRecipeDetail2FragRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val index : TextView = binding.recipeIndexTv
        val process : TextView = binding.recipeDetailExplainTv
        val image : ImageView = binding.recipeDetailThumbnailIv
    }
}