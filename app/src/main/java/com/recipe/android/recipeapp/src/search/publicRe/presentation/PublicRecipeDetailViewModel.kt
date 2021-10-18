package com.recipe.android.recipeapp.src.search.publicRe.presentation

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.recipe.android.recipeapp.src.search.publicRe.model.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.publicRe.presentation.viewpager.PublicPagerAdapter
import com.recipe.android.recipeapp.src.search.publicRe.repository.PublicRecipeDetailRepository

class PublicRecipeDetailViewModel(private val fa: FragmentActivity) {

    lateinit var adapter: PublicPagerAdapter

    private val detailResponse = MutableLiveData<PublicRecipeDetailResponse>()
    fun getDetailResponse(): LiveData<PublicRecipeDetailResponse> = detailResponse

    fun getPublicRecipeDetailInfo(idx: Int) {
        PublicRecipeDetailRepository.getPublicRecipeDetail(
            idx,
            object : PublicRecipeDetailRepository.GetDataCallback<PublicRecipeDetailResponse>{
                override fun onSuccess(data: PublicRecipeDetailResponse?) {
                    data?.let {
                        detailResponse.postValue(it)
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    throwable.printStackTrace()
                }

            })
    }


}