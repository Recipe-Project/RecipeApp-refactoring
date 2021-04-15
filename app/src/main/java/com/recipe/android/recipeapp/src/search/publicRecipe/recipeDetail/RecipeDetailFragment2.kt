package com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail

import android.os.Bundle
import android.view.View
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentRecipeDetail2Binding
import com.recipe.android.recipeapp.src.search.publicRecipe.`interface`.PublicRecipeDetailView
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeProcess
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.adapter.RecipeProcessRecyclerviewAdapter

class RecipeDetailFragment2(private val processList : ArrayList<PublicRecipeProcess>) : BaseFragment<FragmentRecipeDetail2Binding>(FragmentRecipeDetail2Binding::bind, R.layout.fragment_recipe_detail2) {

    private lateinit var adapter: RecipeProcessRecyclerviewAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecipeProcessRecyclerviewAdapter(processList)
        binding.recipeDetail2FragRecyclerview.adapter = adapter
    }


}