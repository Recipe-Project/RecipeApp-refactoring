package com.recipe.android.recipeapp.src.search.searchResult.searchBlog.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentSearchBlogBinding
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.adapter.LoadStateAdapter
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.adapter.SearchAdapter
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.repository.SearchBlogRepository
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.repository.ViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchBlogFragment(private val keyword: String) : BaseFragment<FragmentSearchBlogBinding>(
    FragmentSearchBlogBinding::bind,
    R.layout.fragment_search_blog
) {

    private lateinit var adapter: SearchAdapter
    private lateinit var viewModel: SearchBlogViewModel
    private lateinit var repository: SearchBlogRepository
    private var searchRecipeJob: Job? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repository = SearchBlogRepository()
        setupViewModel()

        setRecyclerView()

        viewModel.isScrapComplete.observe(this as LifecycleOwner, {
            if (!it.isSuccess) showCustomToast(getString(R.string.scrapRetry))
        })
    }

    private fun setRecyclerView() {
        adapter = SearchAdapter(repository) {item ->
            viewModel.postScrap(item)
        }

        binding.rvSearch.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvSearch.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { adapter.retry() },
            footer = LoadStateAdapter { adapter.retry() }
        )

        adapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Log.d(
                    "SearchBlogFragment", "SearchBlogFragment - setRecyclerView() : " +
                            "\uD83D\uDE28 Wooops ${it.error}"
                )
            }
        }

        binding.btnRetry.setOnClickListener { adapter.retry() }

        searchRecipeJob?.cancel()
        searchRecipeJob = lifecycleScope.launch {
            viewModel.recipe.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository, keyword)
        )
            .get(SearchBlogViewModel::class.java)
    }
}