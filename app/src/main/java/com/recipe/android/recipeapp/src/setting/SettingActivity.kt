package com.recipe.android.recipeapp.src.setting

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.FCM_PUSH_OK
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.USER_IDX
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sSharedPreferences
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivitySettingBinding
import com.recipe.android.recipeapp.src.setting.deleteId.DeleteIdDialog
import com.recipe.android.recipeapp.src.setting.developer.DeveloperInfoActivity
import com.recipe.android.recipeapp.src.setting.openSource.OpenSourceActivity
import com.recipe.android.recipeapp.src.setting.signOut.SignOutDialog

class SettingActivity: BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userIdx = sSharedPreferences.getInt(USER_IDX, 0)

        // 탈퇴 버튼 클릭
        binding.btnDeleteId.setOnClickListener {
            // 탈퇴 다이얼로그
            val intent = Intent(this, DeleteIdDialog::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
        }

        binding.btnSignOut.setOnClickListener {
            val intent = Intent(this, SignOutDialog::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
        }

        // 뒤로가기 화살표 버튼
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnDeveloper.setOnClickListener {
            val intent = Intent(this, DeveloperInfoActivity::class.java)
            startActivity(intent)
        }

        binding.btnPush.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }

        binding.btnOpenSource.setOnClickListener {
            val intent = Intent(this, OpenSourceActivity::class.java)
            startActivity(intent)
        }
    }
}