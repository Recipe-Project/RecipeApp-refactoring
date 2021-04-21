package com.recipe.android.recipeapp.src.search.publicRecipe.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemPublicResultFragRecyclerviewBinding
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResult

class PublicResultRecyclerviewAdapter(val context : Context) : RecyclerView.Adapter<PublicResultRecyclerviewAdapter.CustomViewholder>() {

    interface PublicRecipeItemClick {
        fun onClick(view: View, position: Int)
    }
    var publicRecipeItemClick : PublicRecipeItemClick? = null

    interface PublicRecipeScrapClick {
        fun onClick(view: View, position: Int)
    }
    var publicRecipeScrapClick : PublicRecipeScrapClick? = null

    var publicResultList = ArrayList<PublicRecipeResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemPublicResultFragRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.title.text = publicResultList[position].title
        holder.content.text = publicResultList[position].description
        Glide.with(ApplicationClass.instance).load(publicResultList[position].thumbnail).transform(CenterCrop(), RoundedCorners(5)).into(holder.img)
        holder.cnt.text = publicResultList[position].userScrapCnt.toString()

        // 공공레시피 상세조회
        if(publicRecipeItemClick != null) {
            holder.layout.setOnClickListener {
                publicRecipeItemClick?.onClick(it, position)
            }
        }

        // 공공레시피 스크랩
        if(publicResultList[position].userScrapYN == "Y") {
            holder.scrap.setImageResource(R.drawable.ic_favorite_full_white)
        } else {
            holder.scrap.setImageResource(R.drawable.ic_favorite_empty_white)
        }
        if(publicRecipeScrapClick != null) {
            holder.scrap.setOnClickListener {
                if(publicResultList[position].userScrapYN == "Y") {
                    publicResultList[position].userScrapYN = "N"
                    holder.scrap.setImageResource(R.drawable.ic_favorite_empty_white)
                    Toast.makeText(context, "스크랩이 취소되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    publicResultList[position].userScrapYN = "Y"
                    holder.scrap.setImageResource(R.drawable.ic_favorite_full_white)
                    Toast.makeText(context, "스크랩 레시피에 담겼습니다.", Toast.LENGTH_SHORT).show()
                }
                publicRecipeScrapClick?.onClick(it, position)
            }
        }

    }

    override fun getItemCount(): Int = publicResultList.size

    class CustomViewholder(val binding : ItemPublicResultFragRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title : TextView = binding.recipeTitleTv
        val content : TextView = binding.recipeExplainTv
        val img : ImageView = binding.recipeImg
        val cnt : TextView = binding.favoriteRecipeCntTv
        val layout : ConstraintLayout = binding.publicResultFragRecyclerviewLayout
        val scrap : ImageView = binding.publicScrapBtn
    }

    fun submitList(publicResultList : ArrayList<PublicRecipeResult>) {
        this.publicResultList = publicResultList
        notifyDataSetChanged()
    }
}