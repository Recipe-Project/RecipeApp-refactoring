package com.recipe.android.recipeapp.src.fridge.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.FragmentMyFridgeCategoryBinding
import com.recipe.android.recipeapp.src.fridge.home.SwipeToDeleteCallback
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

    class CustomViewholder(val binding: FragmentMyFridgeCategoryBinding, val context : Context) : RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(fridgeResult : GetFridgeResult) {

            // 카테고리 이름
            binding.tvCategory.text = fridgeResult.ingredientCategoryName

            val myFridgeIngredientRecyclerviewAdapter = MyFridgeIngredientRecyclerviewAdapter(context)
            binding.rvIngredient.adapter = myFridgeIngredientRecyclerviewAdapter
            myFridgeIngredientRecyclerviewAdapter.submitList(fridgeResult.ingredientList as ArrayList<FridgeItem>)
            val swipeDelete = object : SwipeToDeleteCallback(context) {
                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    myFridgeIngredientRecyclerviewAdapter.deleteItem(viewHolder.adapterPosition)
                    // 냉장고 삭제 API 호출
                }
            }
            val touchHelper = ItemTouchHelper(swipeDelete)
            touchHelper.attachToRecyclerView(binding.rvIngredient)
        }
    }

    fun submitList(resultList: ArrayList<GetFridgeResult>) {
        this.resultList = resultList
        notifyDataSetChanged()
    }

}