package com.recipe.android.recipeapp.src.fridge.basket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemBasketIngredientBinding
import com.recipe.android.recipeapp.src.fridge.basket.`interface`.BasketActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient

class BasketRecyclerViewAdapter(val view: BasketActivityView):
RecyclerView.Adapter<BasketRecyclerViewAdapter.BasketViewHolder>(){

    private var ingredientList = ArrayList<Ingredient>()

    private var ingredientCnt = 1

    inner class BasketViewHolder(val binding: ItemBasketIngredientBinding): RecyclerView.ViewHolder(binding.root){
        fun bindWithView(ingredient: Ingredient) {
            binding.tvIngredientName.text = ingredient.ingredientName
            if (ingredient.ingredientIcon != null) {
                Glide.with(ApplicationClass.instance).load(ingredient.ingredientIcon).into(binding.icIngredient)
            }

            binding.btnAdd.setOnClickListener{
                ingredientCnt += 1
                binding.tvIngredientCnt.text = ingredientCnt.toString()
            }
            binding.btnRemove.setOnClickListener {
                ingredientCnt -= 1
                binding.tvIngredientCnt.text = ingredientCnt.toString()
            }

            binding.btnRefrigeration.setOnClickListener(StorageMethodListener())
            binding.btnFrozen.setOnClickListener(StorageMethodListener())
            binding.btnRoomTemperature.setOnClickListener(StorageMethodListener())
        }

        inner class StorageMethodListener: View.OnClickListener{
            override fun onClick(v: View?) {
                when(v?.id){
                    R.id.btn_refrigeration -> view.onClickStorageMethod()
                    R.id.btn_frozen -> view.onClickStorageMethod()
                    R.id.btn_room_temperature -> view.onClickStorageMethod()
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        return BasketViewHolder(
            ItemBasketIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bindWithView(ingredientList[position])
    }

    override fun getItemCount(): Int = ingredientList.size

    fun submitList(ingredientList: ArrayList<Ingredient>) {
        this.ingredientList = ingredientList
        notifyDataSetChanged()
    }
}