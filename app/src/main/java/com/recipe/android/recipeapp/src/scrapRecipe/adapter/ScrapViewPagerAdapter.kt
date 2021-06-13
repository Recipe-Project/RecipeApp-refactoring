package com.recipe.android.recipeapp.src.scrapRecipe.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.BlogScrapFragment
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.PublicScrapFragment
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.YoutubeScrapFragment

private const val NUM_PAGES = 3

class ScrapViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BlogScrapFragment()
            1 -> YoutubeScrapFragment()
            2 -> PublicScrapFragment()
            else -> BlogScrapFragment()
        }
    }




}