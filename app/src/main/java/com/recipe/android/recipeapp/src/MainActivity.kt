package com.recipe.android.recipeapp.src

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.opensooq.supernova.gligar.GligarPicker
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityMainBinding
import com.recipe.android.recipeapp.src.fridge.receipt.ReceiptIngredientDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    val TAG = "MainActivity"

    val PICKER_REQUEST_CODE = 5300

    lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PICKER_REQUEST_CODE -> {
                Log.d(TAG, "FridgeFragment - onActivityResult() : success")

                val imagesList = data?.extras?.getStringArray(GligarPicker.IMAGES_RESULT)
                if (imagesList != null) {
                    showLoadingDialog()
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
}