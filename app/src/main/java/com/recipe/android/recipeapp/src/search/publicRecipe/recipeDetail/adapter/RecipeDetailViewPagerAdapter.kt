package com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.adapter

import android.app.Activity
import androidx.fragment.app.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.recipe.android.recipeapp.src.scrapRecipe.blogScrap.BlogScrapFragment
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.PublicScrapFragment
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.YoutubeScrapFragment
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeIngredient
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeProcess
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.RecipeDetailFragment1
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.RecipeDetailFragment2

private const val NUM_PAGES = 2

class RecipeDetailViewPagerAdapter(fa: FragmentActivity, private val ingredientsList : ArrayList<PublicRecipeIngredient>, private val processList : ArrayList<PublicRecipeProcess>) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RecipeDetailFragment1(ingredientsList)
            1 -> RecipeDetailFragment2(processList)
            else -> RecipeDetailFragment1(ingredientsList)
        }
    }
}