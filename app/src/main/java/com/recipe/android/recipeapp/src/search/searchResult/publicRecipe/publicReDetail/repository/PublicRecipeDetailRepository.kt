package com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.repository

import com.recipe.android.recipeapp.common.SimpleRetrofitResponse
import com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.model.PublicRecipeDetailResponse

object PublicRecipeDetailRepository {
    private val publicRecipeDetailDataSource = PublicRecipeDetailDataSource

    fun getPublicRecipeDetail(idx: Int, callback: GetDataCallback<PublicRecipeDetailResponse>){
        PublicRecipeDetailDataSource.getPublicRecipeDetail(idx, callback)
    }

    fun scrapRecipe(idx: Int, callback: GetDataCallback<SimpleRetrofitResponse>){
        PublicRecipeDetailDataSource.scrapRecipe(idx, callback)
    }

    interface GetDataCallback<T> {
        fun onSuccess(data: T?)
        fun onFailure(throwable: Throwable)
    }
}