package com.recipe.android.recipeapp.src.scrapRecipe.blogScrap

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentBlogScrapBinding
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.`interface`.BlogScrapFragmnetView
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.adapter.BlogScrapRecyclerViewAdpater
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.models.BlogScrapResult

class BlogScrapFragment: BaseFragment<FragmentBlogScrapBinding>(FragmentBlogScrapBinding::bind, R.layout.fragment_blog_scrap),
BlogScrapFragmnetView{

    val TAG = "BlogScrapFragment"

    // 스크랩 조회
    private var blogScrapItemList = ArrayList<BlogScrapResult>()
    private lateinit var blogScrapRecyclerViewAdapter: BlogScrapRecyclerViewAdpater

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 블로그 스크랩 조회
        BlogScrapService(this).getBlogScrap(1)

        // 정렬 기준 선택
        val items = arrayOf("최신순", "조회순")

        blogScrapRecyclerViewAdapter = BlogScrapRecyclerViewAdpater()
        binding.rvScrapRecipe.apply {
            adapter = blogScrapRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onGetBlogScrapSuccess(response: BlogScrapResponse) {
        if (response.isSuccess) {
            response.result?.forEach {
                blogScrapItemList.add(it)
            }
            blogScrapRecyclerViewAdapter.submitList(blogScrapItemList)

            binding.tvScrapCnt.text = response.result?.size.toString()
        }
    }

    override fun onGetBlogScrapFailure(message: String) {
        showCustomToast(getString(R.string.networkError))
        Log.d(TAG, "BlogScrapFragment - onGetBlogScrapFailure() : $message")
    }


}