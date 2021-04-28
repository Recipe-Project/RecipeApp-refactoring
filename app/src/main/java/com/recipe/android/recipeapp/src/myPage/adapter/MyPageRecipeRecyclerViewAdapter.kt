package com.recipe.android.recipeapp.src.myPage.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemMyPageRecipeBinding
import com.recipe.android.recipeapp.src.myPage.models.MyRecipe
import com.recipe.android.recipeapp.src.myPage.viewHolder.MyPageRecipeViewHolder
import com.recipe.android.recipeapp.src.myRecipe.MyRecipeActivity
import com.recipe.android.recipeapp.src.myRecipe.myRecipeDetail.MyRecipeDetailActivity

class MyPageRecipeRecyclerViewAdapter: RecyclerView.Adapter<MyPageRecipeViewHolder>() {

    var myRecipeItemList = ArrayList<MyRecipe>()
    var myRecipeTotalSize: Int = 0
    val context = ApplicationClass.instance

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPageRecipeViewHolder {
        return MyPageRecipeViewHolder(
            ItemMyPageRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyPageRecipeViewHolder, position: Int) {
        holder.bindWithView(myRecipeItemList[position], position, myRecipeTotalSize)

        if (position == 5) {
            holder.itemView.setOnClickListener {
                val intent = Intent(context, MyRecipeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(context, intent, null)
            }
        } else {
            holder.itemView.setOnClickListener {
                val intent = Intent(context, MyRecipeDetailActivity::class.java)
                intent.putExtra("myRecipeIdx", myRecipeItemList[position].myRecipeIdx)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(context, intent, null)
            }
        }
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