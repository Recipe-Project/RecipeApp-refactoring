package com.recipe.android.recipeapp.src.setting.privacy

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityPrivacyBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class PrivacyActivity : BaseActivity<ActivityPrivacyBinding>(ActivityPrivacyBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.tvContent.text = getString(R.string.privacyPolicy)

    }
}