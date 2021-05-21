package com.recipe.android.recipeapp.src

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.opensooq.supernova.gligar.GligarPicker
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityMainBinding
import com.recipe.android.recipeapp.src.fridge.receipt.ReceiptIngredientDialog

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    val TAG = "MainActivity"

    val PICKER_REQUEST_CODE = 5300

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 네비게이션 호스트
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController

        // 바인딩
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICKER_REQUEST_CODE -> {
                Log.d(TAG, "FridgeFragment - onActivityResult() : success")
                showLoadingDialog()
                val imagesList = data?.extras?.getStringArray(GligarPicker.IMAGES_RESULT)
                val pickImage = imagesList?.get(0)
                val uri = Uri.parse("$pickImage")
                Log.d(TAG, "FridgeFragment - onActivityResult() : $uri")

                val intent = Intent(this, ReceiptIngredientDialog::class.java)
                intent.putExtra("uri", uri.toString())

                startActivity(intent)
            }
        }
    }
}