package com.recipe.android.recipeapp.src.search.searchBlog.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentSearchBlogBinding
import com.recipe.android.recipeapp.src.search.searchBlog.adapter.SearchAdapter
import com.recipe.android.recipeapp.src.search.searchBlog.adapter.SearchHeaderAdapter
import com.recipe.android.recipeapp.src.search.searchBlog.repository.SearchBlogRepository
import com.recipe.android.recipeapp.src.search.searchBlog.repository.ViewModelFactory
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
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(repository, keyword)
        )
            .get(SearchBlogViewModel::class.java)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        adapter = SearchAdapter(viewModel)

        binding.rvSearch.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        binding.rvSearch.adapter = adapter

        searchRecipeJob?.cancel()
        searchRecipeJob = lifecycleScope.launch {
            viewModel.recipe.collectLatest {
                adapter.submitData(it)
            }
        }
    }
}