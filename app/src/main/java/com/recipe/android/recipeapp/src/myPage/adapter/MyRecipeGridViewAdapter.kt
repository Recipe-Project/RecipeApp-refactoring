package com.recipe.android.recipeapp.src.myPage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemMyPageRecipeBinding
import com.recipe.android.recipeapp.src.myRecipe.models.MyRecipeResult
import com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.MyRecipeDetailActivity

class MyRecipeGridViewAdapter : RecyclerView.Adapter<MyRecipeGridViewAdapter.MyGridViewHolder>() {

    var myRecipeList = ArrayList<MyRecipeResult>()

    inner class MyGridViewHolder(val binding: ItemMyPageRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(myRecipeResult: MyRecipeResult) {
            if (myRecipeResult.thumbnail != null) {
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

        holder.itemView.setOnClickListener {
            val intent = Intent(ApplicationClass.instance, MyRecipeDetailActivity::class.java)
            intent.putExtra("myRecipeIdx", myRecipeList[position].userRecipeIdx)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ContextCompat.startActivity(ApplicationClass.instance, intent, null)
        }
    }

    override fun getItemCount(): Int = myRecipeList.size

    fun submitList(myRecipeList: ArrayList<MyRecipeResult>) {
        this.myRecipeList = myRecipeList
        notifyDataSetChanged()
    }
}