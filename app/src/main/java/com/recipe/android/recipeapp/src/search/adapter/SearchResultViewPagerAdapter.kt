package com.recipe.android.recipeapp.src.search.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.BlogScrapFragment
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.PublicScrapFragment
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.YoutubeScrapFragment
import com.recipe.android.recipeapp.src.search.blogRecipe.BlogResultFragment
import com.recipe.android.recipeapp.src.search.publicRecipe.PublicResultFragment
import com.recipe.android.recipeapp.src.search.youtubeRecipe.YoutubeResultFragment

class SearchResultViewPagerAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {

    private var fragmentList : ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = fragmentList[position]

    fun addFragment(fragment: Fragment) {
        fragmentList.add(fragment)
        notifyItemInserted(fragmentList.size - 1)
    }

}