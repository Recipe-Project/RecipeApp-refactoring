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
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrap
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrapResponse

class YoutubeScrapFragment: BaseFragment<FragmentYoutubeScrapBinding>(FragmentYoutubeScrapBinding::bind, R.layout.fragment_youtube_scrap),
YoutubeScrapFragmentView{

    val TAG = "YoutubeScrapFragment"

    // 스크랩 조회
    private var youtubeScrapItemList = ArrayList<YoutubeScrap>()
    lateinit var youtubeScrapRecyclerViewAdapter: YoutubeScrapRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // 정렬 기준 선택
        val items = arrayOf("최신순", "조회순")

        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, items)
        binding.spinner.adapter = spinnerAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        // 최신순
                    }
                    1 -> {
                        // 조회순
                    }
                    2 -> {
                        // 인기순
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        youtubeScrapRecyclerViewAdapter = YoutubeScrapRecyclerViewAdapter()
        binding.rvScrapRecipe.apply {
            adapter = youtubeScrapRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onStart() {
        super.onStart()

        // sort 수정 필요 - 서버 추가 후 추가 개발 예정
        // 유튜브 스크랩 조회
        YoutubeScrapService(this).getYoutubeScrap(1)
    }

    // 스크랩 조회 api 성공
    override fun onGetYoutubeScrapSuccess(response: YoutubeScrapResponse) {
        if (response.isSuccess) {
            response.result.scrapYoutubeList?.forEach {
                youtubeScrapItemList.add(it)
            }
            youtubeScrapRecyclerViewAdapter.submitList(youtubeScrapItemList)

            binding.tvScrapCnt.text = response.result.scrapYoutubeCount.toString()
            Log.d(TAG, "YoutubeScrapFragment - onGetYoutubeScrapSuccess() : ${response.result.scrapYoutubeCount}")
        }
    }

    // 스크랩 조회 api 실패
    override fun onGetYoutubeScrapFailure(message: String) {
        showCustomToast(getString(R.string.networkError))
        Log.d(TAG, "YoutubeScrapFragment - onGetYoutubeScrapFailure() : $message")
    }
}