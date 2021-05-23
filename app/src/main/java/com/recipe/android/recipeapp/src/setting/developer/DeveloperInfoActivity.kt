package com.recipe.android.recipeapp.src.setting.developer

import android.os.Bundle
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityDeveloperInfoBinding

class DeveloperInfoActivity: BaseActivity<ActivityDeveloperInfoBinding>(ActivityDeveloperInfoBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}