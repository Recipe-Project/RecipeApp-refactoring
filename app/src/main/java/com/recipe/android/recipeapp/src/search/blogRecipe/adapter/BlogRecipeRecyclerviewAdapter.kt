package com.recipe.android.recipeapp.src.search.blogRecipe.adapter

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
import com.recipe.android.recipeapp.databinding.ItemBlogResultFragRecyclerviewBinding
import com.recipe.android.recipeapp.databinding.ItemRecipeListLoadingBinding
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeListItem
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeResult

class BlogRecipeRecyclerviewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        private const val TYPE_DATA = 0
        private const val TYPE_LOADING = 1
    }

    interface BlogRecipeScrapItemClick {
        fun onClick(view: View, position: Int)
    }
    var blogRecipeScrapItemClick : BlogRecipeScrapItemClick? = null

    interface BlogRecipeItemClick {
        fun onClick(view: View, position: Int)
    }
    var blogRecipeItemClick : BlogRecipeItemClick? = null

    private val blogRecipeList = mutableListOf<BlogRecipeListItem?>()

    fun setBlogRecipe(list: ArrayList<BlogRecipeListItem>) {
        this.blogRecipeList.apply {
            clear()
            addAll(list)
        }
        notifyDataSetChanged()
    }

    fun addBlogRecipe(list: ArrayList<BlogRecipeListItem>) {
        this.blogRecipeList.addAll(list)
        notifyDataSetChanged()
    }

    fun setLoadingView(b: Boolean){
        if(b) {
            android.os.Handler(Looper.getMainLooper()).post {
                this.blogRecipeList.add(null)
                notifyItemInserted(blogRecipeList.size - 1)
            }
        } else {
            if(this.blogRecipeList[blogRecipeList.size - 1] == null) {
                this.blogRecipeList.removeAt(blogRecipeList.size - 1)
                notifyItemRemoved(blogRecipeList.size)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            TYPE_DATA -> {
                val binding = ItemBlogResultFragRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
            holder.title.text = blogRecipeList[position]?.title
            holder.blogName.text = blogRecipeList[position]?.blogName
            Glide.with(ApplicationClass.instance).load(blogRecipeList[position]?.thumbnail).transform(CenterCrop(), RoundedCorners(3)).into(holder.thumbnail)
            holder.cnt.text = blogRecipeList[position]?.userScrapCnt.toString()
            holder.postDate.text = blogRecipeList[position]?.postDate

            if(blogRecipeScrapItemClick != null) {
                holder.scrapBtn.setOnClickListener {
                    blogRecipeScrapItemClick?.onClick(it, position)
                }
            }

            if(blogRecipeItemClick != null) {
                holder.layout.setOnClickListener {
                    blogRecipeItemClick?.onClick(it, position)
                }
            }
        }
    }

    override fun getItemCount(): Int = blogRecipeList.size

    override fun getItemViewType(position: Int): Int {
        return when(blogRecipeList[position]) {
            null -> TYPE_LOADING
            else -> TYPE_DATA
        }
    }

    class RecipeViewHolder(val binding: ItemBlogResultFragRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title : TextView = binding.blogTitleTv
        val blogName : TextView = binding.blogNameTv
        val thumbnail : ImageView = binding.blogThumbnailImg
        val cnt : TextView = binding.favoriteBlogRecipeCntTv
        val postDate : TextView = binding.blogPostDate
        val scrapBtn : ImageView = binding.blogScrapBtn
        val layout : ConstraintLayout = binding.blogResultFragRecyclerviewLayout
    }

    class LoadingViewHolder(val binding: ItemRecipeListLoadingBinding) : RecyclerView.ViewHolder(binding.root) {
        val progressBar : ProgressBar = binding.blogRecipeProgressBar
    }
}


