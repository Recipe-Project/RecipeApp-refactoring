package com.recipe.android.recipeapp.src.setting.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.DialogDeleteIdBinding
import com.recipe.android.recipeapp.src.setting.SettingService

class DeleteIdDialog: BaseActivity<DialogDeleteIdBinding>(DialogDeleteIdBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        val userIdx = intent.getIntExtra("userIdx", 0)


        binding.btnYes.setOnClickListener {
            SettingService().deleteId(userIdx)
        }

        binding.btnNo.setOnClickListener {
            finish()
        }

    }
}