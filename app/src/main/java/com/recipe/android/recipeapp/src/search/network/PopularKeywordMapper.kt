package com.recipe.android.recipeapp.src.search.network

import com.recipe.android.recipeapp.src.search.models.PopularKeywordResponse
import com.recipe.android.recipeapp.src.search.models.PopularKeyword
import javax.inject.Inject

class PopularKeywordMapper @Inject constructor(): Function1< PopularKeywordResponse, List<PopularKeyword>>{
    override fun invoke(response: PopularKeywordResponse): List<PopularKeyword> {
        return response.result
    }
}