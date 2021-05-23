package com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemPickIngredientBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.DirectIngredientList

class MyRecipeIngredientRecyclerViewAdapter :
    RecyclerView.Adapter<MyRecipeIngredientRecyclerViewAdapter.PickIngredientViewHolder>() {

    val TAG = "PickIngredientRecyclerViewAdapter"

    private var pickIngredientRecyclerViewItemList = ArrayList<DirectIngredientList>()

    val context = ApplicationClass.instance

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
            if (ingredient.ingredientIcon != null) {
                Glide.with(context).load(ingredient.ingredientIcon).into(binding.icIngredient)
            } else {
               binding.icIngredient.setBackgroundColor(context.getColor(R.color.white))
            }
            binding.btnPickCancel.visibility = View.GONE
        }
    }
}