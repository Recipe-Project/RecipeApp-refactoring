package com.recipe.android.recipeapp.src.fridge.AddDirect.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.recipe.android.recipeapp.src.fridge.AddDirect.fragment.AllCategoryFragment
import com.recipe.android.recipeapp.src.fridge.AddDirect.fragment.CategoryFragment
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.AddDirect.models.IngredientResult

private const val NUM_PAGES = 8

class IngredientCategoryAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {

    val TAG = "IngredientCategoryAdapter"

    private var ingredients = ArrayList<IngredientResult>()

    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "IngredientCategoryAdapter - createFragment() : $position")
        // 탭 설정
        return if (position != 0) {
            CategoryFragment(ingredients[position-1])
        } else {
            AllCategoryFragment(ingredients)
        }
    }

    fun submitList(ingredients: ArrayList<IngredientResult>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

}