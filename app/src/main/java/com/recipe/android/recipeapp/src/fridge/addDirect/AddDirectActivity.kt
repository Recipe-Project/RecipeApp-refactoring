package com.recipe.android.recipeapp.src.fridge.addDirect

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.IC_DEFAULT
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityAddDirectBinding
import com.recipe.android.recipeapp.src.fridge.dialog.PickIngredientIconDialog
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.IngredientResult
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.PostIngredientsResponse
import java.util.ArrayList

interface AddDirectActivityView{
    fun onAddDirectSuccess(postIngredientsResponse: PostIngredientsResponse)
    fun onAddDirectFailure(message: String)
}

class AddDirectActivity : BaseActivity<ActivityAddDirectBinding>(ActivityAddDirectBinding::inflate),
    PickIngredientIconDialog.PickIcon, AddDirectActivityView {
    val TAG = "AddDirectActivity"

    var pickIconUrl: String? = null

    var ingredientName = ""
    var ingredientCategoryIdx: Int? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ingredientsList = intent.extras?.getParcelableArrayList<Parcelable>("ingredientList") as ArrayList<IngredientResult>
        Log.d(TAG, "AddDirectActivity - onCreate() : $ingredientsList")

        // 서버에서 보낸 카테고리
        val categoryList = ArrayList<String>()
        ingredientsList.forEach {
            categoryList.add(it.ingredientCategoryName)
        }

        // cancel
        binding.btnCancel.setOnClickListener {
            finish()
        }

        // dialog
        val pickIngredientIconDialog = PickIngredientIconDialog(
            this,
            this,
            ingredientsList,
            this
        )
        binding.btnAddIcon.setOnClickListener {
            pickIngredientIconDialog.show()
        }

        // 재료이름
        binding.etInputGredient.setOnClickListener {
            it.backgroundTintList = ColorStateList.valueOf(getColor(R.color.red))
        }

        binding.etInputGredient.setOnTouchListener { v, event ->
            v.backgroundTintList = ColorStateList.valueOf(getColor(R.color.red))
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val imm =
                        this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

                    binding.etInputGredient.requestFocus()

                    imm.showSoftInput(binding.etInputGredient, 0)

                    binding.etInputGredient.setOnKeyListener { v, keyCode, event ->
                        if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            imm.hideSoftInputFromWindow(binding.etInputGredient.windowToken, 0)
                            true
                        } else false
                    }

                }
            }
            true
        }

        // 재료추가하기 버튼
        binding.btnAddGredient.setOnClickListener {
            ingredientName = binding.etInputGredient.text.toString()
            if (pickIconUrl == null) {
                pickIconUrl = ApplicationClass.sSharedPreferences.getString(IC_DEFAULT, "")
            }

            if (ingredientCategoryIdx == null) {
                showCustomToast("카테고리를 선택해주세요.")
            } else if (ingredientName == "") {
                showCustomToast("재료명을 입력해주세요.")
            } else {
                // 재료 직접 입력으로 냉장고 바구니 담기
                AddDirectService(this).addDirectService(
                    ingredientName,
                    pickIconUrl!!,
                    ingredientCategoryIdx!!
                )
            }
        }



        binding.btnCategoryBeef.setOnClickListener {
            grayToBlue(binding.btnCategoryBeef)
            blueToGray(binding.btnCategoryVegetable)
            blueToGray(binding.btnCategoryFruit)
            blueToGray(binding.btnCategoryAquaticProducts)
            blueToGray(binding.btnCategorySeasoning)
            blueToGray(binding.btnCategoryProduct)
            blueToGray(binding.btnCategoryEtc)
            ingredientCategoryIdx = 3
            binding.btnAddGredient.backgroundTintList = ColorStateList.valueOf(getColor(R.color.sky_blue))
        }

        binding.btnCategoryVegetable.setOnClickListener {
            grayToBlue(binding.btnCategoryVegetable)
            blueToGray(binding.btnCategoryBeef)
            blueToGray(binding.btnCategoryFruit)
            blueToGray(binding.btnCategoryAquaticProducts)
            blueToGray(binding.btnCategorySeasoning)
            blueToGray(binding.btnCategoryProduct)
            blueToGray(binding.btnCategoryEtc)
            ingredientCategoryIdx = 1
            binding.btnAddGredient.backgroundTintList = ColorStateList.valueOf(getColor(R.color.sky_blue))
        }

        binding.btnCategoryFruit.setOnClickListener {
            grayToBlue(binding.btnCategoryFruit)
            blueToGray(binding.btnCategoryBeef)
            blueToGray(binding.btnCategoryVegetable)
            blueToGray(binding.btnCategoryAquaticProducts)
            blueToGray(binding.btnCategorySeasoning)
            blueToGray(binding.btnCategoryProduct)
            blueToGray(binding.btnCategoryEtc)
            ingredientCategoryIdx = 2
            binding.btnAddGredient.backgroundTintList = ColorStateList.valueOf(getColor(R.color.sky_blue))
        }

        binding.btnCategoryAquaticProducts.setOnClickListener {
            grayToBlue(binding.btnCategoryAquaticProducts)
            blueToGray(binding.btnCategoryBeef)
            blueToGray(binding.btnCategoryFruit)
            blueToGray(binding.btnCategoryVegetable)
            blueToGray(binding.btnCategorySeasoning)
            blueToGray(binding.btnCategoryProduct)
            blueToGray(binding.btnCategoryEtc)

            ingredientCategoryIdx = 4
            binding.btnAddGredient.backgroundTintList = ColorStateList.valueOf(getColor(R.color.sky_blue))
        }

        binding.btnCategorySeasoning.setOnClickListener {
            grayToBlue(binding.btnCategorySeasoning)
            blueToGray(binding.btnCategoryBeef)
            blueToGray(binding.btnCategoryFruit)
            blueToGray(binding.btnCategoryAquaticProducts)
            blueToGray(binding.btnCategoryVegetable)
            blueToGray(binding.btnCategoryProduct)
            blueToGray(binding.btnCategoryEtc)
            ingredientCategoryIdx = 5
            binding.btnAddGredient.backgroundTintList = ColorStateList.valueOf(getColor(R.color.sky_blue))
        }

        binding.btnCategoryProduct.setOnClickListener {
            grayToBlue(binding.btnCategoryProduct)
            blueToGray(binding.btnCategoryBeef)
            blueToGray(binding.btnCategoryFruit)
            blueToGray(binding.btnCategoryAquaticProducts)
            blueToGray(binding.btnCategorySeasoning)
            blueToGray(binding.btnCategoryVegetable)
            blueToGray(binding.btnCategoryEtc)
            ingredientCategoryIdx = 6
            binding.btnAddGredient.backgroundTintList = ColorStateList.valueOf(getColor(R.color.sky_blue))
        }

        binding.btnCategoryEtc.setOnClickListener {
            grayToBlue(binding.btnCategoryEtc)
            blueToGray(binding.btnCategoryBeef)
            blueToGray(binding.btnCategoryFruit)
            blueToGray(binding.btnCategoryAquaticProducts)
            blueToGray(binding.btnCategorySeasoning)
            blueToGray(binding.btnCategoryProduct)
            blueToGray(binding.btnCategoryVegetable)
            ingredientCategoryIdx = 7
            binding.btnAddGredient.backgroundTintList = ColorStateList.valueOf(getColor(R.color.sky_blue))
        }

    }

    override fun btnSaveClick(pickIconUrl: String?) {
        this.pickIconUrl = pickIconUrl
        if (pickIconUrl != null) {
            Glide.with(this).load(pickIconUrl).into(binding.btnAddIcon)
        }
        Log.d(TAG, "AddDirectActivity - btnSaveClick() : ${this.pickIconUrl}")
    }

    private fun grayToBlue(tv: AppCompatTextView) {
        tv.backgroundTintList = ColorStateList.valueOf(getColor(R.color.sky_blue))
    }

    private fun blueToGray(tv: AppCompatTextView){
        tv.backgroundTintList = ColorStateList.valueOf(getColor(R.color.gray_000))
    }

    override fun onAddDirectSuccess(response: PostIngredientsResponse) {
        if (response.isSuccess) {
            showCustomToast(getString(R.string.directAddSuccess))
            finish()
        } else {
            when (response.code) {
                2065 -> showCustomToast(response.message)
                2069 -> showCustomToast(response.message)
                else -> showCustomToast(getString(R.string.networkError))
            }
        }
    }

    override fun onAddDirectFailure(message: String) {
        Log.d(TAG, "AddDirectActivity - onAddDirectFailure() : $message")
        showCustomToast(getString(R.string.networkError))
    }
}