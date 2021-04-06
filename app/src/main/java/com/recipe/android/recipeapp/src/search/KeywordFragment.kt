package com.recipe.android.recipeapp.src.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.api.services.youtube.YouTube
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentKeywordBinding
import com.recipe.android.recipeapp.src.search.`interface`.SearchKeywordView
import com.recipe.android.recipeapp.src.search.adapter.RecentKeywordRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse

class KeywordFragment : BaseFragment<FragmentKeywordBinding>(FragmentKeywordBinding::bind, R.layout.fragment_keyword), SearchKeywordView {

    lateinit var keyword : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 검색 기록이 있어야 최근검색어가 등장
        if (RecentKeywordRecyclerviewAdapter.list.size != 0) {
            binding.keywordFragRecentKeywordTv.visibility = View.VISIBLE
            binding.keywordFragEraseBtn.visibility = View.VISIBLE
            binding.keywordFragRecentKeywordRecylerview.visibility = View.VISIBLE

            val adapter = RecentKeywordRecyclerviewAdapter()
            binding.keywordFragRecentKeywordRecylerview.adapter = adapter

            // 최근 검색어 기록으로 레시피 검색하기
            adapter.recentKeywordItemClick = object : RecentKeywordRecyclerviewAdapter.RecentKeywordItemClick{
                override fun onClick(view: View, position: Int) {
                    keyword = RecentKeywordRecyclerviewAdapter.list[position]
                    SearchService(this@KeywordFragment).getPublicRecipe(keyword)
                    RecentKeywordRecyclerviewAdapter.list.add(keyword)
                }
            }

            // 최근 검색어 삭제하기
            adapter.keywordClearItemClick = object : RecentKeywordRecyclerviewAdapter.KeywordClearItemClick{
                override fun onClick(view: View, position: Int) {
                    Toast.makeText(requireContext(), "검색어가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        // 검색기록 x
        } else {
            binding.keywordFragRecentKeywordTv.visibility = View.GONE
            binding.keywordFragEraseBtn.visibility = View.GONE
            binding.keywordFragRecentKeywordRecylerview.visibility = View.GONE
        }

        // TODO
        // 인기검색어 데이터 조회 후, adapter 연결
    }

    override fun onGetPublicRecipeSuccess(response: PublicRecipeResponse) {
        if(response.isSuccess) {
            val publicResultList = response.result
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.search_frag_frame_layout, SearchResultFragment(publicResultList, keyword)).commitAllowingStateLoss()
        }
    }

    override fun onGetPublicRecipeFailure(message: String) {

    }
}