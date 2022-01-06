package com.recipe.android.recipeapp.src.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.recipe.android.recipeapp.src.search.models.PopularKeyword
import com.recipe.android.recipeapp.src.search.network.SearchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository
) : ViewModel() {

    val popularKeywordList = liveData<Result<List<PopularKeyword>>> {
        emitSource(repository.getPopularKeyword().asLiveData())
    }

    fun postKeyword(keyword: String) {
        viewModelScope.launch(Dispatchers.IO) { repository.postKeyword(keyword) }
    }
}