package com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.recipe.android.recipeapp.databinding.DialogCancelCreateRecipeBinding

class CreateCancelDialog(context: Context, val view: MyRecipeCreateActivityView): Dialog(context) {

    val TAG = "DeleteIdDialog"

    private lateinit var binding: DialogCancelCreateRecipeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogCancelCreateRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(true)
        setCancelable(true)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val params = window!!.attributes
        params.width = WindowManager.LayoutParams.WRAP_CONTENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params

        binding.btnYes.setOnClickListener {
            view.cancelCreateRecipe()
            dismiss()
        }

        binding.btnNo.setOnClickListener {
            dismiss()
        }

    }
}