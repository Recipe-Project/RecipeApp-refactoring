package com.recipe.android.recipeapp.src.search.publicReDetail.presentation.viewpager.frag1

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemPublicViewpager1Binding
import com.recipe.android.recipeapp.src.search.publicReDetail.model.PublicRecipeIngredient

class Frag1Adapter(val context: Context) : RecyclerView.Adapter<Frag1Adapter.Frag1ViewHolder>() {

    var ingredientList = arrayListOf<PublicRecipeIngredient>()

    inner class Frag1ViewHolder(private val binding: ItemPublicViewpager1Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(item: PublicRecipeIngredient) {
            with(binding) {
                ingredientItem = item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Frag1ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPublicViewpager1Binding.inflate(inflater, parent, false)
        return Frag1ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Frag1ViewHolder, position: Int) {
        holder.bindWithView(ingredientList[position])
    }

    override fun getItemCount(): Int = ingredientList.size
}