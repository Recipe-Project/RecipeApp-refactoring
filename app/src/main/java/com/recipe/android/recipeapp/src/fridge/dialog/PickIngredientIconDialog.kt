package com.recipe.android.recipeapp.src.fridge.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.DialogPickIngredientIconBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.PickIngredientService
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientAllRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.*

class PickIngredientIconDialog(
    context: Context,
    private var activity: Activity,
    private val ingredientsList: ArrayList<CategoryIngrediets>?,
    val pickView: PickIcon
) : Dialog(context), PickIngredientActivityView {

    private lateinit var binding: DialogPickIngredientIconBinding
    val TAG = "PickIngredientIconDialog"

    // pick icon url
    var pickIconUrl: String? = null

    val newIngredientsList = ArrayList<CategoryIngrediets>()
    lateinit var ingredientAllRecyclerViewAdapter: IngredientAllRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogPickIngredientIconBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setGravity(Gravity.BOTTOM)
        val params: WindowManager.LayoutParams = window!!.attributes
        params.width = ActionBar.LayoutParams.MATCH_PARENT
        params.height =
            (ApplicationClass.instance.resources.displayMetrics.heightPixels * 0.9).toInt()
        window!!.attributes = params
        window!!.attributes.windowAnimations = R.style.DialogAnimation

        ingredientAllRecyclerViewAdapter = IngredientAllRecyclerViewAdapter(this)
        binding.rvIngredient.apply {
            adapter = ingredientAllRecyclerViewAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        }

        if (ingredientsList == null) {
            PickIngredientService(this).getIngredients("")
        } else {
            ingredientAllRecyclerViewAdapter.submitList(ingredientsList)
        }

        binding.btnCancel.setOnClickListener(PickDialogListener())
        binding.btnSave.setOnClickListener(PickDialogListener())


    }

    inner class PickDialogListener : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v?.id) {
                binding.btnSave.id -> {
                    pickView.btnSaveClick(pickIconUrl)
                    dismiss()
                }
                binding.btnCancel.id -> dismiss()
            }
        }

    }

    interface PickIcon {
        fun btnSaveClick(pickIconUrl: String?)
    }

    override fun onGetIngredientSuccess(response: IngredientResponse) {
        if (response.isSuccess) {
            val ingredientResult = response.result.ingredients

            newIngredientsList.clear()
            ingredientResult.forEach {
                newIngredientsList.add(it)
            }

            // 리사이클러뷰
            ingredientAllRecyclerViewAdapter.submitList(newIngredientsList)
        } else {
            Toast.makeText(context, context.getString(R.string.networkError), Toast.LENGTH_LONG)
                .show()
            Log.d(TAG, "AddDirectActivity - onGetIngredientSuccess() : ${response.message}")
        }
    }

    override fun onPostIngredientSuccess(response: PostIngredientsResponse) {
    }

    override fun pickItem(ingredient: Ingredient) {
        pickIconUrl = ingredient.ingredientIcon
    }

    override fun removePickItem(ingredient: Int) {
    }

    override fun addDirectFailure(message: String) {
    }

    override fun removePickMyIngredients(ingredient: Ingredient) {

    }

    override fun getBasketCntSuccess(response: GetBasketCntResponse) {

    }


}