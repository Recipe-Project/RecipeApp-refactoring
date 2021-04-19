package com.recipe.android.recipeapp.src.fridge.receipt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemReceiptIngredientRecyclerviewBinding
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.ReceiptIngredientDialogView
import com.recipe.android.recipeapp.src.fridge.receipt.models.PostReceiptIngredientResult

class ReceiptIngredientRecyclerviewAdapter(val view : ReceiptIngredientDialogView) : RecyclerView.Adapter<ReceiptIngredientRecyclerviewAdapter.CustomViewholder>() {

    var receiptIngredientList = ArrayList<PostReceiptIngredientResult>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemReceiptIngredientRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding, view)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.bindWithView(receiptIngredientList[position], position)

    }

    override fun getItemCount(): Int = receiptIngredientList.size

    class CustomViewholder(val binding: ItemReceiptIngredientRecyclerviewBinding, val view : ReceiptIngredientDialogView) : RecyclerView.ViewHolder(binding.root) {
        fun bindWithView(result : PostReceiptIngredientResult, position: Int) {
            binding.ingredientTv.text = result.ingredientName
            binding.clearIngredientIv.setOnClickListener {
                view.clearIngredient(position)
            }
            binding.pickIngredientIv.setOnClickListener {
                view.pickIngredient()
            }
        }
    }

    fun submitList(receiptIngredientList : ArrayList<PostReceiptIngredientResult>) {
        this.receiptIngredientList = receiptIngredientList
        notifyDataSetChanged()
    }
}