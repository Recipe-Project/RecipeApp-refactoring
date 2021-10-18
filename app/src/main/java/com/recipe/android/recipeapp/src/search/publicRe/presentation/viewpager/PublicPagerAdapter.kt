package com.recipe.android.recipeapp.src.search.publicRe.presentation.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

private const val NUM_PAGES = 2

class PublicPagerAdapter(fa: FragmentActivity, private val fragmentList: List<Fragment>) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> fragmentList[0]
            1 -> fragmentList[1]
            else -> fragmentList[0]
        }
    }

}