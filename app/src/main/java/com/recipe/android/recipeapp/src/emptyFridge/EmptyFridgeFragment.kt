package com.recipe.android.recipeapp.src.emptyFridge

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentEmptyFridgeBinding
import com.recipe.android.recipeapp.src.MainActivity
import com.recipe.android.recipeapp.src.emptyFridge.`interface`.EmptyFridgeView
import com.recipe.android.recipeapp.src.emptyFridge.adapter.EmptyFridgeRecyclerviewAdapter
import com.recipe.android.recipeapp.src.emptyFridge.models.EmptyFridgeResponse
import com.recipe.android.recipeapp.src.search.KeywordFragment
import com.recipe.android.recipeapp.src.search.SearchFragment
import com.recipe.android.recipeapp.src.search.SearchResultFragment
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.RecipeDetailActivity

class EmptyFridgeFragment : BaseFragment<FragmentEmptyFridgeBinding>(FragmentEmptyFridgeBinding::bind, R.layout.fragment_empty_fridge), EmptyFridgeView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog()
        EmptyFridgeService(this).tryGetEmptyFridge()
    }

    override fun onGetEmptyFridgeSuccess(response: EmptyFridgeResponse) {
        dismissLoadingDialog()
        if(response.result.size != 0) {
            val adapter = EmptyFridgeRecyclerviewAdapter(this)
            binding.emptyFridgeFragRecyclerview.adapter = adapter
            adapter.submitList(response.result)
        } else {
            // 냉장고가 텅 비었을 경우, Default 메세지 제공
            binding.emptyFridgeFragRecyclerview.visibility = View.INVISIBLE
            binding.centerLine1.visibility = View.VISIBLE
            binding.emptyFridgeFragDefaultIv.visibility = View.VISIBLE
            binding.centerLine2.visibility = View.VISIBLE
            binding.emptyFridgeFragDefaultTv.visibility = View.VISIBLE
        }
    }

    override fun onGetEmptyFridgeFailure(message: String) {

    }

    override fun getPublicRecipeDetail(id : Int) {
        val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
        intent.putExtra("index", id)
        requireActivity().startActivity(intent)
    }

    override fun getBlogRecipe(keyword : String) {
        // 네비게이션 호스트
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController
        navController.navigate(R.id.searchFragment)
//        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.search_frag_frame_layout, SearchResultFragment(keyword)).commitAllowingStateLoss()
    }

    override fun getYoutubeRecipe(keyword : String) {
        // 네비게이션 호스트
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController
        navController.navigate(R.id.searchFragment)
//        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.search_frag_frame_layout, SearchResultFragment(keyword)).commitAllowingStateLoss()
    }
}