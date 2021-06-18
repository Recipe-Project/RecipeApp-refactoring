package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentPublicScrapBinding
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.`interface`.PublicScrapFragmentView
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.adapter.PublicScrapRecyclerViewAdapter
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PostPublicScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrap
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrapResponse

class PublicScrapFragment : BaseFragment<FragmentPublicScrapBinding>(
    FragmentPublicScrapBinding::bind,
    R.layout.fragment_public_scrap
), PublicScrapFragmentView {

    val TAG = "PublicScrapFragment"

    // 스크랩 조회
    private var publicScrapItemList = ArrayList<PublicScrap>()
    lateinit var publicScrapRecyclerViewAdapter: PublicScrapRecyclerViewAdapter

    var scrapCnt = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 공공레시피 스크랩 조회
        PublicScrapService(this).getPublicScrap(1)

        publicScrapRecyclerViewAdapter = PublicScrapRecyclerViewAdapter(this)
        binding.rvScrapRecipe.apply {
            adapter = publicScrapRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

    }

    // 스크랩 조회 api 성공
    override fun onGetPublicScrapSuccess(response: PublicScrapResponse) {
        if (response.isSuccess && activity != null) {
            publicScrapItemList.clear()
            response.result.scrapRecipeList?.forEach {
                publicScrapItemList.add(it)
            }
            publicScrapRecyclerViewAdapter.submitList(publicScrapItemList)
            Log.d(TAG, "PublicScrapFragment - onGetPublicScrapSuccess() : $publicScrapItemList")

            scrapCnt = response.result.scrapRecipeCount
            binding.tvScrapCnt.text = scrapCnt.toString()
        }
    }

    // 스크랩 조회 api 실패
    override fun onGetPublicScrapFailure(message: String) {
        showCustomToast(getString(R.string.networkError))
        Log.d(TAG, "PublicScrapFragment - onGetPublicScrapFailure() : $message")
    }

    override fun onPostPublicScrapSuccess(postPublicScrapResponse: PostPublicScrapResponse) {
        if (postPublicScrapResponse.isSuccess) {
            scrapCnt -= 1
            binding.tvScrapCnt.text = scrapCnt.toString()
        }
    }


}