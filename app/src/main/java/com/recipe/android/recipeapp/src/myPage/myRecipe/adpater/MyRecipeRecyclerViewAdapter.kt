package com.recipe.android.recipeapp.src.myPage.myRecipe.adpater

import android.content.Intent
import android.graphics.Outline
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemMyRecipeListBinding
import com.recipe.android.recipeapp.src.myPage.myRecipe.models.MyRecipeResult
import com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeDetail.MyRecipeDetailActivity
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

        holder.itemView.setOnClickListener {
            val intent = Intent(ApplicationClass.instance, MyRecipeDetailActivity::class.java)
            intent.putExtra("myRecipeIdx", myRecipeList[position].userRecipeIdx)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(ApplicationClass.instance, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return myRecipeList.size
    }

    fun submitList(myRecipeList: ArrayList<MyRecipeResult>) {
        this.myRecipeList = myRecipeList
        notifyDataSetChanged()
    }
}