package com.recipe.android.recipeapp.src.search.publicRe.presentation.viewpager

import android.os.Bundle
import android.view.View
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentPublicViewpager1Binding
import com.recipe.android.recipeapp.databinding.FragmentRecipeDetail1Binding
import com.recipe.android.recipeapp.src.search.publicRe.model.PublicRecipeIngredient

class PublicPagerFragment1() :
    BaseFragment<FragmentPublicViewpager1Binding>(
        FragmentPublicViewpager1Binding::bind,
        R.layout.fragment_public_viewpager_1
    ) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}