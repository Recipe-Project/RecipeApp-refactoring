package com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentYoutubeScrapBinding
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.`interface`.YoutubeScrapFragmentView
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.adapter.YoutubeScrapRecyclerViewAdapter
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.PostYoutubeScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrap
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrapResponse

class YoutubeScrapFragment: BaseFragment<FragmentYoutubeScrapBinding>(FragmentYoutubeScrapBinding::bind, R.layout.fragment_youtube_scrap),
YoutubeScrapFragmentView{

    val TAG = "YoutubeScrapFragment"

    // 스크랩 조회
    private var youtubeScrapItemList = ArrayList<YoutubeScrap>()
    lateinit var youtubeScrapRecyclerViewAdapter: YoutubeScrapRecyclerViewAdapter

    var scrapCnt = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 유튜브 스크랩 조회
        YoutubeScrapService(this).getYoutubeScrap(1)

        youtubeScrapRecyclerViewAdapter = YoutubeScrapRecyclerViewAdapter(this)
        binding.rvScrapRecipe.apply {
            adapter = youtubeScrapRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    // 스크랩 조회 api 성공
    override fun onGetYoutubeScrapSuccess(response: YoutubeScrapResponse) {
        if (response.isSuccess) {
            response.result.scrapYoutubeList?.forEach {
                youtubeScrapItemList.add(it)
            }
            youtubeScrapRecyclerViewAdapter.submitList(youtubeScrapItemList)

            Log.d(TAG, "YoutubeScrapFragment - onGetYoutubeScrapSuccess() : ${response.result.scrapYoutubeCount}")

            scrapCnt = response.result.scrapYoutubeCount
            binding.tvScrapCnt.text = scrapCnt.toString()
        }
    }

    // 스크랩 조회 api 실패
    override fun onGetYoutubeScrapFailure(message: String) {
        showCustomToast(getString(R.string.networkError))
        Log.d(TAG, "YoutubeScrapFragment - onGetYoutubeScrapFailure() : $message")
    }

    override fun onPostYoutubeScrapSuccess(response: PostYoutubeScrapResponse) {
        if (response.isSuccess) {
            scrapCnt -= 1
            binding.tvScrapCnt.text = scrapCnt.toString()
        }
    }
}