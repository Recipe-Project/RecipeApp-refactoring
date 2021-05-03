package com.recipe.android.recipeapp.src.fridge.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.recipe.android.recipeapp.databinding.DialogDatePickerBinding
import com.recipe.android.recipeapp.src.fridge.basket.`interface`.DateDialogInterface
import java.util.*

class DateDialog(context: Context, val dateSave: DateDialogInterface, val position: Int): Dialog(context) {

    val TAG = "DateDialog"
    private lateinit var binding: DialogDatePickerBinding

//    val SET_DATE = 4500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = DialogDatePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val params = window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        window!!.attributes = params

//        val calendar: Calendar = Calendar.getInstance()
//        binding.datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
//        ) { view, year, monthOfYear, dayOfMonth ->
//            Log.d(TAG, "DateDialog - onCreate() : $year, $monthOfYear, $dayOfMonth")
//
//            binding.btnSave.setOnClickListener {
//
//                dateSave.clickDate(year, monthOfYear+1, dayOfMonth, position)
//                dismiss()
//
////                val intent = Intent()
////                val bundle = Bundle()
////                bundle.putInt("year", year)
////                bundle.putInt("monthOfYear", monthOfYear+1)
////                bundle.putInt("dayOfMonth", dayOfMonth)
////                intent.putExtras(bundle)
////                setResult(SET_DATE, intent)
////                finish()
//            }
//
//        }

        binding.datePicker.setOnDateChangeListener { view, year, month, dayOfMonth ->
            Log.d(TAG, "DateDialog - onCreate() : $year, $month, $dayOfMonth")
            binding.btnSave.setOnClickListener {
                dateSave.clickDate(year, month+1, dayOfMonth, position)
                dismiss()
            }
        }
    }
}