package com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.viewHolder

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemScrapRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.YoutubeScrapService
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.`interface`.YoutubeScrapFragmentView
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.adapter.YoutubeScrapRecyclerViewAdapter
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.PostYoutubeScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrap
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrapResponse

class YoutubeScrapViewHolder(
    val binding: ItemScrapRecipeBinding,
    val youtubeScrapRecyclerViewAdapter: YoutubeScrapRecyclerViewAdapter,
    val scrapRecipeItemList: ArrayList<YoutubeScrap>
) : RecyclerView.ViewHolder(binding.root),
    YoutubeScrapFragmentView {

    lateinit var youtubeScrapItem: YoutubeScrap

    fun bindWithView(youtubeScrapItem: YoutubeScrap) {

        this.youtubeScrapItem = youtubeScrapItem

        val title = youtubeScrapItem.title
        if(title.contains("&#39;")) {
            val newTitle = title.replace("&#39;", "'")
            binding.tvTitle.text = newTitle
        } else if(title.contains("&quot;")) {
            val newTitle = title.replace("&quot;", "\"")
            binding.tvTitle.text = newTitle
        } else {
            binding.tvTitle.text = title
        }

        if (youtubeScrapItem.thumbnail != null) {
            Glide.with(ApplicationClass.instance).load(youtubeScrapItem.thumbnail).transform(
                CenterCrop(), RoundedCorners(20)
            ).into(binding.imgThumbnail)
        }
        binding.tvScrapCnt.text = youtubeScrapItem.heartCount.toString()
        binding.tvDate.text = youtubeScrapItem.postDate
        binding.tvSource.text = youtubeScrapItem.channelName

        binding.btnScrap.setOnClickListener {
            // 스크랩 취소
            val params = HashMap<String, Any>()
            params["youtubeId"] = youtubeScrapItem.youtubeId
            params["title"] = youtubeScrapItem.title
            params["thumbnail"] = youtubeScrapItem.thumbnail ?: ""
            params["youtubeUrl"] = youtubeScrapItem.youtubeUrl
            params["postDate"] = youtubeScrapItem.postDate
            params["channelName"] = youtubeScrapItem.channelName
            params["playTime"] = youtubeScrapItem.playTime

            YoutubeScrapService(this).postYoutubeScrap(params)
        }
    }

    override fun onGetYoutubeScrapSuccess(response: YoutubeScrapResponse) {
    }

    override fun onGetYoutubeScrapFailure(message: String) {
    }

    override fun onPostYoutubeScrapSuccess(response: PostYoutubeScrapResponse) {
        if (response.isSuccess) {
            Toast.makeText(ApplicationClass.instance, "스크랩이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            scrapRecipeItemList.remove(youtubeScrapItem)
            youtubeScrapRecyclerViewAdapter.notifyDataSetChanged()
        } else {
            Toast.makeText(
                ApplicationClass.instance,
                ApplicationClass.instance.getText(R.string.networkError),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}