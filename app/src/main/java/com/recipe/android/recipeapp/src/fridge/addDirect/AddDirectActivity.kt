package com.recipe.android.recipeapp.src.fridge.addDirect

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityAddDirectBinding
import com.recipe.android.recipeapp.src.fridge.dialog.PickIngredientIconDialog
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResult

class AddDirectActivity : BaseActivity<ActivityAddDirectBinding>(ActivityAddDirectBinding::inflate),
    PickIngredientIconDialog.PickIcon {
    val TAG = "AddDirectActivity"

    var pickIconUrl: String? = null

    var ingredientName = ""
    var ingredientCategoryIdx: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ingredientsList = intent.getParcelableArrayExtra("ingredientsList")

        // cancel
        binding.btnCancel.setOnClickListener {
            finish()
            // 수정 필요
            // ApplicationClass.sSharedPreferences.getString()
        }

        // dialog
        val pickIngredientIconDialog = PickIngredientIconDialog(
            this,
            this,
            ingredientsList as ArrayList<IngredientResult>,
            this
        )
        binding.btnAddIcon.setOnClickListener {
            pickIngredientIconDialog.show()
        }

        // 재료이름
        binding.etInputGredient.setOnClickListener {
            it.backgroundTintList = ColorStateList.valueOf(getColor(R.color.red))
        }

        // 카테고리 선택
        binding.btnAddGredient.isClickable = ingredientCategoryIdx != null

        // 재료추가하기 버튼
        binding.btnAddGredient.setOnClickListener {
            ingredientName = binding.etInputGredient.text.toString()
            if (pickIconUrl == null) {
                pickIconUrl = "fjskfjdkf" // 수정 필요 - 기본 아이콘 제작 후 파베에 올리고 url 넣기
            }
            // 재료 직접 입력으로 냉장고 바구니 담기
            AddDirectService().addDirectService(
                ingredientName,
                pickIconUrl!!,
                ingredientCategoryIdx!!
            )
        }


    }

    // 수정 필요 . 카테고리 리사이클러뷰로 변경 필
    inner class CategoryClick(): View.OnClickListener{
        override fun onClick(v: View?) {
            when(v?.id) {
                binding.btnCategoryBeef.id -> ingredientCategoryIdx = 0
            }
        }

    }

    override fun btnSaveClick(pickIconUrl: String?) {
        this.pickIconUrl = pickIconUrl
        if (pickIconUrl != null) {
            Glide.with(this).load(pickIconUrl).into(binding.btnAddIcon)
        }
    }
}