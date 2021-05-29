package com.recipe.android.recipeapp.src.setting.openSource

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityOpenSourceBinding

class OpenSourceActivity :
    BaseActivity<ActivityOpenSourceBinding>(ActivityOpenSourceBinding::inflate) {

    val openSourceList = ArrayList<OpenSource>()
    lateinit var openSourceRecyclerViewAdapter: OpenSourceRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        openSourceRecyclerViewAdapter = OpenSourceRecyclerViewAdapter()
        binding.rvOpenSource.apply {
            adapter = openSourceRecyclerViewAdapter
            layoutManager =
                LinearLayoutManager(this@OpenSourceActivity, RecyclerView.VERTICAL, false)
        }

        openSourceList.add(
            OpenSource(
                getString(R.string.openSrcTitle1),
                getString(R.string.openSrcLink1),
                getString(R.string.openSrcCopy1),
                getString(R.string.apache)
            )
        )



        openSourceRecyclerViewAdapter.submitList(openSourceList)

    }
}

data class OpenSource(
    val title: String,
    val link: String,
    val copy: String,
    val license: String
)