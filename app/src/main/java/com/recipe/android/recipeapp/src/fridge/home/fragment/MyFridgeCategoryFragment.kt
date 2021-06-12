package com.recipe.android.recipeapp.src.fridge.home.fragment

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentMyFridgeCategoryBinding
import com.recipe.android.recipeapp.src.fridge.FridgeService
import com.recipe.android.recipeapp.src.fridge.home.FridgeUpdateService
import com.recipe.android.recipeapp.src.fridge.home.SwipeToDeleteCallback
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeUpdateView
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeView
import com.recipe.android.recipeapp.src.fridge.home.`interface`.IngredientUpdateView
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeIngredientRecyclerviewAdapter
import com.recipe.android.recipeapp.src.fridge.home.models.*

class MyFridgeCategoryFragment()
    : BaseFragment<FragmentMyFridgeCategoryBinding>(FragmentMyFridgeCategoryBinding::bind, R.layout.fragment_my_fridge_category) {

    val TAG = "MyFridgeCategoryFragment"
    lateinit var result : GetFridgeResult
    var index = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey("result") }?.apply {
            result = getParcelable("result")!!
            index = getInt("index")
            Log.d(TAG, "인덱스 : $index")
        }

        val fridgeItemList = result.ingredientList
        val myFridgeIngredientRecyclerviewAdapter = MyFridgeIngredientRecyclerviewAdapter(requireContext())
        binding.rvIngredient.adapter = myFridgeIngredientRecyclerviewAdapter

        if(fridgeItemList.size != 0) {
            myFridgeIngredientRecyclerviewAdapter.submitList(fridgeItemList)
            myFridgeIngredientRecyclerviewAdapter.getIndex(index)

            // 카테고리 이름
            binding.tvCategory.text = result.ingredientCategoryName
            Log.d(TAG, "MyFridgeCategoryFragment : ${result.ingredientCategoryName}" )

            binding.rvIngredient.visibility = View.VISIBLE
            binding.tvCategory.visibility = View.VISIBLE
            binding.bottomMargin.visibility = View.VISIBLE
            binding.defaultTv.visibility = View.GONE
        } else {
            binding.rvIngredient.visibility = View.GONE
            binding.tvCategory.visibility = View.GONE
            binding.bottomMargin.visibility = View.GONE
            binding.defaultTv.visibility = View.VISIBLE
        }
    }
}