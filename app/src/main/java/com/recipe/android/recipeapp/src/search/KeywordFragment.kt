package com.recipe.android.recipeapp.src.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentKeywordBinding
import com.recipe.android.recipeapp.src.KeywordListener
import com.recipe.android.recipeapp.src.search.adapter.PopularKeywordRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.adapter.RecentKeywordRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.adapter.RecentKeywordRecyclerviewAdapter.Companion.list
import com.recipe.android.recipeapp.src.search.models.PopularKeywordResponse
import com.recipe.android.recipeapp.src.search.models.PostKeywordResponse

class KeywordFragment :
    BaseFragment<FragmentKeywordBinding>(FragmentKeywordBinding::bind, R.layout.fragment_keyword),
    SearchKeywordView {

    val TAG = "KeywordFragment"

    lateinit var keyword: String
    lateinit var adapter: RecentKeywordRecyclerviewAdapter

    var keywordListener: KeywordListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 검색 기록이 있어야 최근검색어가 등장
        if (RecentKeywordRecyclerviewAdapter.list.size != 0) {
            binding.keywordFragRecentKeywordTv.visibility = View.VISIBLE
            binding.keywordFragEraseBtn.visibility = View.VISIBLE
            binding.keywordFragRecentKeywordRecylerview.visibility = View.VISIBLE

            adapter = RecentKeywordRecyclerviewAdapter()
            binding.keywordFragRecentKeywordRecylerview.adapter = adapter

            // 최근 검색어 기록으로 레시피 검색하기
            adapter.recentKeywordItemClick =
                object : RecentKeywordRecyclerviewAdapter.RecentKeywordItemClick {
                    override fun onClick(view: View, position: Int) {
                        keyword = RecentKeywordRecyclerviewAdapter.list[position]
                        list.removeAt(position)
                        RecentKeywordRecyclerviewAdapter.list.add(keyword) // 최근 검색에 목록에 추가
                        SearchService(this@KeywordFragment).postKeyword(keyword) // 검색어 서버로 전송
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.search_frag_frame_layout, SearchResultFragment(keyword))
                            .commitAllowingStateLoss()
                        keywordListener?.setKeyword(keyword)
                    }
                }

            // 최근 검색어 삭제하기
            adapter.keywordClearItemClick =
                object : RecentKeywordRecyclerviewAdapter.KeywordClearItemClick {
                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(requireContext(), "검색어가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            // 검색기록 x
        } else {
            binding.keywordFragRecentKeywordTv.visibility = View.GONE
            binding.keywordFragEraseBtn.visibility = View.GONE
            binding.keywordFragRecentKeywordRecylerview.visibility = View.GONE
            binding.blank.visibility = View.GONE
        }

        binding.keywordFragEraseBtn.setOnClickListener {
            list.clear()
            adapter.notifyDataSetChanged()
        }

        // 인기검색어 조회
        SearchService(this).getPopularKeyword()
    }

    override fun onGetPopularKeywordSuccess(response: PopularKeywordResponse) {

        if (response.isSuccess) {
            val result = response.result
            val adapter = PopularKeywordRecyclerviewAdapter(result)
            binding.keywordFragPopularKeywordRecylerview.adapter = adapter
            adapter.popularKeywordItemClick =
                object : PopularKeywordRecyclerviewAdapter.PopularKeywordItemClick {
                    override fun onClick(view: View, position: Int) {
                        keyword = result[position].bestKeyword
                        if (RecentKeywordRecyclerviewAdapter.list.contains(keyword)) RecentKeywordRecyclerviewAdapter.list.remove(
                            keyword
                        )
                        RecentKeywordRecyclerviewAdapter.list.add(keyword)
                        SearchService(this@KeywordFragment).postKeyword(keyword) // 검색어 서버로 전송
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.search_frag_frame_layout, SearchResultFragment(keyword))
                            .commitAllowingStateLoss()

                        Log.d(TAG, "KeywordFragment - onClick() : 이게 키워드 : $keyword")
                        keywordListener?.setKeyword(keyword)
                    }
                }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is KeywordListener) {
            keywordListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        if (keywordListener != null) {
            keywordListener = null;
        }
    }

    override fun onGetPopularKeywordFailure(message: String) {

    }

    override fun onPostKeywordSuccess(response: PostKeywordResponse) {

    }

    override fun onPostKeywordFailure(message: String) {

    }
}