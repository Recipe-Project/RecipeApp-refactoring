package com.recipe.android.recipeapp.src.myRecipe

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityMyRecipeBinding
import com.recipe.android.recipeapp.src.myPage.adapter.MyRecipeGridViewAdapter
import com.recipe.android.recipeapp.src.myRecipe.adpater.MyRecipeRecyclerViewAdapter
import com.recipe.android.recipeapp.src.myRecipe.models.MyRecipeResponse
import com.recipe.android.recipeapp.src.myRecipe.models.MyRecipeResult
import com.recipe.android.recipeapp.src.myRecipe.myRecipeCreate.MyRecipeCreateActivity

interface MyRecipeActivityView {
    fun onGetMyRecipeSuccess(response: MyRecipeResponse)
    fun onGetMyRecipeFailure(message: String)
}

class MyRecipeActivity: BaseActivity<ActivityMyRecipeBinding>(ActivityMyRecipeBinding::inflate), MyRecipeActivityView {

    val myRecipeRecyclerViewAdapter = MyRecipeRecyclerViewAdapter()
    var myRecipeList = ArrayList<MyRecipeResult>()
    lateinit var myRecipeGridViewAdapter: MyRecipeGridViewAdapter

    var isGridView = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        myRecipeGridViewAdapter = MyRecipeGridViewAdapter()

        // 리사이클러뷰 바인딩
        binding.rvMyRecipe.apply {
            adapter = myRecipeRecyclerViewAdapter
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }

        binding.btnRecipeCreate.setOnClickListener { view ->
            val intent = Intent(this, MyRecipeCreateActivity::class.java)
            startActivity(intent)
        }

        // 뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            finish()
        }

        // 그리드뷰
        binding.btnView.setOnClickListener {
            isGridView = !isGridView

            if (isGridView) {
                binding.btnView.setImageResource(R.drawable.ic_middle_listview)
                binding.rvMyRecipe.apply {
                    adapter = myRecipeGridViewAdapter
                    layoutManager = GridLayoutManager(this@MyRecipeActivity, 3)
                }
                myRecipeGridViewAdapter.submitList(myRecipeList)
            } else {
                binding.btnView.setImageResource(R.drawable.ic_grid_view)
                binding.rvMyRecipe.apply {
                    adapter = myRecipeRecyclerViewAdapter
                    layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                }
                myRecipeRecyclerViewAdapter.submitList(myRecipeList)
            }

        }

    }

    override fun onStart() {
        super.onStart()

        // 나만의 레시피 조회 api 호출
        MyRecipeService(this).getMyRecipe(2,0)

    }

    override fun onGetMyRecipeSuccess(response: MyRecipeResponse) {

        myRecipeList.clear()

        response.result.forEach {
            myRecipeList.add(it)
        }

        myRecipeRecyclerViewAdapter.submitList(myRecipeList)
    }

    override fun onGetMyRecipeFailure(message: String) {
        TODO("Not yet implemented")
    }
}