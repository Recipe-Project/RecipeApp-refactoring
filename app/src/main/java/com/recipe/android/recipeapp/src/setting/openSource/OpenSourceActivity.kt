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
                getString(R.string.openSrcCopy1)
            )
        )
        openSourceList.add(
            OpenSource(
                getString(R.string.openSrcTitle2),
                getString(R.string.openSrcLink2),
                getString(R.string.openSrcCopy2)
            )
        )
        openSourceList.add(
            OpenSource(
                getString(R.string.openSrcTitle3),
                getString(R.string.openSrcLink3),
                getString(R.string.openSrcCopy3)
            )
        )
        openSourceList.add(
            OpenSource(
                getString(R.string.openSrcTitle4),
                getString(R.string.openSrcLink4),
                getString(R.string.openSrcCopy4)
            )
        )
        openSourceList.add(
            OpenSource(
                getString(R.string.openSrcTitle5),
                getString(R.string.openSrcLink5),
                getString(R.string.openSrcCopy5)
            )
        )
        openSourceList.add(
            OpenSource(
                getString(R.string.openSrcTitle6),
                getString(R.string.openSrcLink6),
                getString(R.string.openSrcCopy6)
            )
        )
        openSourceList.add(
            OpenSource(
                getString(R.string.openSrcTitle7),
                getString(R.string.openSrcLink7),
                getString(R.string.openSrcCopy7)
            )
        )
        openSourceList.add(
            OpenSource(
                getString(R.string.openSrcTitle8),
                getString(R.string.openSrcLink8),
                getString(R.string.openSrcCopy8)
            )
        )
        openSourceList.add(
            OpenSource(
                getString(R.string.openSrcTitle9),
                getString(R.string.openSrcLink9),
                getString(R.string.openSrcCopy9)
            + getString(R.string.openSrcCopy92)
            )
        )
        openSourceList.add(
            OpenSource(
                getString(R.string.openSrcTitle10),
                getString(R.string.openSrcLink10),
                getString(R.string.openSrcCopy10)
            )
        )
        openSourceList.add(
            OpenSource(
                getString(R.string.openSrcTitle11),
                getString(R.string.openSrcLink11),
                getString(R.string.openSrcCopy11)
            )
        )

        openSourceRecyclerViewAdapter.submitList(openSourceList)

    }
}

data class OpenSource(
    val title: String,
    val link: String,
    val copy: String
)