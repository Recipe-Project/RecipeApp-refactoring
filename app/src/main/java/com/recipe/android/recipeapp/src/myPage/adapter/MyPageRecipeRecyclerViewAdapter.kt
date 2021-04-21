package com.recipe.android.recipeapp.src.myPage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemMyPageRecipeBinding
import com.recipe.android.recipeapp.src.myPage.models.MyRecipe
import com.recipe.android.recipeapp.src.myPage.viewHolder.MyPageRecipeViewHolder

class MyPageRecipeRecyclerViewAdapter: RecyclerView.Adapter<MyPageRecipeViewHolder>() {

    var myRecipeItemList = ArrayList<MyRecipe>()
    var myRecipeTotalSize: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageRecipeViewHolder {
        return MyPageRecipeViewHolder(
            ItemMyPageRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyPageRecipeViewHolder, position: Int) {
        holder.bindWithView(myRecipeItemList[position], position, myRecipeTotalSize)
    }

    override fun getItemCount(): Int {
        return myRecipeItemList.size
    }

    fun submitList(myRecipeItemList: ArrayList<MyRecipe>, myRecipeTotalSize: Int) {

        this.myRecipeTotalSize = myRecipeTotalSize

        this.myRecipeItemList = myRecipeItemList
        notifyDataSetChanged()
    }
}