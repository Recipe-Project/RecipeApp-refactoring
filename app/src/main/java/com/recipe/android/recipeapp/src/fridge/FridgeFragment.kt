package com.recipe.android.recipeapp.src.fridge

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentFridgeBinding
import com.recipe.android.recipeapp.src.fridge.pickIngredient.PickIngredientActivity
import java.text.SimpleDateFormat
import java.util.*

class FridgeFragment : BaseFragment<FragmentFridgeBinding>(FragmentFridgeBinding::bind, R.layout.fragment_fridge) {

    // fab
    lateinit var fab_open: Animation
    lateinit var fab_close: Animation
    var isFabOpen: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCurrentDay()


        // floating action button
        fab_open = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(requireContext(), R.anim.fab_close)

        // 수정 필요 - 애니메이션
        binding.fabAdd.setOnClickListener {
            fabAnim()
        }

        binding.fabAddDirect.setOnClickListener {
            val intent = Intent(requireContext(), PickIngredientActivity::class.java)
            startActivity(intent)
        }

    }

    private fun fabAnim(){
        if (isFabOpen) {
            binding.fabAddDirect.startAnimation(fab_close)
            binding.fabAddRecipe.startAnimation(fab_close)
            binding.fabAddDirect.isClickable = false
            binding.fabAddRecipe.isClickable = false
            isFabOpen = false
        } else {
            binding.fabAddDirect.startAnimation(fab_open)
            binding.fabAddRecipe.startAnimation(fab_open)
            binding.fabAddDirect.isClickable = true
            binding.fabAddRecipe.isClickable = true
            isFabOpen = true
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun setCurrentDay() {
        val calendar = Calendar.getInstance().time

        val today = SimpleDateFormat("yy/MM/dd").format(calendar)
        binding.fridgeFragDateTv.text = today
    }
}