package com.recipe.android.recipeapp.src.search.publicRecipe

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentPublicResultBinding
import com.recipe.android.recipeapp.src.search.publicRecipe.adapter.PublicResultRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResult

class PublicResultFragment(private val publicResultList : ArrayList<PublicRecipeResult>) : BaseFragment<FragmentPublicResultBinding>(FragmentPublicResultBinding::bind, R.layout.fragment_public_result) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PublicResultRecyclerviewAdapter(publicResultList)
        binding.publicResultFragRecylerview.adapter = adapter
        adapter.publicRecipeItemClick = object : PublicResultRecyclerviewAdapter.PublicRecipeItemClick{
            override fun onClick(view: View, position: Int) {
                val index = publicResultList[position].recipeId
                val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
                intent.putExtra("index", index)
                requireActivity().startActivity(intent)
            }
        }
    }
}