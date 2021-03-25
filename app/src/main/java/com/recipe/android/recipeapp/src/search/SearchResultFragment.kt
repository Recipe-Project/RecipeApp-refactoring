package com.recipe.android.recipeapp.src.search

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentSearchResultBinding
import com.recipe.android.recipeapp.src.search.adapter.SearchResultViewPagerAdapter
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResult

class SearchResultFragment(private val publicResultList : ArrayList<PublicRecipeResult>) : BaseFragment<FragmentSearchResultBinding>(FragmentSearchResultBinding::bind, R.layout.fragment_search_result) {

    private val recipeTypeList = arrayListOf("동영상", "블로그", "공공")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val pagerAdapter = SearchResultViewPagerAdapter(this)
        pagerAdapter.addFragment(YoutubeResultFragment())
        pagerAdapter.addFragment(BlogResultFragment())
        pagerAdapter.addFragment(PublicResultFragment(publicResultList))
        binding.searchResultFragViewpager.adapter = pagerAdapter

        TabLayoutMediator(binding.searchResultFragTablayout, binding.searchResultFragViewpager) { tab, position ->
            tab.text =recipeTypeList[position]
        }.attach()
    }
}