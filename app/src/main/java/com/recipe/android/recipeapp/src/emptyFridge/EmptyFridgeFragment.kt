package com.recipe.android.recipeapp.src.emptyFridge

import android.os.Bundle
import android.view.View
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentEmptyFridgeBinding

class EmptyFridgeFragment : BaseFragment<FragmentEmptyFridgeBinding>(FragmentEmptyFridgeBinding::bind, R.layout.fragment_empty_fridge) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}