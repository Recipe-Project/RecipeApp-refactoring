package com.recipe.android.recipeapp.src.setting.deleteId

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityDeleteIdSuccessBinding
import com.recipe.android.recipeapp.src.signIn.SignInActivity
import kotlin.system.exitProcess

class DeleteIdSuccess: BaseActivity<ActivityDeleteIdSuccessBinding>(ActivityDeleteIdSuccessBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnMoveSignIn.setOnClickListener {
            val intent = Intent (this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.btnOff.setOnClickListener {
            ActivityCompat.finishAffinity(this)
            exitProcess(0)
        }
    }
}