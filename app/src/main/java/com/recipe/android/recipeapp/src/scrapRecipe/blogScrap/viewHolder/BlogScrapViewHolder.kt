package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.viewHolder

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemScrapRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.BlogScrapService
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.`interface`.BlogScrapFragmnetView
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.adapter.BlogScrapRecyclerViewAdpater
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResult

class BlogScrapViewHolder(
    val binding: ItemScrapRecipeBinding,
    val blogScrapRecyclerViewAdpater: BlogScrapRecyclerViewAdpater,
    val blogScrapItemList: ArrayList<BlogScrapResult>
) :
    RecyclerView.ViewHolder(binding.root), BlogScrapFragmnetView {

    lateinit var blogScrapItem: BlogScrapResult

    fun bindWithView(blogScrapItem: BlogScrapResult) {

        this.blogScrapItem = blogScrapItem

        binding.tvTitle.text = blogScrapItem.title
        if (blogScrapItem.thumbnail != null) {
            Glide.with(ApplicationClass.instance).load(blogScrapItem.thumbnail).transform(
                CenterCrop(), RoundedCorners(20)
            ).into(binding.imgThumbnail)
        }
        binding.tvScrapCnt.text = blogScrapItem.userScrapCnt.toString()
        binding.tvDate.text = blogScrapItem.postDate
        binding.tvSource.text = blogScrapItem.bloggerName

        binding.btnScrap.setOnClickListener {
            // 스크랩 취소
            val params = HashMap<String, Any>()
            params["title"] = blogScrapItem.title
            params["thumbnail"] = blogScrapItem.thumbnail ?: ""
            params["blogUrl"] = blogScrapItem.blogUrl
            params["description"] = blogScrapItem.description
            params["bloggerName"] = blogScrapItem.bloggerName
            params["postDate"] = blogScrapItem.postDate

            BlogScrapService(this).postBlogScrap(params)
        }
    }

    override fun onGetBlogScrapSuccess(response: BlogScrapResponse) {
    }

    override fun onBlogScrapFailure(message: String) {
        Toast.makeText(ApplicationClass.instance, ApplicationClass.instance.getText(R.string.networkError), Toast.LENGTH_SHORT).show()
    }

    override fun onPostBlogScrapSuccess(response: SimpleResponse) {
        if (response.isSuccess) {
            Toast.makeText(ApplicationClass.instance, "스크랩이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            blogScrapItemList.remove(blogScrapItem)
            blogScrapRecyclerViewAdpater.notifyDataSetChanged()
        } else {
            Toast.makeText(ApplicationClass.instance, ApplicationClass.instance.getText(R.string.networkError), Toast.LENGTH_SHORT).show()
        }
    }
}