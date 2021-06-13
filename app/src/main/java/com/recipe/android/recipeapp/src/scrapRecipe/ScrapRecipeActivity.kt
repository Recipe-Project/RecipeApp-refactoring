package com.recipe.android.recipeapp.src.scrapRecipe

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityScrapRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.adapter.ScrapViewPagerAdapter

class ScrapRecipeActivity: BaseActivity<ActivityScrapRecipeBinding>(ActivityScrapRecipeBinding::inflate) {

    val TAG = "ScrapRecipeActivity"

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val position = intent?.getIntExtra("position", 0)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        tabLayout = binding.tabLayout
        viewPager = binding.viewPager

        viewPager.adapter = ScrapViewPagerAdapter(this)
        position?.let { viewPager.currentItem = position}

        val tabLayoutTextArray = arrayOf( "블로그", "유튜브", "추천")

        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.text = tabLayoutTextArray[position]
        }.attach()

    }
}