package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemScrapPublicRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrap
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.viewHolder.PublicScrapViewHolder
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.RecipeDetailActivity

class PublicScrapRecyclerViewAdapter: RecyclerView.Adapter<PublicScrapViewHolder>() {

    private var publicRecipeItemList = ArrayList<PublicScrap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicScrapViewHolder {
        return PublicScrapViewHolder(
            ItemScrapPublicRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            this,
            publicRecipeItemList
        )
    }

    override fun onBindViewHolder(holder: PublicScrapViewHolder, position: Int) {
        holder.bindWithView(publicRecipeItemList[position])

        holder.itemView.setOnClickListener {
            val index = publicRecipeItemList[position].recipeId
            val intent = Intent(ApplicationClass.instance, RecipeDetailActivity::class.java)
            intent.putExtra("index", index)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ApplicationClass.instance.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return publicRecipeItemList.size
    }

    fun submitList(publicRecipeItemList: ArrayList<PublicScrap>) {
        this.publicRecipeItemList = publicRecipeItemList
        notifyDataSetChanged()
    }
}