package com.recipe.android.recipeapp.src.search.youtubeRecipe

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.BuildConfig
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentYoutubeResultBinding
import com.recipe.android.recipeapp.src.search.youtubeRecipe.`interface`.YoutubeRecipeView
import com.recipe.android.recipeapp.src.search.youtubeRecipe.adapter.YoutubeRecipeRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeResponse
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeScrapResponse
import java.text.SimpleDateFormat

class YoutubeResultFragment(private val keyword : String) : BaseFragment<FragmentYoutubeResultBinding>(FragmentYoutubeResultBinding::bind, R.layout.fragment_youtube_result), YoutubeRecipeView {

    val TAG = "YoutubeResultFragment"

    private var display = 50
    private var youtubeUrl = ""
    private lateinit var adapter : YoutubeRecipeRecyclerviewAdapter
    private var pageToken : String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = YoutubeRecipeRecyclerviewAdapter()
        binding.youtubeResultFragRecylerview.adapter = adapter

        // 최초로 데이터 load
        YoutubeRecipeService(this).getYoutubeRecipe("id, snippet", "video", display, BuildConfig.GOOGLE_API_KEY, keyword, pageToken)
        initScrollListener()
    }

    private fun initScrollListener() {
        binding.youtubeResultFragRecylerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount-1
                if (!binding.youtubeResultFragRecylerview.canScrollVertically(1)) {
                    if(itemTotalCount == lastVisibleItem) {
                        //무한스크롤 수정 필요
                        //adapter.setLoadingView(true)
                        //YoutubeRecipeService(this@YoutubeResultFragment).getYoutubeRecipe("id, snippet", "video", display, BuildConfig.GOOGLE_API_KEY, keyword, pageToken)
                    }
                }
            }
        })
    }

    override fun onGetYoutubeRecipeSuccess(response: YoutubeRecipeResponse) {
        // 검색된 게시물이 100개 초과시, 100+ 로 표기
        val totalCnt = response.pageInfo.totalResults
        if(totalCnt > 100) {
            binding.youtubeFragItemCnt.text = "100+"
        } else {
            binding.youtubeFragItemCnt.text = response.pageInfo.totalResults.toString()
        }
        binding.youtubeFragItemCntUnit.visibility = View.VISIBLE

        val result = response.items
        adapter.setYoutubeRecipe(result)
        // nextToken
        pageToken = response.nextPageToken

        // Youtube 영상 연결
        adapter.youtubeRecipeItemClick = object : YoutubeRecipeRecyclerviewAdapter.YoutubeRecipeItemClick {
            override fun onClick(view: View, position: Int) {
                youtubeUrl = "https://www.youtube.com/watch?v=${result[position].id.videoId}"
                startActivity(
                    Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(youtubeUrl))
                        .setPackage("com.google.android.youtube")
                )
            }
        }
        // Youtube 스크랩
        adapter.youtubeRecipeScrapItemClick = object : YoutubeRecipeRecyclerviewAdapter.YoutubeRecipeScrapItemClick {
            override fun onClick(view: View, position: Int) {
                YoutubeRecipeService(this@YoutubeResultFragment).postAddingScrap(
                    YoutubeRecipeScrapRequest(result[position].id.videoId, result[position].snippet.title,
                        result[position].snippet.thumbnails.default.url, youtubeUrl,
                    formatPostDate(result[position].snippet.publishTime),
                        result[position].snippet.channelTitle, "00:00")
                )
                // 토스트 메세지

            }
        }
    }

    override fun onGetYoutubeRecipeFailure(message: String) {

    }

    override fun onGetYoutubeRecipeMoreSuccess(response: YoutubeRecipeResponse) {
        val result = response.items
        adapter.setLoadingView(false)
        adapter.addYoutubeRecipe(result)
//        // nextToken
//        pageToken = response.nextPageToken
    }

    override fun onGetYoutubeRecipeMoreFailure(message: String) {

    }

    override fun onPostYoutubeRecipeScrapSuccess(response: YoutubeRecipeScrapResponse) {

    }

    override fun onPostYoutubeRecipeScrapFailure(message: String) {

    }

    @SuppressLint("SimpleDateFormat")
    fun formatPostDate(date: String): String {
        val dateBeforeFormat = SimpleDateFormat("yyyy-MM-dd")
        val formatDate = dateBeforeFormat.parse(date)
        if (formatDate != null) {
            val newDateFormat = SimpleDateFormat("yyyy.MM.dd")
            return newDateFormat.format(formatDate)
        }
        return ""
    }
}