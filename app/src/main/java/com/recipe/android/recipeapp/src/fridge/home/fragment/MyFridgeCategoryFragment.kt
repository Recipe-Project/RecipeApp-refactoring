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
import com.recipe.android.recipeapp.src.fridge.home.FridgeUpdateService
import com.recipe.android.recipeapp.src.fridge.home.SwipeToDeleteCallback
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeUpdateView
import com.recipe.android.recipeapp.src.fridge.home.`interface`.IngredientUpdateView
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeIngredientRecyclerviewAdapter
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientRequest
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientResponse
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult

class MyFridgeCategoryFragment()
    : BaseFragment<FragmentMyFridgeCategoryBinding>(FragmentMyFridgeCategoryBinding::bind, R.layout.fragment_my_fridge_category), FridgeUpdateView {

    val TAG = "MyFridgeCategoryFragment"
    lateinit var result : GetFridgeResult

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey("result") }?.apply {
            result = getParcelable("result")!!
        }

        val fridgeItemList = result.ingredientList
        val myFridgeIngredientRecyclerviewAdapter = MyFridgeIngredientRecyclerviewAdapter(requireContext())
        binding.rvIngredient.adapter = myFridgeIngredientRecyclerviewAdapter
        myFridgeIngredientRecyclerviewAdapter.submitList(fridgeItemList)

        if(fridgeItemList.size != 0) {
            myFridgeIngredientRecyclerviewAdapter.submitList(fridgeItemList)
            val swipeDelete = object : SwipeToDeleteCallback(requireContext()) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val ingredientName = fridgeItemList[viewHolder.adapterPosition].ingredientName
                    myFridgeIngredientRecyclerviewAdapter.deleteItem(viewHolder.adapterPosition)
                    // 냉장고 삭제 API 호출
                    FridgeUpdateService(this@MyFridgeCategoryFragment).tryDeleteIngredient(DeleteIngredientRequest(ingredientName))
                    if(myFridgeIngredientRecyclerviewAdapter.fridgeItemList.size == 0) {
                        binding.tvCategory.visibility = View.GONE
                    }
                }
            }
            val touchHelper = ItemTouchHelper(swipeDelete)
            touchHelper.attachToRecyclerView(binding.rvIngredient)

            // 카테고리 이름
            binding.tvCategory.text = result.ingredientCategoryName
            Log.d(TAG, "MyFridgeCategoryFragment : ${result.ingredientCategoryName}" )

        } else {
            binding.rvIngredient.visibility = View.GONE
            binding.tvCategory.visibility = View.GONE
            binding.bottomMargin.visibility = View.GONE
        }
    }

    override fun onDeleteIngredientSuccess(response: DeleteIngredientResponse) {

    }

    override fun onDeleteIngredientFailure(message: String) {

    }
}