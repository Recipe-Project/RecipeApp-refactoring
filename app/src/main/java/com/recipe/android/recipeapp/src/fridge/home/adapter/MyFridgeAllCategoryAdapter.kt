package com.recipe.android.recipeapp.src.fridge.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.FragmentMyFridgeCategoryBinding
import com.recipe.android.recipeapp.src.fridge.home.FridgeUpdateService
import com.recipe.android.recipeapp.src.fridge.home.SwipeToDeleteCallback
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeUpdateView
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientRequest
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientResponse
import com.recipe.android.recipeapp.src.fridge.home.models.FridgeItem
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult

class MyFridgeAllCategoryAdapter(val context : Context) : RecyclerView.Adapter<MyFridgeAllCategoryAdapter.CustomViewholder>() {

    var resultList = ArrayList<GetFridgeResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = FragmentMyFridgeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding, context)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.bindWithView(resultList[position])
    }

    override fun getItemCount(): Int = resultList.size

    class CustomViewholder(val binding: FragmentMyFridgeCategoryBinding, val context : Context) : RecyclerView.ViewHolder(binding.root), FridgeUpdateView {
        fun bindWithView(fridgeResult : GetFridgeResult) {
            val ingredientList = fridgeResult.ingredientList
            var deleteList = ArrayList<String>()

            // 카테고리 이름
            binding.tvCategory.text = fridgeResult.ingredientCategoryName
            val myFridgeIngredientRecyclerviewAdapter = MyFridgeIngredientRecyclerviewAdapter(context)
            binding.rvIngredient.adapter = myFridgeIngredientRecyclerviewAdapter
            myFridgeIngredientRecyclerviewAdapter.submitList(ingredientList)
            val swipeDelete = object : SwipeToDeleteCallback(context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    deleteList.add(ingredientList[viewHolder.adapterPosition].ingredientName)
                    myFridgeIngredientRecyclerviewAdapter.deleteItem(viewHolder.adapterPosition)

                    // 냉장고 삭제 API 호출
                    FridgeUpdateService(this@CustomViewholder).tryDeleteIngredient(DeleteIngredientRequest(deleteList))
                }
            }
            val touchHelper = ItemTouchHelper(swipeDelete)
            touchHelper.attachToRecyclerView(binding.rvIngredient)
        }

        override fun onDeleteIngredientSuccess(response: DeleteIngredientResponse) {

        }

        override fun onDeleteIngredientFailure(message: String) {

        }
    }

    fun submitList(resultList: ArrayList<GetFridgeResult>) {
        this.resultList = resultList
        notifyDataSetChanged()
    }


}