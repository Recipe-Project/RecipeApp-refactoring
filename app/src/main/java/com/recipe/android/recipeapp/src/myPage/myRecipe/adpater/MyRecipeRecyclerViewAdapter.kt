package com.recipe.android.recipeapp.src.myPage.myRecipe.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemMyRecipeListBinding
import com.recipe.android.recipeapp.src.myPage.myRecipe.models.MyRecipeResult
import com.recipe.android.recipeapp.src.myPage.myRecipe.viewHolder.MyRecipeViewHolder

class MyRecipeRecyclerViewAdapter: RecyclerView.Adapter<MyRecipeViewHolder>() {

    private var myRecipeList = ArrayList<MyRecipeResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecipeViewHolder {
        return MyRecipeViewHolder(
            ItemMyRecipeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyRecipeViewHolder, position: Int) {
        holder.bindWithView(myRecipeList[position])
    }

    override fun getItemCount(): Int {
        return myRecipeList.size
    }

    fun submitList(myRecipeList: ArrayList<MyRecipeResult>) {
        this.myRecipeList = myRecipeList
        notifyDataSetChanged()
    }
}