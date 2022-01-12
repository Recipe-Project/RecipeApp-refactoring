package com.recipe.android.recipeapp.src.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.recipe.android.recipeapp.src.search.network.SearchRepository
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(
    private val repository: SearchRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repository) as T
    }
}