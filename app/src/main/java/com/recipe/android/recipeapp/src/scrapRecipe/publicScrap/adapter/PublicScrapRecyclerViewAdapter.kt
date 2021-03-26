package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemScrapPublicRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrap
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.viewHolder.PublicScrapViewHolder

class PublicScrapRecyclerViewAdapter: RecyclerView.Adapter<PublicScrapViewHolder>() {

    private var publicRecipeItemList = ArrayList<PublicScrap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicScrapViewHolder {
        return PublicScrapViewHolder(
            ItemScrapPublicRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PublicScrapViewHolder, position: Int) {
        holder.bindWithView(publicRecipeItemList[position])
    }

    override fun getItemCount(): Int {
        return publicRecipeItemList.size
    }

    fun submitList(publicRecipeItemList: ArrayList<PublicScrap>) {
        this.publicRecipeItemList = publicRecipeItemList
        notifyDataSetChanged()
    }
}