package com.recipe.android.recipeapp.src.fridge.basket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemBasketIngredientBinding
import com.recipe.android.recipeapp.src.fridge.basket.`interface`.BasketActivityView
import com.recipe.android.recipeapp.src.fridge.basket.models.FridgeBasket

class BasketRecyclerViewAdapter(val view: BasketActivityView):
RecyclerView.Adapter<BasketRecyclerViewAdapter.BasketViewHolder>(){

    private var ingredientList = ArrayList<FridgeBasket>()

    private var ingredientCnt = 1

    val context = ApplicationClass.instance

    inner class BasketViewHolder(val binding: ItemBasketIngredientBinding): RecyclerView.ViewHolder(binding.root){
        fun bindWithView(ingredient: FridgeBasket, position: Int) {
            binding.tvIngredientName.text = ingredient.ingredientName
            if (ingredient.ingredientIcon != "") {
                Glide.with(context).load(ingredient.ingredientIcon).into(binding.icIngredient)
            }

            binding.btnAdd.setOnClickListener{
                ingredientCnt += 1
                binding.tvIngredientCnt.text = ingredientCnt.toString()
                view.onClickCount(binding.tvIngredientCnt.text.toString().toInt(), position)
            }
            binding.btnRemove.setOnClickListener {
                ingredientCnt -= 1
                binding.tvIngredientCnt.text = ingredientCnt.toString()
            }

            binding.btnRefrigeration.setTextColor(context.getColor(R.color.red))

            binding.btnRefrigeration.setOnClickListener {
                view.onClickStorageMethod(context.getString(R.string.refrigeration), position)
                binding.btnRefrigeration.setTextColor(context.getColor(R.color.red))
                binding.btnFrozen.setTextColor(context.getColor(R.color.gray_200))
                binding.btnRoomTemperature.setTextColor(context.getColor(R.color.gray_200))
            }
            binding.btnFrozen.setOnClickListener {
                view.onClickStorageMethod(context.getString(R.string.frozen), position)
                binding.btnFrozen.setTextColor(context.getColor(R.color.red))
                binding.btnRefrigeration.setTextColor(context.getColor(R.color.gray_200))
                binding.btnRoomTemperature.setTextColor(context.getColor(R.color.gray_200))
            }
            binding.btnRoomTemperature.setOnClickListener {
                view.onClickStorageMethod(context.getString(R.string.roomTemperature), position)
                binding.btnRoomTemperature.setTextColor(context.getColor(R.color.red))
                binding.btnRefrigeration.setTextColor(context.getColor(R.color.gray_200))
                binding.btnFrozen.setTextColor(context.getColor(R.color.gray_200))
            }

//            binding.tvExpired.setOnClickListener {
//                // 데이트피커
//                val intent = Intent(context, DateDialog::class.java)
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(context, intent, null)
//                binding.tvExpired.text = intent.getIntExtra("year", 0).toString()
//                view.onSetExpiredAt(binding.tvExpired.text.toString(), position)
//            }

            if (ingredient.expiredAt == null) {
                binding.tvExpired.text = "00.00.00 까지"
            } else {
                binding.tvExpired.text = ingredient.expiredAt
            }

            binding.btnPickRemove.setOnClickListener {
                view.onClickPickRemove(position)
            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(
            ItemBasketIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bindWithView(ingredientList[position], position)

        holder.binding.tvExpired.setOnClickListener {
            view.onClickExpiredAt(position)
        }

    }

    override fun getItemCount(): Int = ingredientList.size

    fun submitList(ingredientList: ArrayList<FridgeBasket>) {
        this.ingredientList = ingredientList
        notifyDataSetChanged()
    }
}