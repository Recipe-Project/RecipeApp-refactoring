package com.recipe.android.recipeapp.src.search

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentSearchBinding
import com.recipe.android.recipeapp.src.search.`interface`.SearchKeywordView
import com.recipe.android.recipeapp.src.search.adapter.RecentKeywordRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.models.PopularKeywordResponse
import com.recipe.android.recipeapp.src.search.models.PostKeywordResponse
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse


class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search), SearchKeywordView {

    val TAG = "SearchFragment"
    private lateinit var keyword : String
    lateinit var inputMethodManager : InputMethodManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.search_frag_frame_layout, KeywordFragment()).commitAllowingStateLoss()

        // 입력받는 방법을 관리하는 Manager 객체를  요청하여 InputMethodmanager에 반환한다.
        inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // IME 키패드에서 검색 버튼 클릭 이벤트
        binding.searchFragEt.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    searchKeyword() // 해당 키워드로 공공 레시피 검색
                    inputMethodManager.hideSoftInputFromWindow(binding.searchFragEt.windowToken, 0) // 검색 버튼 클릭 후 키보드 내리기 ( 점검 필요 )
                    true
                }
                else -> false
            }
        }

        // Edittext의 X버튼 클릭 시 초기화
        binding.searchFragEtCancelBtn.setOnClickListener {
            binding.searchFragEt.text = null
        }

        // 취소 버튼 클릭시 KeywordFragment로 전환
        binding.searchFragCancelBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.search_frag_frame_layout, KeywordFragment()).commitAllowingStateLoss()
            binding.searchFragEt.text = null
        }
    }

    // 해당 키워드로 공공레시피 검색하기
    private fun searchKeyword() {
        keyword = binding.searchFragEt.text.toString()
        SearchService(this).getPublicRecipe(keyword)

        RecentKeywordRecyclerviewAdapter.list.add(keyword) // 최근 검색어 리스트에 검색어 추가
        SearchService(this).postKeyword(keyword) // 검색어 서버로 전송
    }

    override fun onGetPublicRecipeSuccess(response: PublicRecipeResponse) {
        if(response.isSuccess) {
            val publicResultList = response.result
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.search_frag_frame_layout, SearchResultFragment(publicResultList, keyword)).commitAllowingStateLoss()
        }
    }

    override fun onGetPublicRecipeFailure(message: String) {
    }

    override fun onGetPopularKeywordSuccess(response: PopularKeywordResponse) {
        // Nothing
    }

    override fun onGetPopularKeywordFailure(message: String) {
        // Nothing
    }

    override fun onPostKeywordSuccess(response: PostKeywordResponse) {

    }

    override fun onPostKeywordFailure(message: String) {

    }
}