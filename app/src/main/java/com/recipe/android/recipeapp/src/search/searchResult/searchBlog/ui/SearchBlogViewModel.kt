package com.recipe.android.recipeapp.src.search.searchResult.searchBlog.ui

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.recipe.android.recipeapp.common.SimpleResponse
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.model.BlogRecipe
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.paging.BlogRecipePagingSource
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.repository.SearchBlogRepository
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.model.BlogRecipeScrapRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SearchBlogViewModel(val repository: SearchBlogRepository, val keyword: String) : ViewModel() {

    val recipe: Flow<PagingData<BlogRecipe>> = getBlogRecipe()

    private val _isScrapComplete = MutableLiveData<SimpleResponse>()
    val isScrapComplete: LiveData<SimpleResponse>
        get() = _isScrapComplete

    private fun getBlogRecipe(): Flow<PagingData<BlogRecipe>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { BlogRecipePagingSource(repository, keyword) }
        ).flow.cachedIn(viewModelScope)
    }

    fun postScrap(item: BlogRecipe) {
        viewModelScope.launch(Dispatchers.IO) {
            scrap(item).onEach {
                _isScrapComplete.postValue(it.getOrNull())
            }.collect()
        }
    }

    private suspend fun scrap(item: BlogRecipe): Flow<Result<SimpleResponse>> =
        repository.scrap(
            BlogRecipeScrapRequest(
                item.title,
                item.blogUrl,
                item.description,
                item.blogName,
                item.postDate,
                item.thumbnail
            )
        ).map {
            if (it.isSuccess)
                Result.success(it.getOrNull()!!)
            else
                Result.failure(it.exceptionOrNull()!!)
        }

    companion object {
        const val PAGE_SIZE = 10
    }
}