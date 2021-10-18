package com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemScrapRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.YoutubeScrapFragmentView
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrap
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.viewHolder.YoutubeScrapViewHolder
import com.recipe.android.recipeapp.src.search.youtubeRecipe.YoutubeRecipeView
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeResponse
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeScrapResponse

class YoutubeScrapRecyclerViewAdapter(val view: YoutubeScrapFragmentView) : RecyclerView.Adapter<YoutubeScrapViewHolder>(),
    YoutubeRecipeView {

    private var scrapRecipeItemList = ArrayList<YoutubeScrap>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeScrapViewHolder {
        return YoutubeScrapViewHolder(
            ItemScrapRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            this,
            scrapRecipeItemList,
            view
        )
    }

    override fun onBindViewHolder(holder: YoutubeScrapViewHolder, position: Int) {
        holder.bindWithView(scrapRecipeItemList[position])

        holder.itemView.setOnClickListener {
            holder.itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                val uri = Uri.parse(scrapRecipeItemList[position].youtubeUrl)
                intent.data = uri
                intent.setPackage("com.google.android.youtube")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                ContextCompat.startActivity(ApplicationClass.instance, intent, null)
            }
        }
    }

    override fun getItemCount(): Int {
        return scrapRecipeItemList.size
    }

    fun submitList(scrapRecipeItemList: ArrayList<YoutubeScrap>) {
        this.scrapRecipeItemList = scrapRecipeItemList
        notifyDataSetChanged()
    }

    override fun onGetYoutubeRecipeSuccess(response: YoutubeRecipeResponse) {

    }
    override fun onGetYoutubeRecipeFailure(message: String) {
    }

    override fun onPostYoutubeRecipeScrapSuccess(response: YoutubeRecipeScrapResponse) {
    }

    override fun onPostYoutubeRecipeScrapFailure(message: String) {
    }
}