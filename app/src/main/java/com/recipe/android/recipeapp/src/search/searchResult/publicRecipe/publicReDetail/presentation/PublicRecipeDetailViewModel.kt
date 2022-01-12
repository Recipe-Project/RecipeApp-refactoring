package com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.recipe.android.recipeapp.common.SimpleRetrofitResponse
import com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.model.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.model.PublicRecipeDetailResult
import com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.repository.PublicRecipeDetailRepository
import com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.presentation.viewpager.PublicPagerAdapter

class PublicRecipeDetailViewModel : ViewModel() {

    val TAG = "PublicRecipeDetailViewModel"

    lateinit var adapter: PublicPagerAdapter

    private val detailResponse = MutableLiveData<PublicRecipeDetailResponse>()
    fun getDetailResponse(): LiveData<PublicRecipeDetailResponse> = detailResponse

    private var _defaultScrapYN = MutableLiveData<Boolean>()
    val defaultScrapYN: LiveData<Boolean>
        get() = _defaultScrapYN

    private var _recipeScrapYN = MutableLiveData<String>()
    val recipeScrapYN: LiveData<String>
        get() = _recipeScrapYN

    fun getPublicRecipeDetailInfo(idx: Int) {
        PublicRecipeDetailRepository.getPublicRecipeDetail(
            idx,
            object : PublicRecipeDetailRepository.GetDataCallback<PublicRecipeDetailResponse> {
                override fun onSuccess(data: PublicRecipeDetailResponse?) {
                    data?.let {
                        detailResponse.postValue(it)
                        _defaultScrapYN.postValue(it.result.userScrapYN == "Y")
                    }
                }

                override fun onFailure(throwable: Throwable) {
                    throwable.printStackTrace()
                }

            })
    }

    fun scrapRecipe(recipeInfo: PublicRecipeDetailResult?) {
        recipeInfo?.run {
            PublicRecipeDetailRepository.scrapRecipe(
                recipeInfo.recipeId,
                object : PublicRecipeDetailRepository.GetDataCallback<SimpleRetrofitResponse> {
                    override fun onSuccess(data: SimpleRetrofitResponse?) {
                        Log.d(TAG, "PublicRecipeDetailViewModel - onSuccess() : 스크랩 성공")
                        Log.d(
                            TAG,
                            "PublicRecipeDetailViewModel - onSuccess() : ${recipeInfo.userScrapYN}"
                        )
                        data?.let {
                            _recipeScrapYN.value = if (recipeInfo.userScrapYN == "Y") "N" else "Y"
                        }
                    }

                    override fun onFailure(throwable: Throwable) {
                        throwable.printStackTrace()
                    }
                }
            )
        }
    }
}