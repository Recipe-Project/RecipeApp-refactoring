package com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.fragment.AllCategoryFragment
import com.recipe.android.recipeapp.src.fridge.pickIngredient.fragment.CategoryFragment
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResult

class IngredientCategoryAdapter(fa: FragmentActivity, val view: PickIngredientActivityView) :
    FragmentStateAdapter(fa) {

    val TAG = "IngredientCategoryAdapter"

    private var ingredients = ArrayList<IngredientResult>()

    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "IngredientCategoryAdapter - createFragment() : $position")
        // 탭 설정
        return if (position != 0) {
            CategoryFragment(ingredients[position - 1], view)
        } else {
            AllCategoryFragment(ingredients, view)
        }
    }

    fun submitList(ingredients: ArrayList<IngredientResult>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

}