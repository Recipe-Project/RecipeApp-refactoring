package com.recipe.android.recipeapp.src.receipt.dialog

import android.app.ActionBar
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import com.recipe.android.recipeapp.databinding.DialogReceiptBinding

class ReceiptDialog(context: Context) : Dialog(context){

    private lateinit var binding : DialogReceiptBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogReceiptBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val params = window!!.attributes
        params!!.width = ActionBar.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT

        window!!.attributes = params
        window!!.setGravity(Gravity.BOTTOM)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.clearReceiptLayout.setOnClickListener {
            // 영수증 삭제
        }

        binding.inputIngredientsLayout.setOnClickListener {
            // 냉장고에 재료 등록
        }
    }

    override fun show() {
        super.show()
    }
}