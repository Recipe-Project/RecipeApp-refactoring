package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentBlogScrapBinding
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.adapter.BlogScrapRecyclerViewAdpater
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResult

interface BlogScrapFragmnetView {
    fun onGetBlogScrapSuccess(response: BlogScrapResponse)
    fun onBlogScrapFailure(message: String)

    fun onPostBlogScrapSuccess(response: SimpleResponse)
}

class BlogScrapFragment: BaseFragment<FragmentBlogScrapBinding>(FragmentBlogScrapBinding::bind, R.layout.fragment_blog_scrap),
BlogScrapFragmnetView{

    val TAG = "BlogScrapFragment"

    // 스크랩 조회
    private var blogScrapItemList = ArrayList<BlogScrapResult>()
    private lateinit var blogScrapRecyclerViewAdapter: BlogScrapRecyclerViewAdpater

    var scrapCnt = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 블로그 스크랩 조회
        BlogScrapService(this).getBlogScrap(1)

        blogScrapRecyclerViewAdapter = BlogScrapRecyclerViewAdpater(this)
        binding.rvScrapRecipe.apply {
            adapter = blogScrapRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onGetBlogScrapSuccess(response: BlogScrapResponse) {
        if (response.isSuccess && activity != null) {
            response.result?.forEach {
                blogScrapItemList.add(it)
            }
            blogScrapRecyclerViewAdapter.submitList(blogScrapItemList)

            scrapCnt = response.result?.size ?: 0
            binding.tvScrapCnt.text = scrapCnt.toString()

        } else onBlogScrapFailure(response.message)
    }

    override fun onBlogScrapFailure(message: String) {
        showCustomToast(getString(R.string.networkError))
        Log.d(TAG, "BlogScrapFragment - onGetBlogScrapFailure() : $message")
    }

    override fun onPostBlogScrapSuccess(response: SimpleResponse) {
        if (response.isSuccess) {
            scrapCnt -= 1
            binding.tvScrapCnt.text = scrapCnt.toString()
        }
    }


}