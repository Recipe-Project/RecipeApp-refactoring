package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
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
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.*
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.`interface`.MyRecipeCreateActivityView
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.adapter.MultiplePickAllAdapter

class MultiplePickIngredientsDialog(context: Context, val view: MyRecipeCreateActivityView): Dialog(context), PickIngredientActivityView {

    val TAG = "MultiplePickIngredientsDialog"
    private lateinit var binding: DialogPickIngredientIconBinding

    val newIngredientsList = ArrayList<CategoryIngrediets>()
    lateinit var multiplePickAllAdapter: MultiplePickAllAdapter

    val pickIngredientsMyRecipe = ArrayList<Ingredient>()


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

        PickIngredientService(this).getIngredients("")

        multiplePickAllAdapter = MultiplePickAllAdapter(this)
        binding.rvIngredient.apply {
            adapter = multiplePickAllAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        binding.tvTitle.text = context.getString(R.string.ingredientPickDirect)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnSave.setOnClickListener {
            view.pickBtnSaveClick(pickIngredientsMyRecipe)
            dismiss()
        }
    }

    override fun onGetIngredientSuccess(response: IngredientResponse) {
        if (response.isSuccess) {
            val ingredientResult = response.result.ingredients

            newIngredientsList.clear()
            ingredientResult.forEach {
                newIngredientsList.add(it)
            }

            // 리사이클러뷰
            multiplePickAllAdapter.submitList(newIngredientsList)
        } else {
            Toast.makeText(context, context.getString(R.string.networkError), Toast.LENGTH_LONG).show()
            Log.d(TAG, "AddDirectActivity - onGetIngredientSuccess() : ${response.message}")
        }
    }

    override fun onPostIngredientSuccess(response: PostIngredientsResponse) {

    }

    override fun pickItem(ingredient: Ingredient) {
        pickIngredientsMyRecipe.add(ingredient)
        Log.d(TAG, "MultiplePickIngredientsDialog - pickItem() : $pickIngredientsMyRecipe")
    }

    override fun removePickItem(ingredient: Int) {
    }

    override fun addDirectFailure(message: String) {

    }

    override fun removePickMyIngredients(ingredient: Ingredient) {
        pickIngredientsMyRecipe.remove(ingredient)
        Log.d(TAG, "MultiplePickIngredientsDialog - removePickMyIngredients() : $pickIngredientsMyRecipe")
    }

    override fun getBasketCntSuccess(response: GetBasketCntResponse) {

    }
}