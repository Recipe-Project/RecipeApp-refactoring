package com.recipe.android.recipeapp.src.search

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentSearchResultBinding
import com.recipe.android.recipeapp.src.search.adapter.SearchResultViewPagerAdapter
import com.recipe.android.recipeapp.src.search.blogRecipe.BlogResultFragment
import com.recipe.android.recipeapp.src.search.publicRecipe.PublicResultFragment
import com.recipe.android.recipeapp.src.search.youtubeRecipe.YoutubeResultFragment


class SearchResultFragment(private val keyword: String) : BaseFragment<FragmentSearchResultBinding>(
    FragmentSearchResultBinding::bind,
    R.layout.fragment_search_result
) {

    val TAG = "SearchResultFragment"

    private val recipeTypeList = arrayListOf("블로그", "유튜브", "추천")

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val searchType = arguments?.getString("searchType")
        val searchKeyword = arguments?.getString("searchKeyword")

        val pagerAdapter = SearchResultViewPagerAdapter(this)
        pagerAdapter.addFragment(BlogResultFragment(keyword))
        pagerAdapter.addFragment(YoutubeResultFragment(keyword))
        pagerAdapter.addFragment(PublicResultFragment(keyword))

        binding.searchResultFragViewpager.adapter = pagerAdapter

        if (!searchType.isNullOrEmpty() && !searchKeyword.isNullOrEmpty()) {
            when (searchType) {
                "blog" -> {
                    pagerAdapter.addFragment(BlogResultFragment(searchKeyword))
                    binding.searchResultFragViewpager.setCurrentItem(0, false)
                }
                "youtube" -> {
                    pagerAdapter.addFragment(BlogResultFragment(searchKeyword))
                    binding.searchResultFragViewpager.setCurrentItem(1, false)
                }
            }
        }

        TabLayoutMediator(
            binding.searchResultFragTablayout,
            binding.searchResultFragViewpager
        ) { tab, position ->
            tab.text = recipeTypeList[position]
        }.attach()

    }
}