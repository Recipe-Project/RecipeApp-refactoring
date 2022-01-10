package com.recipe.android.recipeapp.src.search.searchBlog.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.recipe.android.recipeapp.src.search.searchBlog.ui.SearchBlogViewModel

class ViewModelFactory(private val repository: SearchBlogRepository, private val keyword: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchBlogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SearchBlogViewModel(repository, keyword) as T
        }
        throw IllegalArgumentException("Exception: Unknown ViewModel class")
    }
}