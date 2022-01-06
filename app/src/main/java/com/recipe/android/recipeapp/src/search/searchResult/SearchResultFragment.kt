package com.recipe.android.recipeapp.src.search.searchResult

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentSearchResultBinding
import com.recipe.android.recipeapp.src.search.searchResult.blogRecipe.BlogResultFragment
import com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.PublicResultFragment
import com.recipe.android.recipeapp.src.search.searchResult.youtubeRecipe.YoutubeResultFragment


class SearchResultFragment() : BaseFragment<FragmentSearchResultBinding>(
    FragmentSearchResultBinding::bind,
    R.layout.fragment_search_result
) {
    val TAG = "SearchResultFragment"

    private val recipeTypeList = arrayListOf("블로그", "유튜브", "추천")

    lateinit var searchType: String
    lateinit var searchKeyword: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val args: SearchResultFragmentArgs by navArgs()
        searchType = args.searchType ?: ""
        searchKeyword = args.searchKeyword ?: ""

        binding.keyword = searchKeyword

        val pagerAdapter = SearchResultViewPagerAdapter(this)
        pagerAdapter.addFragment(BlogResultFragment(searchKeyword))
        pagerAdapter.addFragment(YoutubeResultFragment(searchKeyword))
        pagerAdapter.addFragment(PublicResultFragment(searchKeyword))

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

        backSearchFragment()

        setSearchBar()
        clearSearchBar()
    }

    private fun backSearchFragment() {
        binding.barSearch.btnBack.setOnClickListener{
            val action = SearchResultFragmentDirections.backAction()
            findNavController().navigate(action)
        }
    }

    private fun clearSearchBar() {
        binding.barSearch.btnCancelSearchBar.setOnClickListener {
            binding.barSearch.searchFragEt.setText("")
        }
    }

    private fun setSearchBar() {
        binding.barSearch.searchFragEt.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val action = SearchResultFragmentDirections.refreshAction("blog", v.text.toString())
                findNavController().navigate(action)
                handled = true
            }
            handled
        }
    }
}