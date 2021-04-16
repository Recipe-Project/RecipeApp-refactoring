package com.recipe.android.recipeapp.src.fridge.dialog

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.DialogDatePickerBinding
import java.util.*

class DateDialog: BaseActivity<DialogDatePickerBinding>(DialogDatePickerBinding::inflate) {

    val TAG = "DateDialog"

    val SET_DATE = 4500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val calendar: Calendar = Calendar.getInstance()
        binding.datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            Log.d(TAG, "DateDialog - onCreate() : $year, $monthOfYear, $dayOfMonth")

            binding.btnSave.setOnClickListener {
                val intent = Intent()
                val bundle = Bundle()
                bundle.putInt("year", year)
                bundle.putInt("monthOfYear", monthOfYear+1)
                bundle.putInt("dayOfMonth", dayOfMonth)
                intent.putExtras(bundle)
                setResult(SET_DATE, intent)
                finish()
            }

        }
    }
}