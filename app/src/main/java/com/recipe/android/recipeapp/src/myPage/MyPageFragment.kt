package com.recipe.android.recipeapp.src.myPage

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentMyPageBinding
import com.recipe.android.recipeapp.src.myPage.myRecipe.MyRecipeActivity

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnViewAllMyRecipe.setOnClickListener {
            val intent = Intent(context, MyRecipeActivity::class.java)
            startActivity(intent)
        }

    }
}