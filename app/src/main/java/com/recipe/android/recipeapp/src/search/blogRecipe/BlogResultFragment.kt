package com.recipe.android.recipeapp.src.search.blogRecipe

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentBlogResultBinding
import com.recipe.android.recipeapp.src.search.blogRecipe.`interface`.BlogRecipeView
import com.recipe.android.recipeapp.src.search.blogRecipe.adapter.BlogRecipeRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeResponse
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeScrapResponse

class BlogResultFragment(private val keyword : String)
    : BaseFragment<FragmentBlogResultBinding>(FragmentBlogResultBinding::bind, R.layout.fragment_blog_result), BlogRecipeView {

    private var start = 1
    private var display = 30
    private lateinit var adapter : BlogRecipeRecyclerviewAdapter
    private var flag = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = BlogRecipeRecyclerviewAdapter(requireContext())
        binding.blogResultFragRecylerview.adapter = adapter

        // 최초로 데이터 load
        showLoadingDialog()
        BlogRecipeService(this).getBlogRecipe(keyword = keyword, display = display, start = start)
        initScrollListener()
    }

    private fun initScrollListener() {
        binding.blogResultFragRecylerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-2

                val layoutManager = binding.blogResultFragRecylerview.layoutManager
                if (!binding.blogResultFragRecylerview.canScrollVertically(1)) {
                    adapter.setLoadingView(false)

                    if(lastVisibleItemPosition == itemTotalCount) {
                        loadMoreData()
                    }
                }




            }
        })
    }

    private fun loadMoreData() {
        adapter.setLoadingView(true)
        BlogRecipeService(this@BlogResultFragment).getBlogRecipeMore(keyword = keyword, display = display, start = start)
    }

    override fun onGetBlogRecipeSuccess(response: BlogRecipeResponse) {
        dismissLoadingDialog()

        if (response.isSuccess) {
            // 검색된 게시물이 100개 초과시, 100+ 로 표기
            val totalCnt = response.result.total

            if(totalCnt == 0) {
                binding.blogResultFragRecylerview.visibility = View.GONE
                binding.blogFragItemCntUnit.visibility = View.GONE
                binding.blogFragItemCnt.visibility = View.GONE
                binding.defaultTv.visibility = View.VISIBLE
                binding.defaultTv.text = "'$keyword'에 대한\n검색결과가 없습니다."
            } else {
                binding.blogResultFragRecylerview.visibility = View.VISIBLE
                binding.blogFragItemCntUnit.visibility = View.VISIBLE
                binding.blogFragItemCnt.visibility = View.VISIBLE
                binding.defaultTv.visibility = View.GONE

                if(totalCnt > 100) {
                    binding.blogFragItemCnt.text = "100+"
                } else {
                    binding.blogFragItemCnt.text = totalCnt.toString()
                }
                binding.blogFragItemCntUnit.visibility = View.VISIBLE

                val result = response.result.blogList
                adapter.setBlogRecipe(result)
                adapter.setLoadingView(false)

                // 블로그 스크랩
                adapter.blogRecipeScrapItemClick = object : BlogRecipeRecyclerviewAdapter.BlogRecipeScrapItemClick {
                    override fun onClick(view: View, position: Int) {
                        BlogRecipeService(this@BlogResultFragment).tryPostAddingScrap(
                            BlogRecipeScrapRequest(result[position].title, result[position].blogUrl, result[position].description, result[position].blogName,
                                result[position].postDate, result[position].thumbnail)
                        )

                    }
                }

                // 블로그 검색 결과 URL 연결
                adapter.blogRecipeItemClick = object : BlogRecipeRecyclerviewAdapter.BlogRecipeItemClick {
                    override fun onClick(view: View, position: Int) {
                        startActivity(
                            Intent(Intent.ACTION_VIEW)
                                .setData(Uri.parse(result[position].blogUrl))
                        )
                    }
                }
                start += display // 불러온 데이터 수만큼 페이지 전환
                flag = true
            }
        } else {
            // 통신에러
        }
    }

    override fun onGetBlogRecipeFailure(message: String) {

    }

    override fun onGetBlogRecipeMoreSuccess(response: BlogRecipeResponse) {
        // 너무 빨리 데이터가 로드되면 스크롤 되는 Ui 를 확인하기 어려우므로,
        // Handler 를 사용하여 1초간 postDelayed 시켜야할까...?

        dismissLoadingDialog()
        if (response.isSuccess) {
            // isNext = response.is_Next // 있어야할 것 같습니다.
            val result = response.result.blogList
            adapter.run {
                setLoadingView(false)
                addBlogRecipe(result)
                blogRecipeScrapItemClick = object : BlogRecipeRecyclerviewAdapter.BlogRecipeScrapItemClick {
                    override fun onClick(view: View, position: Int) {
                        BlogRecipeService(this@BlogResultFragment).tryPostAddingScrap(
                            BlogRecipeScrapRequest(result[position].title, result[position].blogUrl, result[position].description, result[position].blogName,
                                result[position].postDate, result[position].thumbnail)
                        )

                    }
                }
                flag = true
            }
            start += display // 불러온 데이터 수만큼 페이지 전환
        } else {
            // 통신에러
        }


    }

    override fun onGetBlogRecipeMoreFailure(message: String) {

    }

    override fun onPostBlogRecipeScrapSuccess(response: BlogRecipeScrapResponse) {

    }

    override fun onPostBlogRecipeScrapFailure(message: String) {

    }
}