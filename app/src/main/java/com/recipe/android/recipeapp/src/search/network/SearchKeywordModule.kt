package com.recipe.android.recipeapp.src.search.network

import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import retrofit2.Retrofit

@Module
@InstallIn(FragmentComponent::class)
class SearchKeywordModule {

    @Provides
    fun searchAPI(retrofit: Retrofit): SearchAPI {
        return retrofit.create(SearchAPI::class.java)
    }

    @Provides
    fun retrofit() = sRetrofit
}