package com.recipe.android.recipeapp.src.fridge

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentFridgeBinding
import com.recipe.android.recipeapp.src.receipt.ReceiptActivity
import com.recipe.android.recipeapp.src.fridge.pickIngredient.PickIngredientActivity
import java.text.SimpleDateFormat
import java.util.*

class FridgeFragment :
    BaseFragment<FragmentFridgeBinding>(FragmentFridgeBinding::bind, R.layout.fragment_fridge) {

    // fab
    var isClicked: Boolean = false
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open)
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close)
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim)
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 현재 날짜 세팅
        setCurrentDay()

        // 임시 화면 전환
        binding.fridgeFragTitle.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext(), ReceiptActivity::class.java ))
        }

        // + 버튼 클릭
        binding.fabAdd.setOnClickListener {
            setVisibility(isClicked)
            setFabAnim(isClicked)
            isClicked = !isClicked
        }

        binding.bgFloating.setOnClickListener {
            setVisibility(isClicked)
            setFabAnim(isClicked)
            isClicked = !isClicked
        }


        // 직접입력 버튼 클릭
        binding.fabAddDirect.setOnClickListener {
            val intent = Intent(requireContext(), PickIngredientActivity::class.java)
            startActivity(intent)
        }

        // 영수증 입력 버튼 클릭
        binding.fabAddRecipe.setOnClickListener {

        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun setCurrentDay() {
        val calendar = Calendar.getInstance().time

        val today = SimpleDateFormat("yy/MM/dd").format(calendar)
        binding.fridgeFragDateTv.text = today
    }

    private fun setFabAnim(isClicked: Boolean) {
        if (!isClicked) {
            binding.fabAddDirect.startAnimation(fromBottom)
            binding.fabAddRecipe.startAnimation(fromBottom)
            binding.fabAdd.startAnimation(rotateOpen)
            binding.bgFloating.setBackgroundResource(R.drawable.blur_dark)
            binding.bgFloating.visibility = View.VISIBLE
        } else {
            binding.fabAddDirect.startAnimation(toBottom)
            binding.fabAddRecipe.startAnimation(toBottom)
            binding.fabAdd.startAnimation(rotateClose)
            binding.bgFloating.setBackgroundColor(Color.TRANSPARENT)
            binding.bgFloating.visibility = View.GONE
        }
    }

    private fun setVisibility(isClicked: Boolean) {
        if (!isClicked) {
            binding.fabAddDirect.visibility = View.VISIBLE
            binding.fabAddRecipe.visibility = View.VISIBLE
            binding.tvAddDirect.visibility = View.VISIBLE
            binding.tvAddRecipe.visibility = View.VISIBLE
        } else {
            binding.fabAddDirect.visibility = View.INVISIBLE
            binding.fabAddRecipe.visibility = View.INVISIBLE
            binding.tvAddDirect.visibility = View.INVISIBLE
            binding.tvAddRecipe.visibility = View.INVISIBLE
        }
    }
}