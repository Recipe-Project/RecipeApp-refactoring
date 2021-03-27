package com.recipe.android.recipeapp.src.search

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityRecipeDetailBinding
import com.recipe.android.recipeapp.src.search.publicRecipe.PublicRecipeService
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeDetailView
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeDetailResponse

class RecipeDetailActivity : BaseActivity<ActivityRecipeDetailBinding>(ActivityRecipeDetailBinding::inflate), PublicRecipeDetailView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.hasExtra("index")) {
            val index = intent.getIntExtra("index", 0)
            PublicRecipeService(this).getPublicRecipeDetail(index)
        }

        setSupportActionBar(findViewById(R.id.toolbar))

    }

    override fun onGetPublicRecipeDetailSuccess(response: PublicRecipeDetailResponse) {
        val result = response.result
        findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout).title = result.recipeName

    }

    override fun onGetPublicRecipeDetailFailure(message: String) {

    }
}