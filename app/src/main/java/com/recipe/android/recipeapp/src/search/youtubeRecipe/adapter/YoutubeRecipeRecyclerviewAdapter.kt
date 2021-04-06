package com.recipe.android.recipeapp.src.search.youtubeRecipe.adapter

import android.annotation.SuppressLint
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemRecipeListLoadingBinding
import com.recipe.android.recipeapp.databinding.ItemYoutubeResultFragRecyclerviewBinding
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeResult
import java.text.SimpleDateFormat

class YoutubeRecipeRecyclerviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        private const val Y_TYPE_DATA = 0
        private const val Y_TYPE_LOADING = 1
    }

    interface YoutubeRecipeScrapItemClick {
        fun onClick(view: View, position: Int)
    }
    var youtubeRecipeScrapItemClick : YoutubeRecipeScrapItemClick? = null

    interface YoutubeRecipeItemClick {
        fun onClick(view: View, position: Int)
    }
    var youtubeRecipeItemClick : YoutubeRecipeItemClick? = null

    private val youtubeRecipeList = mutableListOf<YoutubeRecipeResult?>()

    fun setYoutubeRecipe(list: ArrayList<YoutubeRecipeResult>) {
        this.youtubeRecipeList.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addYoutubeRecipe(list: ArrayList<YoutubeRecipeResult>) {
        this.youtubeRecipeList.addAll(list)
        notifyDataSetChanged()
    }

    fun setLoadingView(b: Boolean){
        if(b) {
            android.os.Handler(Looper.getMainLooper()).post {
                this.youtubeRecipeList.add(null)
                notifyItemInserted(youtubeRecipeList.size - 1)
            }
        } else {
            if(this.youtubeRecipeList[youtubeRecipeList.size - 1] == null) {
                this.youtubeRecipeList.removeAt(youtubeRecipeList.size - 1)
                notifyItemRemoved(youtubeRecipeList.size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            Y_TYPE_DATA -> {
                val binding = ItemYoutubeResultFragRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return RecipeViewHolder(binding)
            }
            else -> {
                val binding = ItemRecipeListLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return LoadingViewHolder(binding)
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is RecipeViewHolder) {
            holder.title.text = youtubeRecipeList[position]?.snippet?.title
            holder.channerName.text = youtubeRecipeList[position]?.snippet?.channelTitle
            holder.cnt.text = "없네.."
            holder.playTime.text = "00:00"

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
                    youtubeRecipeScrapItemClick?.onClick(it, position)
                }
            }
            if(youtubeRecipeItemClick != null) {
                holder.layout.setOnClickListener {
                    youtubeRecipeItemClick?.onClick(it, position)
                }
            }

        }
    }

    override fun getItemCount(): Int = youtubeRecipeList.size

    override fun getItemViewType(position: Int): Int {
        return when(youtubeRecipeList[position]) {
            null -> Y_TYPE_LOADING
            else -> Y_TYPE_DATA
        }
    }

    class RecipeViewHolder(val binding: ItemYoutubeResultFragRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title : TextView = binding.youtubeTitleTv
        val channerName : TextView = binding.youtubeNameTv
        val thumbnail : ImageView = binding.youtubeThumbnailImg
        val cnt : TextView = binding.favoriteYoutubeRecipeCntTv
        val postDate : TextView = binding.youtubePostDate
        val scrapBtn : ImageView = binding.youtubeScrapBtn
        val playTime : TextView = binding.youtubePlayTime
        val layout : ConstraintLayout = binding.youtubeResultFragRecyclerviewLayout


    }

    class LoadingViewHolder(val binding: ItemRecipeListLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        val progressBar : ProgressBar = binding.blogRecipeProgressBar
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


