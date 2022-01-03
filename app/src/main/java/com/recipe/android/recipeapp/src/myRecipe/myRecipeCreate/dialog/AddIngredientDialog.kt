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
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.databinding.DialogAddIngredientBinding

class AddIngredientDialog(context: Context, private var activity: Activity, val view: MyRecipeCreateActivityView) : Dialog(context) {

    private lateinit var binding: DialogAddIngredientBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogAddIngredientBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        window!!.setBackgroundDrawable(ColorDrawable())
        window!!.setGravity(Gravity.BOTTOM)
        val params: WindowManager.LayoutParams = window!!.attributes
        params.width = ActionBar.LayoutParams.MATCH_PARENT
        params.height = 450
        window!!.attributes = params
        window!!.attributes.windowAnimations = R.style.DialogAnimation


        binding.btnAddDirect.setOnClickListener(SelectAddIngredientListener())
        binding.btnPickIngredient.setOnClickListener(SelectAddIngredientListener())
    }

    inner class SelectAddIngredientListener : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v?.id) {
                binding.btnAddDirect.id -> {
                    // 재료 직접 추가 버튼
                    view.selectAddDirect()
                    dismiss()
                }
                binding.btnPickIngredient.id -> {
                    // 재료 선택 버튼
                    view.selectPickDirect()
                    dismiss()
                }
            }
        }
    }
}