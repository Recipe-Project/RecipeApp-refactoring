package com.recipe.android.recipeapp.src.fridge.basket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemBasketIngredientBinding
import com.recipe.android.recipeapp.src.fridge.basket.BasketActivity
import com.recipe.android.recipeapp.src.fridge.basket.`interface`.BasketActivityView
import com.recipe.android.recipeapp.src.fridge.basket.`interface`.DateDialogInterface
import com.recipe.android.recipeapp.src.fridge.basket.models.BasketIngredient
import com.recipe.android.recipeapp.src.fridge.dialog.DateDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class BasketRecyclerViewAdapter(val view: BasketActivityView, val activity: BasketActivity):
RecyclerView.Adapter<BasketRecyclerViewAdapter.BasketViewHolder>(){

    private var ingredientList = ArrayList<BasketIngredient>()

    val context = ApplicationClass.instance

    inner class BasketViewHolder(val binding: ItemBasketIngredientBinding): RecyclerView.ViewHolder(
        binding.root
    ), DateDialogInterface{
        fun bindWithView(ingredient: BasketIngredient, position: Int) {

            val ingredientCnt = ingredient.ingredientCnt
            binding.tvIngredientCnt.text = ingredientCnt.toString()

            binding.tvIngredientName.text = ingredient.ingredientName
            if (ingredient.ingredientIcon != "") {
                Glide.with(context).load(ingredient.ingredientIcon).into(binding.icIngredient)
            }

            binding.btnAdd.setOnClickListener{
                view.onUpCnt(position, ingredientCnt + 1)
                binding.tvIngredientCnt.text = (ingredientCnt + 1).toString()
            }
            binding.btnRemove.setOnClickListener {
                if (ingredientCnt > 1) {
                    view.onDownCnt(position, ingredientCnt - 1)
                    binding.tvIngredientCnt.text = (ingredientCnt - 1).toString()
                }
            }

            binding.btnRefrigeration.setTextColor(context.getColor(R.color.green))

            binding.btnRefrigeration.setOnClickListener {
                view.onClickStorageMethod(context.getString(R.string.refrigeration), position)
                binding.btnRefrigeration.setTextColor(context.getColor(R.color.green))
                binding.btnFrozen.setTextColor(context.getColor(R.color.gray_200))
                binding.btnRoomTemperature.setTextColor(context.getColor(R.color.gray_200))
            }
            binding.btnFrozen.setOnClickListener {
                view.onClickStorageMethod(context.getString(R.string.frozen), position)
                binding.btnFrozen.setTextColor(context.getColor(R.color.green))
                binding.btnRefrigeration.setTextColor(context.getColor(R.color.gray_200))
                binding.btnRoomTemperature.setTextColor(context.getColor(R.color.gray_200))
            }
            binding.btnRoomTemperature.setOnClickListener {
                view.onClickStorageMethod(context.getString(R.string.roomTemperature), position)
                binding.btnRoomTemperature.setTextColor(context.getColor(R.color.green))
                binding.btnRefrigeration.setTextColor(context.getColor(R.color.gray_200))
                binding.btnFrozen.setTextColor(context.getColor(R.color.gray_200))
            }

            when (ingredient.storageMethod) {
                "냉장" -> binding.btnRefrigeration.performClick()
                "냉동" -> binding.btnFrozen.performClick()
                "실온" -> binding.btnRoomTemperature.performClick()
            }

            if (ingredient.expiredAt == null) {
                binding.tvExpired.text = "00.00.00까지"
            } else {
                binding.tvExpired.text = ingredient.expiredAt
            }

            binding.btnPickRemove.setOnClickListener {
                view.onClickPickRemove(position)
            }

            binding.btnExpired.setOnClickListener {
                val dateDialog = DateDialog(activity, this, position)
                dateDialog.show()
            }
        }

        override fun clickDate(year: Int, month: Int, dayOfMonth: Int, position: Int) {
            val cal = Calendar.getInstance()
            cal.set(year, month, dayOfMonth)
            val date = cal.time
            val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd", Locale.KOREAN)
            binding.tvExpired.text = simpleDateFormat.format(date)
            view.onClickExpiredAt(position, simpleDateFormat.format(date))
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(
            ItemBasketIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bindWithView(ingredientList[position], position)

    }

    override fun getItemCount(): Int = ingredientList.size

    fun submitList(ingredientList: ArrayList<BasketIngredient>) {
        this.ingredientList = ingredientList
        notifyDataSetChanged()
    }


}