package com.recipe.android.recipeapp.src.search.publicRecipe.publicReDetail.presentation.viewpager.frag2

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemPublicViewpager2Binding
import com.recipe.android.recipeapp.src.search.publicRecipe.publicReDetail.model.PublicRecipeProcess

class Frag2Adapter(val context: Context) : RecyclerView.Adapter<Frag2Adapter.Frag2ViewHolder>() {

    var processList = listOf<PublicRecipeProcess>()

    inner class Frag2ViewHolder(private val binding: ItemPublicViewpager2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(item: PublicRecipeProcess) {
            with(binding) {
                processItem = item
                processNo = item.recipeProcessNo.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Frag2ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPublicViewpager2Binding.inflate(inflater, parent, false)
        return Frag2ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: Frag2ViewHolder, position: Int) {
        holder.bindWithView(processList[position])
    }

    override fun getItemCount(): Int = processList.size
}