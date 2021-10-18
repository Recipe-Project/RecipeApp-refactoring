package com.recipe.android.recipeapp.src.search.publicRe.presentation.viewpager

import android.os.Bundle
import android.view.View
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentPublicViewpager2Binding

class PublicPagerFragment2() :
    BaseFragment<FragmentPublicViewpager2Binding>(
        FragmentPublicViewpager2Binding::bind,
        R.layout.fragment_public_viewpager_2
    ) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey("data") }?.apply {

        }
    }
}