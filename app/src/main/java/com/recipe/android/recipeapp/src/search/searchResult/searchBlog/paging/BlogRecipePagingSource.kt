package com.recipe.android.recipeapp.src.search.searchResult.searchBlog.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.model.BlogRecipe
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.repository.SearchBlogRepository
import com.recipe.android.recipeapp.src.search.searchResult.searchBlog.ui.SearchBlogViewModel
import retrofit2.HttpException
import java.io.IOException

const val STARTING_PAGE_INDEX = 1

class BlogRecipePagingSource(
    private val repository: SearchBlogRepository,
    private val keyword: String
) : PagingSource<Int, BlogRecipe>() {
    override fun getRefreshKey(state: PagingState<Int, BlogRecipe>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BlogRecipe> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX

            val response = repository.getBlogRecipe(keyword, SearchBlogViewModel.PAGE_SIZE, position)
            checkNotNull(response)

            repository.totalCnt = response.total

            val prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1
            val nextKey = if (response.blogList.isEmpty()) null else position + SearchBlogViewModel.PAGE_SIZE
            LoadResult.Page(
                data = response.blogList,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

}