package com.recipe.android.recipeapp.src.search.publicRe.repository

import com.recipe.android.recipeapp.src.search.publicRe.model.PublicRecipeDetailResponse

object PublicRecipeDetailRepository {
    private val publicRecipeDetailDataSource = PublicRecipeDetailDataSource

    fun getPublicRecipeDetail(idx: Int, callback: GetDataCallback<PublicRecipeDetailResponse>){
        publicRecipeDetailDataSource.getPublicRecipeDetail(idx, callback)
    }

    interface GetDataCallback<T> {
        fun onSuccess(data: T?)
        fun onFailure(throwable: Throwable)
    }
}