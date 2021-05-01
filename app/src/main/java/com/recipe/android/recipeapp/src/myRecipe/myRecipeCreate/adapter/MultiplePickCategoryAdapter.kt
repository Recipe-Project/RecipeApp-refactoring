package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemIngredientBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient

class MultiplePickCategoryAdapter(val view: PickIngredientActivityView) :
    RecyclerView.Adapter<MultiplePickCategoryAdapter.MultiplePickCategoryViewHolder>() {

    val TAG = "MultiplePickCategoryAdapter"

    var ingredientList = ArrayList<Ingredient>()

    var clickPosition = -1

    inner class MultiplePickCategoryViewHolder(val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindWithView(ingredient: Ingredient) {
            binding.tvIngredientName.text = ingredient.ingredientName
            if (ingredient.ingredientIcon != "") {
                Glide.with(ApplicationClass.instance).load(ingredient.ingredientIcon)
                    .into(binding.icIngredient)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MultiplePickCategoryViewHolder {
        return MultiplePickCategoryViewHolder(
            ItemIngredientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MultiplePickCategoryViewHolder, position: Int) {
        holder.bindWithView(ingredientList[position])

        holder.binding.circleGreen.visibility = View.INVISIBLE

        holder.itemView.setOnClickListener {
            clickPosition = position

            if (holder.binding.circleGreen.visibility == View.INVISIBLE) {
                holder.binding.circleGreen.visibility = View.VISIBLE
                Log.d(TAG, "MultiplePickCategoryAdapter - onBindViewHolder() : ${ingredientList[position]}")
                view.pickItem(ingredientList[position])
            } else {
                holder.binding.circleGreen.visibility = View.INVISIBLE
                view.removePickMyIngredients(ingredientList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun submitList(ingredientList: ArrayList<Ingredient>?) {
        if (ingredientList != null) {
            this.ingredientList = ingredientList
        }
        notifyDataSetChanged()
    }
}