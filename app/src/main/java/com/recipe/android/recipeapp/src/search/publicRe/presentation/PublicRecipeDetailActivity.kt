package com.recipe.android.recipeapp.src.search.publicRe.presentation

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityPublicRecipeDetailBinding
import com.recipe.android.recipeapp.src.search.publicRe.model.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.publicRe.presentation.viewpager.PublicPagerAdapter
import com.recipe.android.recipeapp.src.search.publicRe.presentation.viewpager.PublicPagerFragment1
import com.recipe.android.recipeapp.src.search.publicRe.presentation.viewpager.PublicPagerFragment2

class PublicRecipeDetailActivity: BaseActivity<ActivityPublicRecipeDetailBinding>(
    ActivityPublicRecipeDetailBinding::inflate) {

    val TAG = "PublicRecipeDetailActivity"

    private lateinit var model: PublicRecipeDetailViewModel
    var recipeIndex: Int? = null

    lateinit var adapter: PublicPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = PublicRecipeDetailViewModel(this)

        intent?.hasExtra("index").apply {
            recipeIndex = intent.getIntExtra("index", 0)
        }

        binding.run {
            viewModel = PublicRecipeDetailViewModel(this@PublicRecipeDetailActivity)
        }

        recipeIndex?.let {
            getDetailInfo(it)
            listenToObservables()
        }
    }

    private fun getDetailInfo(idx: Int){
        model.getPublicRecipeDetailInfo(idx)
    }

    private fun listenToObservables(){
        model.getDetailResponse().observe(this, Observer {
            setPagerAdapter(it)
        })
    }

    private fun setPagerAdapter(data: PublicRecipeDetailResponse) {
        Log.d(TAG, "PublicRecipeDetailActivity - setPagerAdapter() : ")
        adapter = PublicPagerAdapter(
            this,
            listOf(
                PublicPagerFragment1(),
                PublicPagerFragment2()
            ),
            data
        )
        binding.viewPager.adapter = adapter
    }
}