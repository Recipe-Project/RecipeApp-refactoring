package com.recipe.android.recipeapp.src.fridge.home.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.DialogSignOutBinding
import com.recipe.android.recipeapp.src.MainActivity
import com.recipe.android.recipeapp.src.fridge.FridgeFragment
import com.recipe.android.recipeapp.src.fridge.home.FridgeUpdateService
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeUpdateView
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientRequest
import com.recipe.android.recipeapp.src.fridge.home.models.DeleteIngredientResponse

class DeleteDialog : BaseActivity<DialogSignOutBinding>(DialogSignOutBinding::inflate), FridgeUpdateView {

    val TAG = "DeleteDialog"
    var deleteList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.appCompatTextView18.text = "삭제"
        binding.appCompatTextView19.text = "냉장고 재료를 삭제하시겠어요?"

        binding.btnYes.setOnClickListener {
            for (i in FridgeFragment.checkboxList) {
                if(i.checked) {
                    deleteList.add(i.deleteIngredient)
                }
            }
            FridgeUpdateService(this).tryDeleteIngredient(DeleteIngredientRequest(deleteList))
            deleteList.clear()
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.btnNo.setOnClickListener {
            finish()
        }
    }

    override fun onDeleteIngredientSuccess(response: DeleteIngredientResponse) {

    }

    override fun onDeleteIngredientFailure(message: String) {

    }
}