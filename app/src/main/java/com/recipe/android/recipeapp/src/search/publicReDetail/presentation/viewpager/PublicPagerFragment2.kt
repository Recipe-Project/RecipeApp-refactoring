package com.recipe.android.recipeapp.src.search.publicReDetail.presentation.viewpager

import android.os.Bundle
import android.view.View
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentPublicViewpager2Binding
import com.recipe.android.recipeapp.src.search.publicReDetail.model.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.publicReDetail.model.PublicRecipeProcess
import com.recipe.android.recipeapp.src.search.publicReDetail.presentation.viewpager.frag2.Frag2Adapter

class PublicPagerFragment2() :
    BaseFragment<FragmentPublicViewpager2Binding>(
        FragmentPublicViewpager2Binding::bind,
        R.layout.fragment_public_viewpager_2
    ) {

    private lateinit var processList: List<PublicRecipeProcess>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey("data") }?.apply {
            val data = getParcelable<PublicRecipeDetailResponse>("data") as PublicRecipeDetailResponse
            processList = data.result.recipeProcessList.sortedBy { it.recipeProcessNo }
            setRecyclerView()
        }
    }

    private fun setRecyclerView(){
        val fragAdapter = Frag2Adapter(requireActivity())
        binding.rvProcess.adapter = fragAdapter
        fragAdapter.processList = processList
        fragAdapter.notifyDataSetChanged()
    }
}