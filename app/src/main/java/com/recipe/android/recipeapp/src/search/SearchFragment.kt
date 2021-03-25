package com.recipe.android.recipeapp.src.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentSearchBinding
import com.recipe.android.recipeapp.src.search.`interface`.SearchKeywordView
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search), SearchKeywordView {

    val TAG = "SearchFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.search_frag_frame_layout, KeywordFragment()).commitAllowingStateLoss()

        binding.searchFragEt.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    searchKeyword()
                    true
                }
                else -> false
            }
        }
    }

    private fun searchKeyword() {
        val keyword : String = binding.searchFragEt.text.toString()
        SearchService(this).getPublicRecipe(keyword)
    }

    override fun onGetPublicRecipeSuccess(response: PublicRecipeResponse) {
        if(response.isSuccess) {
            val publicResultList = response.result
            requireActivity().supportFragmentManager.beginTransaction().replace(R.id.search_frag_frame_layout, SearchResultFragment(publicResultList)).commitAllowingStateLoss()
        }


    }

    override fun onGetPublicRecipeFailure(message: String) {

    }
}