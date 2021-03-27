package com.recipe.android.recipeapp.src.search.publicRecipe.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemPublicResultFragRecyclerviewBinding
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResult

class PublicResultRecyclerviewAdapter(private val publicResultList : ArrayList<PublicRecipeResult>) : RecyclerView.Adapter<PublicResultRecyclerviewAdapter.CustomViewholder>() {

    interface PublicRecipeItemClick {
        fun onClick(view: View, position: Int)
    }
    var publicRecipeItemClick : PublicRecipeItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemPublicResultFragRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.title.text = publicResultList[position].title
        holder.content.text = publicResultList[position].description
        Glide.with(ApplicationClass.instance).load(publicResultList[position].thumbnail).into(holder.img)
        holder.cnt.text = publicResultList[position].userScrapCnt.toString()

        // Click Event
        if(publicRecipeItemClick != null) {
            holder.layout.setOnClickListener {
                publicRecipeItemClick?.onClick(it, position)
            }
        }

    }

    override fun getItemCount(): Int = publicResultList.size

    class CustomViewholder(val binding : ItemPublicResultFragRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title : TextView = binding.recipeTitleTv
        val content : TextView = binding.recipeExplainTv
        val img : ImageView = binding.recipeImg
        val cnt : TextView = binding.favoriteRecipeCntTv
        val layout : ConstraintLayout = binding.publicResultFragRecyclerviewLayout
    }
}