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
import com.recipe.android.recipeapp.src.search.SearchFragment

interface KeywordListener {
    fun setKeyword(keyword: String)
}

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    KeywordListener {

    val TAG = "MainActivity"

    val PICKER_REQUEST_CODE = 5300

    lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 네비게이션 호스트
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment

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

    override fun setKeyword(keyword: String) {
        val frag = navHostFragment.childFragmentManager.fragments.get(0) as SearchFragment
        frag.setKeyword(keyword)
    }

    var time: Long = 0
    override fun onBackPressed() {
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis()
            showCustomToast("뒤로 가기 버튼을 한번 더 누르면 종료합니다.")
        } else if (System.currentTimeMillis() - time < 2000) {
            finish()
        }
    }

}