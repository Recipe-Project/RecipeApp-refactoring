package com.recipe.android.recipeapp.src.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentSearchBinding
import com.recipe.android.recipeapp.src.search.adapter.PopularKeywordRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.adapter.RecentKeywordRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.adapter.RecentKeywordRecyclerviewAdapter.Companion.DELETE_KEYWORD
import com.recipe.android.recipeapp.src.search.adapter.RecentKeywordRecyclerviewAdapter.Companion.DELETE_LAST_ONE_KEYWORD
import com.recipe.android.recipeapp.src.search.adapter.RecentKeywordRecyclerviewAdapter.Companion.SEARCH_KEYWORD
import com.recipe.android.recipeapp.src.search.models.PopularKeyword
import com.recipe.android.recipeapp.utils.SharedPreferencesManager
import com.recipe.android.recipeapp.utils.gone
import com.recipe.android.recipeapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {

    val TAG = "SearchFragment"

    lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var viewModelFactory: SearchViewModelFactory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        setupViewModel()

        viewModel.popularKeywordList.observe(this as LifecycleOwner, { list ->
            if (list.getOrNull() != null)
                setupPopularKeywordRecyclerView(list.getOrNull()!!)
        })
        setupRecentKeywordRecyclerView()

        setSearchBar()
        clearSearchBar()
    }

    private fun searchKeyword(keyword: String) {
        SharedPreferencesManager.storeRecentSearchKeywordList(keyword)
        postKeywordToServer(keyword)
        val action = SearchFragmentDirections.nextAction("blog", keyword)
        findNavController().navigate(action)
    }

    private fun setSearchBar() {
        binding.barSearch.searchFragEt.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchKeyword(v.text.toString())
                handled = true
            }
            handled
        }
    }

    private fun setupPopularKeywordRecyclerView(keywordList: List<PopularKeyword>) {
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PopularKeywordRecyclerviewAdapter(keywordList) { keyword ->
                searchKeyword(keyword)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
    }

    private fun setupRecentKeywordRecyclerView() {
        val recentKeywordList = SharedPreferencesManager.getRecentSearchKeywordList()

        if (!recentKeywordList.isNullOrEmpty()) {
            setVisibleRecentKeyword()

            binding.rvRecent.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
                adapter = RecentKeywordRecyclerviewAdapter(recentKeywordList) { keyword, type ->
                    when (type) {
                        SEARCH_KEYWORD -> {
                            searchKeyword(keyword)
                        }
                        DELETE_KEYWORD -> {
                            SharedPreferencesManager.deleteRecentKeyword(keyword)
                        }
                        DELETE_LAST_ONE_KEYWORD -> {
                            allClearRecentKeyword()
                        }
                    }
                }
            }
        } else {
            setGoneRecentKeyword()
        }
    }

    fun allClearRecentKeyword() {
        SharedPreferencesManager.clearAllRecentKeyword()
        setGoneRecentKeyword()
    }

    private fun postKeywordToServer(keyword: String) {
        viewModel.postKeyword(keyword)
    }

    private fun clearSearchBar() {
        binding.barSearch.btnCancelSearchBar.setOnClickListener {
            binding.barSearch.searchFragEt.setText("")
        }
    }

    private fun setVisibleRecentKeyword() {
        binding.layoutRecent.visible()
    }

    private fun setGoneRecentKeyword() {
        binding.layoutRecent.gone()
    }
}
