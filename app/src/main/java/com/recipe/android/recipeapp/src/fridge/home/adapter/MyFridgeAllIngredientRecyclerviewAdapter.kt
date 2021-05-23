package com.recipe.android.recipeapp.src.fridge.home.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.databinding.FragmentMyFridgeCategoryBinding
import com.recipe.android.recipeapp.src.fridge.home.FridgeUpdateService
import com.recipe.android.recipeapp.src.fridge.home.SwipeToDeleteCallback
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeUpdateView
import com.recipe.android.recipeapp.src.fridge.home.`interface`.IngredientUpdateView
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientRequest
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientResponse
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult

class MyFridgeAllIngredientRecyclerviewAdapter(val view: IngredientUpdateView) : RecyclerView.Adapter<MyFridgeAllIngredientRecyclerviewAdapter.CustomViewholder>() {

    var resultList = ArrayList<GetFridgeResult>()
    lateinit var customViewholder : CustomViewholder

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewholder {
        val binding = FragmentMyFridgeCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomViewholder(binding, parent.context, view)
    }

    override fun onBindViewHolder(holder: CustomViewholder, position: Int) {
        holder.bindWithView(resultList[position])
    }

    override fun getItemCount(): Int = resultList.size

    class CustomViewholder(val binding: FragmentMyFridgeCategoryBinding, val context : Context, val view: IngredientUpdateView) : RecyclerView.ViewHolder(binding.root), FridgeUpdateView {

        val myFridgeIngredientRecyclerviewAdapter = MyFridgeIngredientRecyclerviewAdapter(context, view)

        fun bindWithView(fridgeResult : GetFridgeResult) {
            val ingredientList = fridgeResult.ingredientList

            binding.rvIngredient.adapter = myFridgeIngredientRecyclerviewAdapter

            if(ingredientList.size != 0){
                // 카테고리 이름
                binding.tvCategory.text = fridgeResult.ingredientCategoryName

                myFridgeIngredientRecyclerviewAdapter.submitList(ingredientList)
                val swipeDelete = object : SwipeToDeleteCallback(context) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val ingredientName = ingredientList[viewHolder.adapterPosition].ingredientName
                        myFridgeIngredientRecyclerviewAdapter.deleteItem(viewHolder.adapterPosition)

                        // 냉장고 삭제 API 호출
                        FridgeUpdateService(this@CustomViewholder).tryDeleteIngredient(DeleteIngredientRequest(ingredientName))
                        binding.tvCategory.visibility = View.GONE
                    }
                }
                val touchHelper = ItemTouchHelper(swipeDelete)
                touchHelper.attachToRecyclerView(binding.rvIngredient)
            } else {
                binding.rvIngredient.visibility = View.GONE
                binding.tvCategory.visibility = View.GONE
                binding.bottomMargin.visibility = View.GONE
            }
        }

        override fun onDeleteIngredientSuccess(response: DeleteIngredientResponse) {

        }

        override fun onDeleteIngredientFailure(message: String) {

        }

    }

    fun submitList(resultList: ArrayList<GetFridgeResult>) {
        this.resultList = resultList
        notifyDataSetChanged()
    }


}