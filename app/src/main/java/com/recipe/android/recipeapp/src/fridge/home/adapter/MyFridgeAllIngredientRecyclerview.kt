package com.recipe.android.recipeapp.src.fridge.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.FragmentMyFridgeCategoryBinding
import com.recipe.android.recipeapp.src.fridge.home.models.FridgeItem
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResult

class MyFridgeAllIngredientRecyclerview(val context : Context) : RecyclerView.Adapter<MyFridgeAllIngredientRecyclerview.CustomViewholder>() {

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
            myFridgeIngredientRecyclerviewAdapter.submitList(fridgeResult.fridgeList as ArrayList<FridgeItem>)
        }

    }

    fun submitList(resultList: ArrayList<GetFridgeResult>) {
        this.resultList = resultList
        notifyDataSetChanged()
    }

}