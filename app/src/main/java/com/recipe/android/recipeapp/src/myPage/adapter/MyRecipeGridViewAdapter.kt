package com.recipe.android.recipeapp.src.myPage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemMyPageRecipeBinding
import com.recipe.android.recipeapp.src.myRecipe.models.MyRecipeResult

class MyRecipeGridViewAdapter : RecyclerView.Adapter<MyRecipeGridViewAdapter.MyGridViewHolder>() {

    var myRecipeList = ArrayList<MyRecipeResult>()

    inner class MyGridViewHolder(val binding: ItemMyPageRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(myRecipeResult: MyRecipeResult) {
            if (myRecipeResult.thumbnail != "") {
                Glide.with(ApplicationClass.instance).load(myRecipeResult.thumbnail)
                    .into(binding.imgThumbnail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyGridViewHolder {
        return MyGridViewHolder(
            ItemMyPageRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyGridViewHolder, position: Int) {
        holder.bindWithView(myRecipeList[position])
    }

    override fun getItemCount(): Int = myRecipeList.size

    fun submitList(myRecipeList: ArrayList<MyRecipeResult>) {
        this.myRecipeList = myRecipeList
        notifyDataSetChanged()
    }
}