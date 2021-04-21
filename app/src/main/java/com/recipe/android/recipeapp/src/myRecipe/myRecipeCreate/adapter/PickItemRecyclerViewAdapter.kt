package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.IC_DEFAULT
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sSharedPreferences
import com.recipe.android.recipeapp.databinding.ItemPickIngredientBinding
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`.MyRecipeCreateActivityView
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.DirectIngredientList

class PickItemRecyclerViewAdapter(val view: MyRecipeCreateActivityView) :
    RecyclerView.Adapter<PickItemRecyclerViewAdapter.PickIngredientViewHolder>() {

    val TAG = "PickIngredientRecyclerViewAdapter"

    private var pickIngredientRecyclerViewItemList = ArrayList<DirectIngredientList>()

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

    fun submitList(pickIngredientRecyclerViewItemList: ArrayList<DirectIngredientList>) {
        this.pickIngredientRecyclerViewItemList = pickIngredientRecyclerViewItemList
        notifyDataSetChanged()
    }

    inner class PickIngredientViewHolder(val binding: ItemPickIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(ingredient: DirectIngredientList, position: Int) {
            binding.tvIngredientName.text = ingredient.ingredientName
            if (ingredient.ingredientIcon != "") {
                Glide.with(ApplicationClass.instance).load(ingredient.ingredientIcon).into(binding.icIngredient)
            } else {
                Glide.with(ApplicationClass.instance).load(sSharedPreferences.getString(IC_DEFAULT, "")).into(binding.icIngredient)
            }
            binding.btnPickCancel.setOnClickListener {
                view.removePickItem(position)
            }
        }
    }
}