package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentPublicScrapBinding
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.`interface`.PublicScrapFragmentView
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.adapter.PublicScrapRecyclerViewAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 공공레시피 스크랩 조회
        PublicScrapService(this).getPublicScrap(1)

        // 정렬 기준 선택
        val items = arrayOf("최신순", "조회순")

        val spinnerAdapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, items)
        binding.spinner.adapter = spinnerAdapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        publicScrapRecyclerViewAdapter = PublicScrapRecyclerViewAdapter()
        binding.rvScrapRecipe.apply {
            adapter = publicScrapRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

    }

    override fun onStart() {
        super.onStart()

    }

    // 스크랩 조회 api 성공
    override fun onGetPublicScrapSuccess(response: PublicScrapResponse) {

        if (response.isSuccess) {
            publicScrapItemList.clear()
            response.result.scrapRecipeList?.forEach {
                publicScrapItemList.add(it)
            }
            publicScrapRecyclerViewAdapter.submitList(publicScrapItemList)
            Log.d(TAG, "PublicScrapFragment - onGetPublicScrapSuccess() : $publicScrapItemList")

            binding.tvScrapCnt.text = response.result.scrapRecipeCount.toString()
        }
    }

    // 스크랩 조회 api 실패
    override fun onGetPublicScrapFailure(message: String) {
        showCustomToast(getString(R.string.networkError))
        Log.d(TAG, "PublicScrapFragment - onGetPublicScrapFailure() : $message")
    }


}