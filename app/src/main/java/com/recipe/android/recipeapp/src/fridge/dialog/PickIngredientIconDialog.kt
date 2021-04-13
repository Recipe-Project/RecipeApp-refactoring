package com.recipe.android.recipeapp.src.fridge.dialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.DialogPickIngredientIconBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientAllRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.Ingredient
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResponse
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResult
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.PostIngredientsResponse

class PickIngredientIconDialog(
    context: Context,
    private var activity: Activity,
    private val ingredientsList: ArrayList<IngredientResult>,
    val pickView: PickIcon
) : Dialog(context), PickIngredientActivityView {

    private lateinit var binding: DialogPickIngredientIconBinding

    // pick icon url
    var pickIconUrl: String? = null

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

        val ingredientAllRecyclerViewAdapter = IngredientAllRecyclerViewAdapter(this)
        binding.rvIngredient.apply {
            adapter = ingredientAllRecyclerViewAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        }
        ingredientAllRecyclerViewAdapter.submitList(ingredientsList)

        binding.btnCancel.setOnClickListener(PickDialogListener())
        binding.btnSave.setOnClickListener(PickDialogListener())

    }

    inner class PickDialogListener : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v?.id) {
                binding.btnSave.id -> {
                    pickView.btnSaveClick(pickIconUrl)
                }
                binding.btnCancel.id -> dismiss()
            }
        }

    }

    interface PickIcon {
        fun btnSaveClick(pickIconUrl: String?)
    }

    override fun onGetIngredientSuccess(response: IngredientResponse) {
    }

    override fun onPostIngredientSuccess(response: PostIngredientsResponse) {
    }

    override fun pickItem(ingredient: Ingredient) {
        pickIconUrl = ingredient.ingredientIcon.toString()
    }

    override fun removePickItem(ingredient: Int) {
    }

    override fun addDirectFailure(message: String) {
    }


}