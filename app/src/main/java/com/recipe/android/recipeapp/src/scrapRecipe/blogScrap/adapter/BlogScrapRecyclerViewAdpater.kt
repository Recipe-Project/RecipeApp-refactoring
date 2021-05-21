package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemScrapRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResult
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.viewHolder.BlogScrapViewHolder

class BlogScrapRecyclerViewAdpater: RecyclerView.Adapter<BlogScrapViewHolder>() {

    private var blogScrapItemList = ArrayList<BlogScrapResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogScrapViewHolder {
        return BlogScrapViewHolder(
            ItemScrapRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BlogScrapViewHolder, position: Int) {
        holder.bindWithView(blogScrapItemList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val uri = Uri.parse(blogScrapItemList[position].blogUrl)
            intent.data = uri
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(ApplicationClass.instance, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return blogScrapItemList.size
    }

    fun submitList (blogScrapItemList: ArrayList<BlogScrapResult>){
        this.blogScrapItemList = blogScrapItemList
        notifyDataSetChanged()
    }

}