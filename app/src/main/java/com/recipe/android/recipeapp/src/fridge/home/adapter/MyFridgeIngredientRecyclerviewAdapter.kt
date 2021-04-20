package com.recipe.android.recipeapp.src.fridge.home.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemMyFridgeIngredientRecyclerviewBinding
import com.recipe.android.recipeapp.src.fridge.home.models.FridgeItem

class MyFridgeIngredientRecyclerviewAdapter(val context : Context) : RecyclerView.Adapter<MyFridgeIngredientRecyclerviewAdapter.CustomViewholder>() {

    var fridgeItemList = ArrayList<FridgeItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemMyFridgeIngredientRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding, context)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.bindWithView(fridgeItemList[position])
    }

    override fun getItemCount(): Int = fridgeItemList.size

    class CustomViewholder(val binding: ItemMyFridgeIngredientRecyclerviewBinding, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(fridgeItem: FridgeItem) {

            binding.ingredientNameTv.text = fridgeItem.ingredientName
            if(fridgeItem.ingredientIcon != null) {
                Glide.with(ApplicationClass.instance).load(fridgeItem.ingredientIcon).into(binding.ingredientIv)
            }
            binding.expireDateTv.text = fridgeItem.expiredAt
            binding.ingredientCntTv.text = fridgeItem.count.toString()

            // storageMethod 세팅
            when(fridgeItem.storageMethod) {
                "냉장" -> {
                    binding.freshnessRefrigerationTv.setTextColor(ContextCompat.getColor(context, R.color.red))
                }
                "냉동" -> {
                    binding.freshnessFrozenTv.setTextColor(ContextCompat.getColor(context, R.color.red))
                }
                "실온" -> {
                    binding.freshnessRoomTemperatureTv.setTextColor(ContextCompat.getColor(context, R.color.red))
                }
            }

            // freshness 세팅
            when(fridgeItem.freshness) {
                1 -> {
                    // Red
                    binding.freshnessIv.setImageResource(R.drawable.ic_freshness_red)
                }
                2 -> {
                    // Yellow
                    binding.freshnessIv.setImageResource(R.drawable.ic_freshness_yellow)
                }
                3 -> {
                    // Green
                    binding.freshnessIv.setImageResource(R.drawable.ic_freshness_good)
                }
                444 -> {
                    // Black
                    binding.freshnessIv.setImageResource(R.drawable.ic_freshness_black)
                }
                else -> {
                    binding.freshnessIv.visibility = View.VISIBLE
                }
            }
        }
    }

    fun submitList(fridgeItemList: ArrayList<FridgeItem>) {
        this.fridgeItemList = fridgeItemList
        notifyDataSetChanged()
    }

    fun deleteItem(index : Int) {
        fridgeItemList.removeAt(index)
        notifyDataSetChanged()
    }
}