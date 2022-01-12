package com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.presentation.viewpager

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.model.PublicRecipeDetailResponse

private const val NUM_PAGES = 2

class PublicPagerAdapter(
    fa: FragmentActivity,
    private val fragmentList: List<Fragment>,
    private val data: PublicRecipeDetailResponse
) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        val fragment = when (position) {
            1 -> fragmentList[1]
            else -> fragmentList[0]
        }
        fragment.arguments = Bundle().apply {
            putParcelable("data", data)
        }
        return fragment
    }
}