package com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.appbar.AppBarLayout
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
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeDetailResult
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.adapter.RecipeDetailViewPagerAdapter
import java.lang.Math.abs

class RecipeDetailActivity : BaseActivity<ActivityRecipeDetailBinding>(ActivityRecipeDetailBinding::inflate), PublicRecipeDetailView, PublicRecipeScrapView {

    private var recipeId : Int = 0
    private val tapTypeList = arrayListOf("재료", "레시피")
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    val TAG = "RecipeDetailActivity"
    lateinit var result : PublicRecipeDetailResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 해당 레시피 인덱스 가져오기
        if(intent.hasExtra("index")) {
            val index = intent.getIntExtra("index", 0)
            showLoadingDialog()
            PublicRecipeDetailService(this).getPublicRecipeDetail(index)
        }

        // 공공레시피 스크랩하기
        binding.recipeDetailActivityFavoriteTv.setOnClickListener {
            if(result.userScrapYN == "Y") {
                showCustomToast("스크랩이 취소되었습니다.")
                result.userScrapYN = "N"
                binding.recipeDetailActivityFavoriteIv.setImageResource(R.drawable.ic_favorite_for_public_scrap)
            } else if(result.userScrapYN == "N") {
                showCustomToast("스크랩 레시피에 담겼습니다.")
                result.userScrapYN = "Y"
                binding.recipeDetailActivityFavoriteIv.setImageResource(R.drawable.ic_favorite_for_public_scrap_red)
            }
            PublicRecipeScrapService(this).tryPostAddingScrap(PublicRecipeScrapRequest(recipeId = recipeId))
        }
        binding.recipeDetailActivityFavoriteIv.setOnClickListener {
            if(result.userScrapYN == "Y") {
                showCustomToast("스크랩이 취소되었습니다.")
                result.userScrapYN = "N"
                binding.recipeDetailActivityFavoriteIv.setImageResource(R.drawable.ic_favorite_for_public_scrap)
            } else if(result.userScrapYN == "N") {
                showCustomToast("스크랩 레시피에 담겼습니다.")
                result.userScrapYN = "Y"
                binding.recipeDetailActivityFavoriteIv.setImageResource(R.drawable.ic_favorite_for_public_scrap_red)
            }
            PublicRecipeScrapService(this).tryPostAddingScrap(PublicRecipeScrapRequest(recipeId = recipeId))
        }


        // 툴바 세팅
        setSupportActionBar(binding.toolbar)
        // appBarLayout
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if(kotlin.math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.afterScrollLayout.visibility = View.VISIBLE
            } else {
                binding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent))
                binding.afterScrollLayout.visibility = View.GONE
            }
        })

        // 뒤로가기 버튼
        binding.beforeScrollBackBtn.setOnClickListener {
            finish()
        }
        binding.afterScrollBackBtn.setOnClickListener {
            finish()
        }
    }

    override fun onGetPublicRecipeDetailSuccess(response: PublicRecipeDetailResponse) {
        if(response.isSuccess) {
            dismissLoadingDialog()

            result = response.result
            binding.beforeScrollTitleTv.text = result.recipeName
            binding.afterScrollTitleTv.text = result.recipeName
            binding.beforeScrollScrapCntTv.text = result.userScrapCnt.toString()
            binding.afterScrollScrapCntTv.text = result.userScrapCnt.toString()

            binding.recipeDetailActivityCookingTimeTv.text = result.cookingTime
            binding.recipeDetailActivityLevelTv.text = result.level
            binding.recipeDetailActivitySummaryTv.text = result.summary
            Glide.with(ApplicationClass.instance).load(result.thumbnail).transform(CenterCrop(), RoundedCorners(5)).into(binding.recipeDetailActivityThumbnail)

            // For scrap
            recipeId = result.recipeId
            if(result.userScrapYN == "Y") {
                binding.recipeDetailActivityFavoriteIv.setImageResource(R.drawable.ic_favorite_for_public_scrap_red)
            } else if(result.userScrapYN == "N") {
                binding.recipeDetailActivityFavoriteIv.setImageResource(R.drawable.ic_favorite_for_public_scrap)
            }

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

    override fun onPostPublicRecipeScrapSuccess(response: PublicRecipeScrapResponse) {
        Log.d(TAG, response.result.userIdx.toString()) // For test

    }

    override fun onPostPublicRecipeScrapFailure(message: String) {

    }
}