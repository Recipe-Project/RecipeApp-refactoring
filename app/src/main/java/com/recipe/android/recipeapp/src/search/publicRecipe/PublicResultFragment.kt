package com.recipe.android.recipeapp.src.search.publicRecipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentPublicResultBinding
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.adapter.PublicResultRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResult
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeScrapView
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeView
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.RecipeDetailActivity

class PublicResultFragment(private val keyword : String)
    : BaseFragment<FragmentPublicResultBinding>(FragmentPublicResultBinding::bind, R.layout.fragment_public_result), PublicRecipeView,
    PublicRecipeScrapView {

    lateinit var publicRecipeRecyclerviewAdapter: PublicResultRecyclerviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog()
        publicRecipeRecyclerviewAdapter = PublicResultRecyclerviewAdapter(requireContext())
        binding.publicResultFragRecylerview.adapter = publicRecipeRecyclerviewAdapter
        PublicRecipeService(this).getPublicRecipe(keyword)
    }

    override fun onGetPublicRecipeSuccess(response: PublicRecipeResponse) {
        if(response.isSuccess) {
            dismissLoadingDialog()

            val result = response.result

            binding.publicFragItemCnt.text = result.size.toString()
            binding.publicFragItemCntUnit.visibility = View.VISIBLE

            publicRecipeRecyclerviewAdapter.submitList(result)

            // 공공레시피 상세 조회
            publicRecipeRecyclerviewAdapter.publicRecipeItemClick = object : PublicResultRecyclerviewAdapter.PublicRecipeItemClick{
                override fun onClick(view: View, position: Int) {
                    val index = result[position].recipeId
                    val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                    intent.putExtra("index", index)
                    requireActivity().startActivity(intent)
                }
            }

            // 공공레시피 스크랩
            publicRecipeRecyclerviewAdapter.publicRecipeScrapClick = object : PublicResultRecyclerviewAdapter.PublicRecipeScrapClick {
                override fun onClick(view: View, position: Int) {
                    val recipeId = publicRecipeRecyclerviewAdapter.publicResultList[position].recipeId
                    PublicRecipeScrapService(this@PublicResultFragment).tryPostAddingScrap(PublicRecipeScrapRequest(recipeId = recipeId))
                }
            }




        }
    }

    override fun onGetPublicRecipeFailure(message: String) {

    }

    override fun onPostPublicRecipeScrapSuccess(response: PublicRecipeScrapResponse) {

    }

    override fun onPostPublicRecipeScrapFailure(message: String) {

    }
}