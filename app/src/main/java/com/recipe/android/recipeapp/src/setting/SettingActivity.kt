package com.recipe.android.recipeapp.src.setting

import android.content.Intent
import android.os.Bundle
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.FCM_PUSH_OK
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.USER_IDX
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sSharedPreferences
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivitySettingBinding
import com.recipe.android.recipeapp.src.setting.deleteId.DeleteIdDialog
import com.recipe.android.recipeapp.src.setting.developer.DeveloperInfoActivity
import com.recipe.android.recipeapp.src.setting.signOut.SignOutDialog

class SettingActivity: BaseActivity<ActivitySettingBinding>(ActivitySettingBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userIdx = sSharedPreferences.getInt(USER_IDX, 0)

        binding.switchPush.isChecked = sSharedPreferences.getBoolean(FCM_PUSH_OK, false)

        binding.switchPush.setOnCheckedChangeListener { buttonView, isChecked ->
            when (isChecked){
                true -> sSharedPreferences.edit().putBoolean(FCM_PUSH_OK, true).apply()
                false -> sSharedPreferences.edit().putBoolean(FCM_PUSH_OK, false).apply()
            }
        }

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
    }
}