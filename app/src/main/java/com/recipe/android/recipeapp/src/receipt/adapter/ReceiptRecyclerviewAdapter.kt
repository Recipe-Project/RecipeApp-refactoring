package com.recipe.android.recipeapp.src.receipt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemReceiptActivityRecyclerviewBinding
import com.recipe.android.recipeapp.src.receipt.models.GetAllReceiptResult

class ReceiptRecyclerviewAdapter(private val receiptList : ArrayList<GetAllReceiptResult>) : RecyclerView.Adapter<ReceiptRecyclerviewAdapter.CustomViewholder>() {

    interface ReceiptMoreBtnItemClick {
        fun onClick(view: View, position: Int)
    }
    var receiptMoreBtnItemClick : ReceiptMoreBtnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemReceiptActivityRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.title.text = receiptList[position].title
        holder.date.text = receiptList[position].receiptDate

    }

    override fun getItemCount(): Int = receiptList.size

    class CustomViewholder(val binding: ItemReceiptActivityRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title : TextView = binding.receiptShopNameTv
        val date : TextView = binding.receiptDateTv
        val contents : TextView = binding.receiptItemListTv
        val moreBtn : ImageView = binding.receiptMoreBtn
    }
}