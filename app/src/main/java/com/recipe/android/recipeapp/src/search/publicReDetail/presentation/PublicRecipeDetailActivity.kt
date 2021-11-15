package com.recipe.android.recipeapp.src.search.publicReDetail.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityPublicRecipeDetailBinding
import com.recipe.android.recipeapp.src.search.publicReDetail.model.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.publicReDetail.presentation.viewpager.PublicPagerAdapter
import com.recipe.android.recipeapp.src.search.publicReDetail.presentation.viewpager.PublicPagerFragment1
import com.recipe.android.recipeapp.src.search.publicReDetail.presentation.viewpager.PublicPagerFragment2

class PublicRecipeDetailActivity : BaseActivity<ActivityPublicRecipeDetailBinding>(
    ActivityPublicRecipeDetailBinding::inflate
) {

    val TAG = "PublicRecipeDetailActivity"

    private lateinit var model: PublicRecipeDetailViewModel
    var recipeIndex: Int? = null

    lateinit var adapter: PublicPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model = PublicRecipeDetailViewModel()

        intent?.hasExtra("index").apply {
            recipeIndex = intent.getIntExtra("index", 0)
        }

        binding.run {
            viewModel = PublicRecipeDetailViewModel()
            activity = this@PublicRecipeDetailActivity
        }

        recipeIndex?.let {
            setDefaultScrapImage()
            listenToObservables()
            getDetailInfo(it)
        }

        setToolbar()
    }

    private fun bindingTab() {
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "재료"
                1 -> tab.text = "레시피"
            }
        }.attach()
    }

    private fun getDetailInfo(idx: Int) {
        model.getPublicRecipeDetailInfo(idx)
    }

    private fun listenToObservables() {
        model.getDetailResponse().observe(this, Observer {
            if (it.isSuccess) {
                setPagerAdapter(it)
                bindingTab()
                binding.recipeInfo = it.result
                scrapListenToObservables()
            }
        })
    }

    private fun setDefaultScrapImage() {
        model.defaultScrapYN.observe(this, Observer {
            binding.icScrap.setImageResource(
                if (it) R.drawable.ic_favorite_for_public_scrap_red
                else R.drawable.ic_favorite_for_public_scrap
            )
            model.defaultScrapYN.removeObservers(this)
        })
    }

    fun clickScrap(view: View) {
        binding.btnScrap.setOnClickListener {
            model.scrapRecipe(binding.recipeInfo)
        }
    }

    private fun scrapListenToObservables() {
        model.recipeScrapYN.observe(this, Observer {
            Log.d(TAG, "PublicRecipeDetailActivity - scrapListenToObservables() : $it")
            if (it == "Y") scrapNtoY()
            else scrapYtoN()
        })
    }

    private fun scrapNtoY() {
        showCustomToast(getString(R.string.scrapComplete))
        binding.icScrap.setImageResource(R.drawable.ic_favorite_for_public_scrap_red)
        binding.recipeInfo?.apply {
            userScrapYN = "Y"
            userScrapCnt += 1
        }
        binding.invalidateAll()
    }

    private fun scrapYtoN() {
        showCustomToast(getString(R.string.scrapCancel))
        binding.icScrap.setImageResource(R.drawable.ic_favorite_for_public_scrap)
        binding.recipeInfo?.apply {
            userScrapYN = "N"
            userScrapCnt -= 1
        }
        binding.invalidateAll()
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

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (kotlin.math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.toolbarAfter.visibility = View.VISIBLE
                binding.beforeScroll.visibility = View.INVISIBLE
            } else {
                binding.toolbar.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.transparent
                    )
                )
                binding.toolbarAfter.visibility = View.GONE
                binding.beforeScroll.visibility = View.VISIBLE
            }
        })
    }
}