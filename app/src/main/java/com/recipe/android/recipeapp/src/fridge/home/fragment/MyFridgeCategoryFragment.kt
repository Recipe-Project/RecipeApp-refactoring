package com.recipe.android.recipeapp.src.fridge.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentMyFridgeCategoryBinding
import com.recipe.android.recipeapp.src.fridge.home.FridgeUpdateService
import com.recipe.android.recipeapp.src.fridge.home.SwipeToDeleteCallback
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeUpdateView
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeIngredientRecyclerviewAdapter
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientRequest
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientResponse
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult

class MyFridgeCategoryFragment(val result : GetFridgeResult)
    : BaseFragment<FragmentMyFridgeCategoryBinding>(FragmentMyFridgeCategoryBinding::bind, R.layout.fragment_my_fridge_category), FridgeUpdateView {

    val TAG = "MyFridgeCategoryFragment"
    var deleteList = ArrayList<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fridgeItemList = result.ingredientList
        val myFridgeIngredientRecyclerviewAdapter = MyFridgeIngredientRecyclerviewAdapter(requireContext())
        binding.rvIngredient.adapter = myFridgeIngredientRecyclerviewAdapter
        myFridgeIngredientRecyclerviewAdapter.submitList(fridgeItemList)
        val swipeDelete = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteList.add(fridgeItemList[viewHolder.adapterPosition].ingredientName)
                myFridgeIngredientRecyclerviewAdapter.deleteItem(viewHolder.adapterPosition)
                // 냉장고 삭제 API 호출
                FridgeUpdateService(this@MyFridgeCategoryFragment).tryDeleteIngredient(DeleteIngredientRequest(deleteList))
            }
        }
        val touchHelper = ItemTouchHelper(swipeDelete)
        touchHelper.attachToRecyclerView(binding.rvIngredient)


        // 카테고리 이름
        binding.tvCategory.text = result.ingredientCategoryName
        Log.d(TAG, "MyFridgeCategoryFragment : ${result.ingredientCategoryName}" )
    }

    override fun onDeleteIngredientSuccess(response: DeleteIngredientResponse) {

    }

    override fun onDeleteIngredientFailure(message: String) {

    }
}