package com.recipe.android.recipeapp.src.search.blogRecipe.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemBlogResultFragRecyclerviewBinding
import com.recipe.android.recipeapp.databinding.ItemRecipeListLoadingBinding
import com.recipe.android.recipeapp.src.search.blogRecipe.BlogRecipeService
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeListItem
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeResult
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeScrapRequest

class BlogRecipeRecyclerviewAdapter(val context: Context) :
    RecyclerView.Adapter<BlogRecipeRecyclerviewAdapter.CustomViewHolder>() {

    interface BlogRecipeScrapItemClick {
        fun onClick(view: View, position: Int)
    }

    var blogRecipeScrapItemClick: BlogRecipeScrapItemClick? = null

    interface BlogRecipeItemClick {
        fun onClick(view: View, position: Int)
    }

    var blogRecipeItemClick: BlogRecipeItemClick? = null

    var blogRecipeList = mutableListOf<BlogRecipeListItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BlogRecipeRecyclerviewAdapter.CustomViewHolder {
        val binding = ItemBlogResultFragRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BlogRecipeRecyclerviewAdapter.CustomViewHolder,
        position: Int
    ) {

        val title = blogRecipeList[position].title
        if (title.contains("&#39;")) {
            val newTitle = title.replace("&#39;", "'")
            holder.title.text = newTitle
        } else if (title.contains("&quot;")) {
            val newTitle = title.replace("&quot;", "\"")
            holder.title.text = newTitle
        } else {
            holder.title.text = title
        }

        holder.blogName.text = blogRecipeList[position].blogName
        Glide.with(ApplicationClass.instance).load(blogRecipeList[position].thumbnail)
            .transform(CenterCrop(), RoundedCorners(3)).into(holder.thumbnail)
        holder.cnt.text = blogRecipeList[position].userScrapCnt.toString()
        holder.postDate.text = blogRecipeList[position].postDate

        if (blogRecipeScrapItemClick != null) {
            holder.scrapBtn.setOnClickListener {
                if (blogRecipeList[position].userScrapYN == "Y") {
                    blogRecipeList[position].userScrapYN = "N"
                    holder.scrapBtn.setImageResource(R.drawable.ic_favorite_empty_white)
                    Toast.makeText(context, "스크랩이 취소되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    blogRecipeList[position].userScrapYN = "Y"
                    holder.scrapBtn.setImageResource(R.drawable.ic_favorite_full_white)
                    Toast.makeText(context, "스크랩 레시피에 담겼습니다.", Toast.LENGTH_SHORT).show()
                }
                //blogRecipeScrapItemClick?.onClick(it, position)
                BlogRecipeService(null).tryPostAddingScrap(
                    BlogRecipeScrapRequest(
                        blogRecipeList[position].title,
                        blogRecipeList[position].blogUrl,
                        blogRecipeList[position].description,
                        blogRecipeList[position].blogName,
                        blogRecipeList[position].postDate,
                        blogRecipeList[position].thumbnail
                    )
                )
            }
        }

// 첫화면 말고 2페이지, 3페이지로 스크롤 후 클릭한 경우, IndexOutOfBoundsException 에러가 나면서 앱이 꺼져서 클릭함수를 수정했습니다. - 레이나
//        if(blogRecipeItemClick != null) {
//            holder.layout.setOnClickListener {
//                blogRecipeItemClick?.onClick(it, position)
//            }
//        }

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse(blogRecipeList[position].blogUrl)))
        }
    }

    override fun getItemCount(): Int = blogRecipeList.size

    class CustomViewHolder(val binding: ItemBlogResultFragRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.blogTitleTv
        val blogName: TextView = binding.blogNameTv
        val thumbnail: ImageView = binding.blogThumbnailImg
        val cnt: TextView = binding.favoriteBlogRecipeCntTv
        val postDate: TextView = binding.blogPostDate
        val scrapBtn: ImageView = binding.blogScrapBtn
        val layout: ConstraintLayout = binding.blogResultFragRecyclerviewLayout
    }

    fun submitList(list: MutableList<BlogRecipeListItem>) {
        this.blogRecipeList = list
        notifyDataSetChanged()
    }
}


