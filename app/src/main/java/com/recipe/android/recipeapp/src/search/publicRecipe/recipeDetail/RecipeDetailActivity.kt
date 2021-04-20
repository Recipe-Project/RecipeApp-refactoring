package com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityRecipeDetailBinding
import com.recipe.android.recipeapp.src.search.publicRecipe.PublicRecipeScrapService
import com.recipe.android.recipeapp.src.search.publicRecipe.PublicRecipeDetailService
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeDetailView
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeScrapView
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.adapter.RecipeDetailViewPagerAdapter

class RecipeDetailActivity : BaseActivity<ActivityRecipeDetailBinding>(ActivityRecipeDetailBinding::inflate), PublicRecipeDetailView, PublicRecipeScrapView {

    private var recipeId : Int = 0
    private val tapTypeList = arrayListOf("재료", "레시피")
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    val TAG = "RecipeDetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 해당 레시피 인덱스 가져오기
        if(intent.hasExtra("index")) {
            val index = intent.getIntExtra("index", 0)
            PublicRecipeDetailService(this).getPublicRecipeDetail(index)
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // 공공레시피 스크랩하기
        binding.recipeDetailActivityFavoriteTv.setOnClickListener {
            PublicRecipeScrapService(this).tryPostAddingScrap(PublicRecipeScrapRequest(recipeId = recipeId))
        }
        binding.recipeDetailActivityFavoriteIv.setOnClickListener {
            PublicRecipeScrapService(this).tryPostAddingScrap(PublicRecipeScrapRequest(recipeId = recipeId))
        }
    }

    override fun onGetPublicRecipeDetailSuccess(response: PublicRecipeDetailResponse) {
        if(response.isSuccess) {
            val result = response.result
            binding.toolbarLayout.title = result.recipeName
            binding.recipeDetailActivityCookingTimeTv.text = result.cookingTime
            binding.recipeDetailActivityLevelTv.text = result.level
            binding.recipeDetailActivitySummaryTv.text = result.summary
            Glide.with(ApplicationClass.instance).load(result.thumbnail).transform(CenterCrop(), RoundedCorners(5)).into(binding.recipeDetailActivityThumbnail)

            recipeId = result.recipeId

            // 재료, 레시피 뷰페이저 & 탭
            viewPager = binding.recipeDetailActivityViewpager
            tabLayout = binding.recipeDetailActivityTablayout
            viewPager.adapter = RecipeDetailViewPagerAdapter(this, result.recipeIngredientList, result.recipeProcessList)
            TabLayoutMediator(tabLayout, viewPager){tab, position ->
                tab.text = tapTypeList[position]
            }.attach()
        }
    }

    override fun onGetPublicRecipeDetailFailure(message: String) {

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_recipe_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.menu_favorite -> true
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    override fun onPostPublicRecipeScrapSuccess(response: PublicRecipeScrapResponse) {
        Log.d(TAG, response.result.userIdx.toString()) // For test

    }

    override fun onPostPublicRecipeScrapFailure(message: String) {

    }
}