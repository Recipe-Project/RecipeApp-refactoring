package com.recipe.android.recipeapp.src.fridge

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentFridgeBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class FridgeFragment : BaseFragment<FragmentFridgeBinding>(FragmentFridgeBinding::bind, R.layout.fragment_fridge) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCurrentDay()

    }


    @SuppressLint("SimpleDateFormat")
    private fun setCurrentDay() {
        val calendar = Calendar.getInstance().time

        val today = SimpleDateFormat("yy/MM/dd").format(calendar)
        binding.fridgeFragDateTv.text = today
    }
}