package com.recipe.android.recipeapp.src.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemPublicResultFragRecyclerviewBinding
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResult

class PublicResultRecyclerviewAdapter(private val publicResultList : ArrayList<PublicRecipeResult>) : RecyclerView.Adapter<PublicResultRecyclerviewAdapter.CustomViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemPublicResultFragRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.title.text = publicResultList[position].title
        holder.content.text = publicResultList[position].description
        Glide.with(ApplicationClass.instance).load(publicResultList[position].thumbnail).into(holder.img)
    }

    override fun getItemCount(): Int = publicResultList.size

    class CustomViewholder(val binding : ItemPublicResultFragRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title : TextView = binding.tvRecipeTitle
        val content : TextView = binding.tvRecipeExplain
        val img : ImageView = binding.imgRecipe
    }
}