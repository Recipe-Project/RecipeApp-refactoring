package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.dialog

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
import com.recipe.android.recipeapp.src.fridge.pickIngredient.PickIngredientActivityView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.adapter.IngredientAllRecyclerViewAdapter
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.*
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.MyRecipeCreateService
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.models.MyRecipeCreateResponse

interface MyRecipeCreateActivityView {
    fun onPostMyRecipeCreateSuccess(response: MyRecipeCreateResponse)
    fun onMyRecipeCreateFailure(message: String)

    fun onPatchMyRecipeSuccess(response: MyRecipeCreateResponse)
    fun onPatchMyRecipeFailure(message: String)

    fun onGetIngredientMyRecipeSuccess(response: IngredientResponse)

    fun selectAddDirect()
    fun selectPickDirect()

    fun pickItem(ingredient: Ingredient)
    fun removePickItem(ingredient: Int)

    fun cancelCreateRecipe()

    fun pickBtnSaveClick(pickIngredientsMyRecipe: ArrayList<Ingredient>?)
}

class PickIconDialog(
    context: Context,
    private var activity: Activity,
    val pickView: PickIcon
) : Dialog(context), PickIngredientActivityView, MyRecipeCreateActivityView {

    private lateinit var binding: DialogPickIngredientIconBinding

    private val ingredientsList = ArrayList<CategoryIngrediets>()

    // pick icon url
    var pickIconUrl: String = ""
    var pickIconName: String = ""

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

        // 재료조회
        MyRecipeCreateService(this).getIngredients("")


        ingredientAllRecyclerViewAdapter = IngredientAllRecyclerViewAdapter(this)
        binding.rvIngredient.apply {
            adapter = ingredientAllRecyclerViewAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        }

        binding.btnCancel.setOnClickListener(PickDialogListener())
        binding.btnSave.setOnClickListener(PickDialogListener())

    }

    inner class PickDialogListener : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v?.id) {
                binding.btnSave.id -> {
                    pickView.btnSaveClick(pickIconUrl, pickIconName)
                    dismiss()
                }
                binding.btnCancel.id -> dismiss()
            }
        }

    }

    interface PickIcon {
        fun btnSaveClick(pickIconUrl: String, name: String)
    }

    override fun onPostMyRecipeCreateSuccess(response: MyRecipeCreateResponse) {
    }

    override fun onMyRecipeCreateFailure(message: String) {

    }

    override fun onPatchMyRecipeSuccess(response: MyRecipeCreateResponse) {

    }

    override fun onPatchMyRecipeFailure(message: String) {

    }

    override fun onGetIngredientMyRecipeSuccess(response: IngredientResponse) {
        if (response.isSuccess) {
            ingredientsList.clear()
            response.result.ingredients.forEach {
                ingredientsList.add(it)
            }
            ingredientAllRecyclerViewAdapter.submitList(ingredientsList)
        }
    }

    override fun selectAddDirect() {

    }

    override fun selectPickDirect() {

    }

    override fun onGetIngredientSuccess(response: IngredientResponse) {

    }

    override fun onPostIngredientSuccess(response: PostIngredientsResponse) {
    }

    override fun pickItem(ingredient: Ingredient) {
        pickIconUrl = ingredient.ingredientIcon
        pickIconName = ingredient.ingredientName
    }

    override fun removePickItem(ingredient: Int) {
    }

    override fun cancelCreateRecipe() {

    }

    override fun pickBtnSaveClick(pickIngredientsMyRecipe: ArrayList<Ingredient>?) {

    }

    override fun addDirectFailure(message: String) {
    }

    override fun removePickMyIngredients(ingredient: Ingredient) {

    }

    override fun getBasketCntSuccess(response: GetBasketCntResponse) {

    }


}