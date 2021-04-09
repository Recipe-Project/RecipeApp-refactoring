package com.recipe.android.recipeapp.src.receipt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityReceiptBinding
import gun0912.tedimagepicker.builder.TedImagePicker

class ReceiptActivity : BaseActivity<ActivityReceiptBinding>(ActivityReceiptBinding::inflate) {

    val TAG = "ReceiptActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 영수증 입력을 위해 카메라 및 앫범 실행
        binding.inputReceiptBtn.setOnClickListener {
            TedImagePicker.with(this).start { uri ->  
                Log.d(TAG, uri.toString())
            }
        }

        // 뒤로 가기 버튼 클릭
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}