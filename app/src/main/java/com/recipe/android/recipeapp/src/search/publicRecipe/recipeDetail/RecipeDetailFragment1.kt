package com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentRecipeDetail1Binding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientAllRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.search.publicRecipe.models.PublicRecipeIngredient
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.adapter.IngredientExistRecyclerViewAdapter

class RecipeDetailFragment1(private val ingredientsList : ArrayList<PublicRecipeIngredient>)
    : BaseFragment<FragmentRecipeDetail1Binding>(FragmentRecipeDetail1Binding::bind, R.layout.fragment_recipe_detail1) {

    private val ingredientExistList = ArrayList<PublicRecipeIngredient>()
    private val ingredientNoList = ArrayList<PublicRecipeIngredient>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for(i in ingredientsList) {
            if(i.inFridgeYN == "N") {
                ingredientNoList.add(i)
            } else {
                ingredientExistList.add(i)
            }
        }

        val ingredientExistRecyclerViewAdapter = IngredientExistRecyclerViewAdapter()
        binding.ingredientsExistRecyclerview.apply {
            adapter = ingredientExistRecyclerViewAdapter
            layoutManager = GridLayoutManager(context, 4)
        }
        ingredientExistRecyclerViewAdapter.submitList(ingredientExistList)

        val ingredientNoRecyclerViewAdapter = IngredientExistRecyclerViewAdapter()
        binding.ingredientsNoRecyclerview.apply {
            adapter = ingredientNoRecyclerViewAdapter
            layoutManager = GridLayoutManager(context, 4)
        }
        ingredientNoRecyclerViewAdapter.submitList(ingredientNoList)

    }
}