package com.recipe.android.recipeapp.src.search.searchResult

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SearchResultViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    private var fragmentList : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size - 1)
    }

}