package com.recipe.android.recipeapp.src.emptyFridge

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentEmptyFridgeBinding
import com.recipe.android.recipeapp.src.emptyFridge.`interface`.EmptyFridgeView
import com.recipe.android.recipeapp.src.emptyFridge.adapter.EmptyFridgeRecyclerviewAdapter
import com.recipe.android.recipeapp.src.emptyFridge.models.EmptyFridgeResponse
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.RecipeDetailActivity
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeScrapResponse

class EmptyFridgeFragment : BaseFragment<FragmentEmptyFridgeBinding>(FragmentEmptyFridgeBinding::bind, R.layout.fragment_empty_fridge), EmptyFridgeView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        EmptyFridgeService(this).tryGetEmptyFridge()

    }

    override fun onGetEmptyFridgeSuccess(response: EmptyFridgeResponse) {
        if(response.result.size != 0) {
            val adapter = EmptyFridgeRecyclerviewAdapter(this)
            binding.emptyFridgeFragRecyclerview.adapter = adapter
            adapter.submitList(response.result)
        }
    }

    override fun onGetEmptyFridgeFailure(message: String) {

    }

    override fun getPublicRecipeDetail(recipeId : Int) {
        val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
        intent.putExtra("index", recipeId)
        requireActivity().startActivity(intent)
    }

    override fun getBlogRecipe(keyword : String) {

    }

    override fun getYoutubeRecipe(keyword : String) {

    }
}