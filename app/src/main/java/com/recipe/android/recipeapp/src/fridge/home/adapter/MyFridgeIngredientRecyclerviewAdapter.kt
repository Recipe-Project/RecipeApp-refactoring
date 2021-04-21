package com.recipe.android.recipeapp.src.fridge.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemMyFridgeIngredientRecyclerviewBinding
import com.recipe.android.recipeapp.src.fridge.FridgeFragment
import com.recipe.android.recipeapp.src.fridge.home.`interface`.IngredientUpdateView
import com.recipe.android.recipeapp.src.fridge.home.models.FridgeItem

class MyFridgeIngredientRecyclerviewAdapter(val context: Context, val view: IngredientUpdateView)
    : RecyclerView.Adapter<MyFridgeIngredientRecyclerviewAdapter.CustomViewholder>() {

    var fridgeItemList = ArrayList<FridgeItem>()
    lateinit var binding : ItemMyFridgeIngredientRecyclerviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        binding = ItemMyFridgeIngredientRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding, context, view)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.bindWithView(fridgeItemList[position], position)
    }

    override fun getItemCount(): Int = fridgeItemList.size

    class CustomViewholder(val binding: ItemMyFridgeIngredientRecyclerviewBinding, val context: Context, val view : IngredientUpdateView)
        : RecyclerView.ViewHolder(binding.root) {

        var ingredientCnt : Int = 1

        fun bindWithView(fridgeItem: FridgeItem, position: Int) {
            binding.ingredientNameTv.text = fridgeItem.ingredientName
            if(fridgeItem.ingredientIcon != null) {
                Glide.with(ApplicationClass.instance).load(fridgeItem.ingredientIcon).into(binding.ingredientIv)
            }
            binding.expireDateTv.text = fridgeItem.expiredAt
            ingredientCnt = fridgeItem.count
            binding.ingredientCntTv.text = ingredientCnt.toString()

            // storageMethod 세팅
            when(fridgeItem.storageMethod) {
                "냉장" -> binding.freshnessRefrigerationTv.setTextColor(ContextCompat.getColor(context, R.color.red))
                "냉동" -> binding.freshnessFrozenTv.setTextColor(ContextCompat.getColor(context, R.color.red))
                "실온" -> binding.freshnessRoomTemperatureTv.setTextColor(ContextCompat.getColor(context, R.color.red))
            }
            binding.freshnessRefrigerationTv.setOnClickListener {
                view.onClickStorageMethod(context.getString(R.string.refrigeration), position)
                binding.freshnessRefrigerationTv.setTextColor(ContextCompat.getColor(context, R.color.red))
                binding.freshnessFrozenTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))
                binding.freshnessRoomTemperatureTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))
            }
            binding.freshnessFrozenTv.setOnClickListener {
                view.onClickStorageMethod(context.getString(R.string.frozen), position)
                binding.freshnessRefrigerationTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))
                binding.freshnessFrozenTv.setTextColor(ContextCompat.getColor(context, R.color.red))
                binding.freshnessRoomTemperatureTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))
            }
            binding.freshnessRoomTemperatureTv.setOnClickListener {
                view.onClickStorageMethod(context.getString(R.string.roomTemperature), position)
                binding.freshnessRefrigerationTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))
                binding.freshnessFrozenTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))
                binding.freshnessRoomTemperatureTv.setTextColor(ContextCompat.getColor(context, R.color.red))
            }

            // count 세팅
            binding.plusCntIv.setOnClickListener {
                ingredientCnt += 1
                binding.ingredientCntTv.text = ingredientCnt.toString()
                view.onClickCount(binding.ingredientCntTv.text.toString().toInt(), position)
            }
            binding.minusCntIv.setOnClickListener {
                if(ingredientCnt > 1) {
                    ingredientCnt -= 1
                    binding.ingredientCntTv.text = ingredientCnt.toString()
                    view.onClickCount(binding.ingredientCntTv.text.toString().toInt(), position)
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

            if(FridgeFragment.updateButtonFlag) {
                binding.checkbox.visibility = View.VISIBLE
                binding.freshnessLayout.visibility = View.VISIBLE
                binding.freshnessIv.visibility = View.GONE
                binding.plusCntIv.visibility = View.VISIBLE
                binding.minusCntIv.visibility = View.VISIBLE

            } else {
                binding.checkbox.visibility = View.GONE
                binding.freshnessLayout.visibility = View.GONE
                binding.freshnessIv.visibility = View.VISIBLE
                binding.plusCntIv.visibility = View.GONE
                binding.minusCntIv.visibility = View.GONE
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