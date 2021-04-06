package com.recipe.android.recipeapp.src.search.publicRecipe

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityRecipeDetailBinding
import com.recipe.android.recipeapp.src.search.publicRecipe.PublicRecipeService
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeDetailView
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeScrapView
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeScrapResponse

class RecipeDetailActivity : BaseActivity<ActivityRecipeDetailBinding>(ActivityRecipeDetailBinding::inflate), PublicRecipeDetailView, PublicRecipeScrapView {

    private var recipeId : Int = 0
    val TAG = "RecipeDetailActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.hasExtra("index")) {
            val index = intent.getIntExtra("index", 0)
            PublicRecipeService(this).getPublicRecipeDetail(index)
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.recipeDetailActivityFavoriteTv.setOnClickListener {
            PublicRecipeScrapService(this).tryPostAddingScrap(PublicRecipeScrapRequest(recipeId = recipeId))
        }
        binding.recipeDetailActivityFavoriteIv.setOnClickListener {
            PublicRecipeScrapService(this).tryPostAddingScrap(PublicRecipeScrapRequest(recipeId = recipeId))
        }



    }

    override fun onGetPublicRecipeDetailSuccess(response: PublicRecipeDetailResponse) {
        val result = response.result
        binding.toolbarLayout.title = result.recipeName
        binding.recipeDetailActivityCookingTimeTv.text = result.cookingTime
        binding.recipeDetailActivityLevelTv.text = result.level
        binding.recipeDetailActivitySummaryTv.text = result.summary

        recipeId = result.recipeId // for test


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