package com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemPickIngredientBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient

class PickIngredientRecyclerViewAdapter(val view: PickIngredientActivityView) :
    RecyclerView.Adapter<PickIngredientRecyclerViewAdapter.PickIngredientViewHolder>() {

    val TAG = "PickIngredientRecyclerViewAdapter"

    private var pickIngredientRecyclerViewItemList = ArrayList<Ingredient>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickIngredientViewHolder {
        return PickIngredientViewHolder(
            ItemPickIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PickIngredientViewHolder, position: Int) {
        holder.bindWithView(pickIngredientRecyclerViewItemList[position], position)
    }

    override fun getItemCount(): Int {
        return pickIngredientRecyclerViewItemList.size
    }

    fun submitList(pickIngredientRecyclerViewItemList: ArrayList<Ingredient>) {
        this.pickIngredientRecyclerViewItemList = pickIngredientRecyclerViewItemList
        notifyDataSetChanged()
    }

    inner class PickIngredientViewHolder(val binding: ItemPickIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(ingredient: Ingredient, position: Int) {
            binding.tvIngredientName.text = ingredient.ingredientName
            if (ingredient.ingredientIcon != "") {
                Glide.with(ApplicationClass.instance).load(ingredient.ingredientIcon).into(binding.icIngredient)
            } else {
                Glide.with(ApplicationClass.instance).load(
                    ApplicationClass.sSharedPreferences.getString(
                        ApplicationClass.IC_DEFAULT, "")).into(binding.icIngredient)
            }
            binding.btnPickCancel.setOnClickListener {
                view.removePickItem(position)
            }
        }
    }
}