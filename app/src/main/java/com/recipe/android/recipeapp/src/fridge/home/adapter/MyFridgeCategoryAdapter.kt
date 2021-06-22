package com.recipe.android.recipeapp.src.fridge.home.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.recipe.android.recipeapp.src.fridge.home.`interface`.IngredientUpdateView
import com.recipe.android.recipeapp.src.fridge.home.fragment.MyFridgeAllCategoryFragment
import com.recipe.android.recipeapp.src.fridge.home.fragment.MyFridgeCategoryFragment
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult

private const val NUM_PAGES = 7


class MyFridgeCategoryAdapter(fa: FragmentActivity, val view : IngredientUpdateView) : FragmentStateAdapter(fa) {

    val TAG = "MyFridgeCategoryAdapter"

    private var ingredients = ArrayList<GetFridgeResult>()
    private var currentPosition = 0

    override fun getItemCount(): Int = NUM_PAGES

    var isEditMode = false

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "MyFridgeCategoryAdapter - createFragment() : $position")
        Log.d(TAG, "MyFridgeCategoryAdapter - createFragment() get item id : ${getItemId(position)}")

        currentPosition = getRealPosition(position)

        Log.d(TAG, "MyFridgeCategoryAdapter - createFragment() : 현재 포지션 :$currentPosition")
        
        return if (currentPosition != 0) {
            val myFridgeCategoryFragment = MyFridgeCategoryFragment()
            myFridgeCategoryFragment.arguments = Bundle().apply {
                putParcelable("result", ingredients[currentPosition - 1])
                putInt("index", position - 1)
                putBoolean("isEditMode", isEditMode)
            }
            myFridgeCategoryFragment
        } else {
            // MyFridgeAllCategoryFragment(ingredients, view)
            val myFridgeAllCategoryFragment = MyFridgeAllCategoryFragment()
            myFridgeAllCategoryFragment.arguments = Bundle().apply {
                putParcelableArrayList("resultList", ingredients)
                putBoolean("isEditMode", isEditMode)
            }
            myFridgeAllCategoryFragment
        }
    }

    fun submitList(ingredients: ArrayList<GetFridgeResult>, isEditMode: Boolean) {
        this.isEditMode = isEditMode
        this.ingredients = ingredients
        notifyDataSetChanged()
    }

    private fun getRealPosition(position: Int): Int {
        return position % NUM_PAGES
    }


}