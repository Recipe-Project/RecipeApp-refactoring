package com.recipe.android.recipeapp.src.search.searchBlog.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.recipe.android.recipeapp.src.search.searchBlog.model.BlogRecipe
import com.recipe.android.recipeapp.src.search.searchBlog.paging.BlogRecipePagingSource
import com.recipe.android.recipeapp.src.search.searchBlog.repository.SearchBlogRepository
import kotlinx.coroutines.flow.Flow

class SearchBlogViewModel(val repository: SearchBlogRepository, val keyword: String): ViewModel() {
    val recipe: Flow<PagingData<BlogRecipe>> = getBlogRecipe()

    private fun getBlogRecipe(): Flow<PagingData<BlogRecipe>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { BlogRecipePagingSource(repository, keyword) }
        ).flow.cachedIn(viewModelScope)
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}