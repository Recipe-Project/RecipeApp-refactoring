package com.recipe.android.recipeapp.config

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.viewbinding.ViewBinding
import com.recipe.android.recipeapp.common.LoadingDialog

abstract class BaseActivity<B : ViewBinding>(private val inflate: (LayoutInflater) -> B) :
    AppCompatActivity() {
    protected lateinit var binding: B
        private set

    lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = inflate(layoutInflater)
        setContentView(binding.root)

        loadingDialog = LoadingDialog(this)
    }

    fun showCustomToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoadingDialog(){
        loadingDialog.show()
    }

    fun dismissLoadingDialog(){
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

    fun showKeyboard(editText: AppCompatEditText){
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, 0)
    }


}