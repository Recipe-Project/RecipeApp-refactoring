package com.recipe.android.recipeapp.src.search.youtubeRecipe.adapter

import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemRecipeListLoadingBinding
import com.recipe.android.recipeapp.databinding.ItemYoutubeResultFragRecyclerviewBinding
import com.recipe.android.recipeapp.src.search.blogRecipe.adapter.BlogRecipeRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeResult
import java.text.SimpleDateFormat

class YoutubeRecipeRecyclerviewAdapter : RecyclerView.Adapter<YoutubeRecipeRecyclerviewAdapter.CustomViewHolder>(){

    interface YoutubeRecipeScrapItemClick {
        fun onClick(view: View, position: Int)
    }
    var youtubeRecipeScrapItemClick : YoutubeRecipeScrapItemClick? = null

    interface YoutubeRecipeItemClick {
        fun onClick(view: View, position: Int)
    }
    var youtubeRecipeItemClick : YoutubeRecipeItemClick? = null

    var youtubeRecipeList = mutableListOf<YoutubeRecipeResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeRecipeRecyclerviewAdapter.CustomViewHolder {
        val binding = ItemYoutubeResultFragRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YoutubeRecipeRecyclerviewAdapter.CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: YoutubeRecipeRecyclerviewAdapter.CustomViewHolder, position: Int) {
        val title = youtubeRecipeList[position]?.snippet?.title
        if(title!!.contains("&#39;")) {
            val newTitle = title.replace("&#39;", "'")
            holder.title.text = newTitle
        } else if(title!!.contains("&quot;")) {
            val newTitle = title.replace("&quot;", "\"")
            holder.title.text = newTitle
        } else {
            holder.title.text = title
        }

        holder.channerName.text = youtubeRecipeList[position]?.snippet?.channelTitle

        // postDate String Slicing
        val date = youtubeRecipeList[position]?.snippet?.publishTime
        if(date != null) {
            val strNewDate : String = formatPostDate(date)
            holder.postDate.text = strNewDate
        }

        Glide.with(ApplicationClass.instance).load(youtubeRecipeList[position]?.snippet?.thumbnails?.default?.url).transform(
            CenterCrop(), RoundedCorners(3)
        ).into(holder.thumbnail)

        if(youtubeRecipeScrapItemClick != null) {
            holder.scrapBtn.setOnClickListener {
//                    if(publicResultList[position].userScrapYN == "Y") {
//                        publicResultList[position].userScrapYN = "N"
//                        holder.scrap.setImageResource(R.drawable.ic_favorite_empty_white)
//                        Toast.makeText(context, "스크랩이 취소되었습니다.", Toast.LENGTH_SHORT).show()
//                    } else {
//                        publicResultList[position].userScrapYN = "Y"
//                        holder.scrap.setImageResource(R.drawable.ic_favorite_full_white)
//                        Toast.makeText(context, "스크랩 레시피에 담겼습니다.", Toast.LENGTH_SHORT).show()
//                    }
                youtubeRecipeScrapItemClick?.onClick(it, position)
            }
        }
        if(youtubeRecipeItemClick != null) {
            holder.layout.setOnClickListener {
                youtubeRecipeItemClick?.onClick(it, position)
            }
        }
    }

    override fun getItemCount(): Int = youtubeRecipeList.size


    class CustomViewHolder(val binding: ItemYoutubeResultFragRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title : TextView = binding.youtubeTitleTv
        val channerName : TextView = binding.youtubeNameTv
        val thumbnail : ImageView = binding.youtubeThumbnailImg
        val postDate : TextView = binding.youtubePostDate
        val scrapBtn : ImageView = binding.youtubeScrapBtn
        val layout : ConstraintLayout = binding.youtubeResultFragRecyclerviewLayout
    }

    fun submitList(list : MutableList<YoutubeRecipeResult>) {
        this.youtubeRecipeList = list
        notifyDataSetChanged()
    }

    @SuppressLint("SimpleDateFormat")
    fun formatPostDate(date: String): String {
        val dateBeforeFormat = SimpleDateFormat("yyyy-MM-dd")
        val formatDate = dateBeforeFormat.parse(date)
        if (formatDate != null) {
            val newDateFormat = SimpleDateFormat("yyyy.MM.dd")
            return newDateFormat.format(formatDate)
        }
        return ""
    }
}


