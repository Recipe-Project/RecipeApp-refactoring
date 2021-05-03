package com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.fragment.AllCategoryFragment
import com.recipe.android.recipeapp.src.fridge.pickIngredient.fragment.CategoryFragment
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.CategoryIngrediets
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResult

class IngredientViewPagerAdapter(fa: FragmentActivity, val view: PickIngredientActivityView) :
    FragmentStateAdapter(fa) {

    val TAG = "IngredientCategoryAdapter"

    private var ingredients = ArrayList<CategoryIngrediets>()

    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "IngredientCategoryAdapter - createFragment() : $position")
        // 탭 설정
        return if (position != 0) {
            // CategoryFragment(ingredients[position - 1], view)
            val categoryFragment = CategoryFragment()
            categoryFragment.arguments = Bundle().apply {
                putParcelable("ingredients", ingredients[position - 1])
            }
            return categoryFragment
        } else {
            //AllCategoryFragment(ingredients, view)
            val allFragment = AllCategoryFragment()
            allFragment.arguments = Bundle().apply {
                putParcelableArrayList("ingredients", ingredients)
            }
            return allFragment
        }
    }

    fun submitList(ingredients: ArrayList<CategoryIngrediets>) {
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

}