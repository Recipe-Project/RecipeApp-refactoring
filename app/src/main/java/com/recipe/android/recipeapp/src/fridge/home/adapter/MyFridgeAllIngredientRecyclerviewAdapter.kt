package com.recipe.android.recipeapp.src.fridge.home.adapter
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.ItemAllIngredientsBinding
import com.recipe.android.recipeapp.src.fridge.home.dialog.FridgeUpdateView
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientResponse
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult

class MyFridgeAllIngredientRecyclerviewAdapter() : RecyclerView.Adapter<MyFridgeAllIngredientRecyclerviewAdapter.CustomViewholder>() {

    var resultList = ArrayList<GetFridgeResult>()
    var isEditMode = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = ItemAllIngredientsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.bindWithView(resultList[position], isEditMode)
    }

    override fun getItemCount(): Int = resultList.size

    class CustomViewholder(
        val binding: ItemAllIngredientsBinding,
        val context : Context) : RecyclerView.ViewHolder(binding.root),
        FridgeUpdateView
    {
        val myFridgeIngredientRecyclerviewAdapter = MyFridgeIngredientRecyclerviewAdapter(context)

        fun bindWithView(fridgeResult: GetFridgeResult, isEditMode: Boolean) {
            val ingredientList = fridgeResult.ingredientList
            binding.rvIngredient.adapter = myFridgeIngredientRecyclerviewAdapter
            binding.rvIngredient.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            if(ingredientList.size != 0){
                // 카테고리 이름
                binding.tvCategory.text = fridgeResult.ingredientCategoryName
                Log.d("test", fridgeResult.ingredientCategoryName)
                myFridgeIngredientRecyclerviewAdapter.submitList(ingredientList, isEditMode)
                myFridgeIngredientRecyclerviewAdapter.getIndex(adapterPosition)
                Log.d("test", "adapterPosition : $adapterPosition" )
            } else {
                binding.rvIngredient.visibility = View.GONE
                binding.tvCategory.visibility = View.GONE
            }
        }

        override fun onDeleteIngredientSuccess(response: DeleteIngredientResponse) {
        }

        override fun onDeleteIngredientFailure(message: String) {

        }

    }

    fun submitList(resultList: ArrayList<GetFridgeResult>, isEditMode: Boolean) {
        this.resultList = resultList
        this.isEditMode = isEditMode
        notifyDataSetChanged()
    }


}