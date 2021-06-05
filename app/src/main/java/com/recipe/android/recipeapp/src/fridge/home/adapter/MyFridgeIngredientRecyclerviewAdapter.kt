package com.recipe.android.recipeapp.src.fridge.home.adapter

import android.content.Context
import android.util.Log
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
import com.recipe.android.recipeapp.src.fridge.FridgeFragment.Companion.checkboxList
import com.recipe.android.recipeapp.src.fridge.FridgeFragment.Companion.patchFridgeList
import com.recipe.android.recipeapp.src.fridge.home.models.CheckboxData
import com.recipe.android.recipeapp.src.fridge.home.models.FridgeItem
import com.recipe.android.recipeapp.src.fridge.home.models.PatchFridgeObject

class MyFridgeIngredientRecyclerviewAdapter(val context: Context)
    : RecyclerView.Adapter<MyFridgeIngredientRecyclerviewAdapter.CustomViewholder>() {

    val TAG = "MyFridgeIngredientRecyclerviewAdapter"

    var fridgeItemList = ArrayList<FridgeItem>()

    lateinit var binding : ItemMyFridgeIngredientRecyclerviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        binding = ItemMyFridgeIngredientRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding, context)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.bindWithView(fridgeItemList[position], position)

        patchFridgeList.forEach {
            Log.d(TAG, "PatchFridgeList : ${it.ingredientName}, ${it.expiredAt}, ${it.storageMethod}, ${it.count}" )
            Log.d(TAG, "PatchFridgeList's Size : ${FridgeFragment.patchFridgeList.size}")
            Log.d(TAG, "체크박스 리스트 사이즈 : ${checkboxList.size}")
        }
    }

    override fun getItemCount(): Int = fridgeItemList.size

    inner class CustomViewholder(val binding: ItemMyFridgeIngredientRecyclerviewBinding, val context: Context)
        : RecyclerView.ViewHolder(binding.root) {

        var ingredientCnt : Int = 1

        fun bindWithView(fridgeItem: FridgeItem, position: Int) {

//            if(position >= checkboxList.size) {
//                checkboxList.add(position, CheckboxData(position, binding.checkbox.isChecked, fridgeItem.ingredientName))
//                Log.d(TAG, "체크박스 리스트 사이즈 : ${checkboxList.size}")
//            }


            binding.checkbox.setOnClickListener {
                checkboxList[position].checked = binding.checkbox.isChecked
            }
            binding.checkbox.isChecked = checkboxList[position].checked

            binding.ingredientNameTv.text = fridgeItem.ingredientName
            if(fridgeItem.ingredientIcon != null) {
                Glide.with(ApplicationClass.instance).load(fridgeItem.ingredientIcon).into(binding.ingredientIv)
            }
            if(fridgeItem.expiredAt != null) {
                binding.expireDateTv.text = fridgeItem.expiredAt
            } else {
                binding.expireDateTv.text = "00.00.00까지"
            }
            ingredientCnt = fridgeItem.count
            binding.ingredientCntTv.text = ingredientCnt.toString()

            // storageMethod 세팅
            when(fridgeItem.storageMethod) {
                "냉장" -> binding.freshnessRefrigerationTv.setTextColor(ContextCompat.getColor(context, R.color.green))
                "냉동" -> binding.freshnessFrozenTv.setTextColor(ContextCompat.getColor(context, R.color.green))
                "실온" -> binding.freshnessRoomTemperatureTv.setTextColor(ContextCompat.getColor(context, R.color.green))
            }
            binding.freshnessRefrigerationTv.setOnClickListener {
                //view.onClickStorageMethod(context.getString(R.string.refrigeration), position)
                binding.freshnessRefrigerationTv.setTextColor(ContextCompat.getColor(context, R.color.green))
                binding.freshnessFrozenTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))
                binding.freshnessRoomTemperatureTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))

                FridgeFragment.patchFridgeList[position].storageMethod = "냉장"
            }
            binding.freshnessFrozenTv.setOnClickListener {
                //view.onClickStorageMethod(context.getString(R.string.frozen), position)
                binding.freshnessRefrigerationTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))
                binding.freshnessFrozenTv.setTextColor(ContextCompat.getColor(context, R.color.green))
                binding.freshnessRoomTemperatureTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))

                FridgeFragment.patchFridgeList[position].storageMethod = "냉동"
            }
            binding.freshnessRoomTemperatureTv.setOnClickListener {
                //view.onClickStorageMethod(context.getString(R.string.roomTemperature), position)
                binding.freshnessRefrigerationTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))
                binding.freshnessFrozenTv.setTextColor(ContextCompat.getColor(context, R.color.gray_200))
                binding.freshnessRoomTemperatureTv.setTextColor(ContextCompat.getColor(context, R.color.green))

                FridgeFragment.patchFridgeList[position].storageMethod = "실온"
            }

            // count 세팅
            binding.plusCntIv.setOnClickListener {
                ingredientCnt += 1
                binding.ingredientCntTv.text = ingredientCnt.toString()
                //view.onClickCount(binding.ingredientCntTv.text.toString().toInt(), position)

                FridgeFragment.patchFridgeList[position].count = ingredientCnt
            }
            binding.minusCntIv.setOnClickListener {
                if(ingredientCnt > 1) {
                    ingredientCnt -= 1
                    binding.ingredientCntTv.text = ingredientCnt.toString()
                    //view.onClickCount(binding.ingredientCntTv.text.toString().toInt(), position)

                    FridgeFragment.patchFridgeList[position].count = ingredientCnt
                }
            }

            // freshness 세팅
            when(fridgeItem.freshness) {
                1 -> {
                    // Red
                    binding.freshnessIv.setImageResource(R.drawable.ic_freshness_red_new)
                }
                2 -> {
                    // Yellow
                    binding.freshnessIv.setImageResource(R.drawable.ic_freshness_yellow_new)
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

            // 달력 띄우기
            binding.expireDateTv.setOnClickListener {

            }

            if(FridgeFragment.updateButtonFlag) {
                binding.checkbox.visibility = View.VISIBLE
                binding.freshnessLayout.visibility = View.VISIBLE
                binding.freshnessIv.visibility = View.GONE
                binding.plusCntIv.visibility = View.VISIBLE
                binding.minusCntIv.visibility = View.VISIBLE
                binding.bottomView.visibility = View.GONE
                binding.bottomLine.visibility = View.VISIBLE

            } else {
                binding.checkbox.visibility = View.GONE
                binding.freshnessLayout.visibility = View.GONE
                binding.freshnessIv.visibility = View.VISIBLE
                binding.plusCntIv.visibility = View.GONE
                binding.minusCntIv.visibility = View.GONE
                binding.bottomView.visibility = View.VISIBLE
                binding.bottomLine.visibility = View.GONE
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