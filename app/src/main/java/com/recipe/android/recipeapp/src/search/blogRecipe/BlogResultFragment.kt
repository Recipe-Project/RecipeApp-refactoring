package com.recipe.android.recipeapp.src.search.blogRecipe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentBlogResultBinding
import com.recipe.android.recipeapp.src.search.blogRecipe.`interface`.BlogRecipeView
import com.recipe.android.recipeapp.src.search.blogRecipe.adapter.BlogRecipeRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeListItem
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeResponse
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeScrapResponse

class BlogResultFragment(private val keyword : String)
    : BaseFragment<FragmentBlogResultBinding>(FragmentBlogResultBinding::bind, R.layout.fragment_blog_result), BlogRecipeView {

    val TAG = "BlogResultFragment"
    private var start = 1
    private var display = 20
    private var isEnd = false
    private var blogRecipeList = mutableListOf<BlogRecipeListItem>()
    private lateinit var blogAdapter : BlogRecipeRecyclerviewAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        layoutManager = LinearLayoutManager(requireContext())
        setUpRecyclerView()
        binding.blogResultFragRecylerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = blogAdapter.itemCount

                if(!binding.blogResultFragRecylerview.canScrollVertically(1)) {
                    if(visibleItemCount + pastVisibleItem >= total) {
                        if(!isEnd) {
                            start += display
                            BlogRecipeService(this@BlogResultFragment).getBlogRecipe(keyword = keyword, display = display, start = start)
                        }
                    }
                }
            }
        })

        start = 1
        blogAdapter.blogRecipeList.clear()
        isEnd = false
        showLoadingDialog()
        BlogRecipeService(this@BlogResultFragment).getBlogRecipe(keyword = keyword, display = display, start = start)

    }

    private fun setUpRecyclerView() {
        val rv = binding.blogResultFragRecylerview
        rv.setHasFixedSize(true)
        rv.layoutManager = layoutManager
        blogAdapter = BlogRecipeRecyclerviewAdapter(requireContext())
        rv.adapter = blogAdapter
    }

    override fun onResume() {
        super.onResume()


    }

    override fun onGetBlogRecipeSuccess(response: BlogRecipeResponse) {
        dismissLoadingDialog()

        val totalCnt = response.result.total

        if(totalCnt > 100) {
            binding.blogFragItemCnt.text = "100+"
        } else {
            binding.blogFragItemCnt.text = totalCnt.toString()
        }
        //binding.blogFragItemCntUnit.visibility = View.VISIBLE

        if(response.result.blogList.isNullOrEmpty() && start == 1) {
            Log.d(TAG, "onGetBlogRecipeSuccess : 데이터 없음")
            binding.blogResultFragRecylerview.visibility = View.GONE
            binding.blogFragItemCntUnit.visibility = View.GONE
            binding.blogFragItemCnt.visibility = View.GONE
            binding.defaultTv.visibility = View.VISIBLE
            binding.defaultTv.text = "'$keyword'에 대한\n검색결과가 없습니다."
        } else if (response.result.blogList.isNotEmpty() && start == 1) {
            Log.d(TAG, "onGetBlogRecipeSuccess : 데이터 있음")
            binding.blogResultFragRecylerview.visibility = View.VISIBLE
            binding.blogFragItemCntUnit.visibility = View.VISIBLE
            binding.blogFragItemCnt.visibility = View.VISIBLE
            binding.defaultTv.visibility = View.GONE

            blogRecipeList.addAll(response.result.blogList)
            blogAdapter.submitList(blogRecipeList)
        } else if (response.result.blogList.isNotEmpty() && start != 1) {
            Log.d(TAG, "onGetBlogRecipeSuccess : 추가 데이터 있음")
            binding.blogResultFragRecylerview.visibility = View.VISIBLE
            blogRecipeList.addAll(response.result.blogList)
            blogAdapter.notifyItemInserted(blogRecipeList.size - 1)
        }

        if(response.result.blogList.isNullOrEmpty() && start != 1) {
            Log.d(TAG, "onGetBlogRecipeSuccess : 추가 데이터 없음")

            binding.blogResultFragRecylerview.visibility = View.VISIBLE
            isEnd = true
        }

        val result = response.result.blogList
        // 블로그 스크랩
        blogAdapter.blogRecipeScrapItemClick = object : BlogRecipeRecyclerviewAdapter.BlogRecipeScrapItemClick {
            override fun onClick(view: View, position: Int) {
                BlogRecipeService(this@BlogResultFragment).tryPostAddingScrap(
                    BlogRecipeScrapRequest(result[position].title, result[position].blogUrl, result[position].description, result[position].blogName,
                        result[position].postDate, result[position].thumbnail)
                )

            }
        }

        // 블로그 검색 결과 URL 연결
        blogAdapter.blogRecipeItemClick = object : BlogRecipeRecyclerviewAdapter.BlogRecipeItemClick {
            override fun onClick(view: View, position: Int) {
                startActivity(
                    Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(result[position].blogUrl))
                )
            }
        }
    }

    override fun onGetBlogRecipeFailure(message: String) {

    }

    override fun onPostBlogRecipeScrapSuccess(response: BlogRecipeScrapResponse) {

    }

    override fun onPostBlogRecipeScrapFailure(message: String) {

    }
}