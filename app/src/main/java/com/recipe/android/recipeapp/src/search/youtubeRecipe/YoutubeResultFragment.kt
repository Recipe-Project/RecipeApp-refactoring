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
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.YoutubeScrapService
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.`interface`.YoutubeScrapFragmentView
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.PostYoutubeScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrap
import com.recipe.android.recipeapp.src.scrapRecipe.youtubeScrap.models.YoutubeScrapResponse
import com.recipe.android.recipeapp.src.search.blogRecipe.BlogRecipeService
import com.recipe.android.recipeapp.src.search.blogRecipe.adapter.BlogRecipeRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.blogRecipe.models.BlogRecipeListItem
import com.recipe.android.recipeapp.src.search.youtubeRecipe.`interface`.YoutubeRecipeView
import com.recipe.android.recipeapp.src.search.youtubeRecipe.adapter.YoutubeRecipeRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeResponse
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeResult
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.youtubeRecipe.models.YoutubeRecipeScrapResponse
import java.text.SimpleDateFormat

class YoutubeResultFragment(private val keyword : String) : BaseFragment<FragmentYoutubeResultBinding>(FragmentYoutubeResultBinding::bind, R.layout.fragment_youtube_result),
    YoutubeRecipeView, YoutubeScrapFragmentView {

    val TAG = "YoutubeResultFragment"
    private var display = 30
    private var isEnd = false
    private var pageToken : String = ""

    private var youtubeRecipeList = mutableListOf<YoutubeRecipeResult>()
    private var youtubeUrl = ""
    private lateinit var youtubeAdapter : YoutubeRecipeRecyclerviewAdapter
    private lateinit var layoutManager: LinearLayoutManager

    // 스크랩 조회
    private var youtubeScrapItemList = ArrayList<YoutubeScrap>()
    private var isScrapList = mutableListOf<Boolean>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 유튜브 스크랩 조회
        YoutubeScrapService(this).getYoutubeScrap(1)

        layoutManager = LinearLayoutManager(requireContext())
        setUpRecyclerView()
        initScrollListener()
    }

    override fun onResume() {
        super.onResume()

        pageToken = ""
        youtubeAdapter.youtubeRecipeList.clear()
        isEnd = false
        showLoadingDialog()
        YoutubeRecipeService(this).getYoutubeRecipe("id, snippet", "video", display, BuildConfig.GOOGLE_API_KEY, keyword, pageToken)
    }

    private fun setUpRecyclerView() {
        val rv = binding.youtubeResultFragRecylerview
        rv.setHasFixedSize(true)
        rv.layoutManager = layoutManager
        youtubeAdapter = YoutubeRecipeRecyclerviewAdapter(requireContext())
        rv.adapter = youtubeAdapter
    }

    private fun initScrollListener() {
        binding.youtubeResultFragRecylerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = youtubeAdapter.itemCount

                if (!binding.youtubeResultFragRecylerview.canScrollVertically(1)) {
                    if(visibleItemCount + pastVisibleItem >= total) {
                        if(!isEnd) {
                            YoutubeRecipeService(this@YoutubeResultFragment).getYoutubeRecipe("id, snippet", "video", display, BuildConfig.GOOGLE_API_KEY, keyword, pageToken)
                        }
                    }
                }
            }
        })
    }

    override fun onGetYoutubeRecipeSuccess(response: YoutubeRecipeResponse) {

        dismissLoadingDialog()
        if (activity != null) {
            response.items.forEach { p ->
                var scrapFlag = false
                for(i in youtubeScrapItemList) {
                    if(i.youtubeId == p.id.videoId) {
                        isScrapList.add(true)
                        scrapFlag = true
                        break
                    }
                }
                if(!scrapFlag) {
                    isScrapList.add(false)
                }
            }

            // 검색된 게시물이 100개 초과시, 100+ 로 표기
            val totalCnt = response.pageInfo.totalResults

            if(totalCnt > 100) {
                binding.youtubeFragItemCnt.text = "100+"
            } else {
                binding.youtubeFragItemCnt.text = response.pageInfo.totalResults.toString()
            }

            if(response.items.isNullOrEmpty() && pageToken == "") {
                Log.d(TAG, "onGetYoutubeRecipeSuccess : 데이터 없음")
                binding.youtubeResultFragRecylerview.visibility = View.GONE
                binding.youtubeFragItemCntUnit.visibility = View.GONE
                binding.youtubeFragItemCnt.visibility = View.GONE
                binding.defaultTv.visibility = View.VISIBLE
                binding.defaultTv.text = "'$keyword'에 대한\n검색결과가 없습니다."
            } else if (response.items.isNotEmpty() && pageToken == "") {
                pageToken = response.nextPageToken
                Log.d(TAG, "onGetYoutubeRecipeSuccess : 데이터 있음")
                binding.youtubeResultFragRecylerview.visibility = View.VISIBLE
                binding.youtubeFragItemCntUnit.visibility = View.VISIBLE
                binding.youtubeFragItemCnt.visibility = View.VISIBLE
                binding.defaultTv.visibility = View.GONE

                youtubeRecipeList.addAll(response.items)
                youtubeAdapter.submitList(youtubeRecipeList)
                youtubeAdapter.submitIsScrapList(isScrapList)
            } else if (response.items.isNotEmpty() && pageToken != "") {
                pageToken = response.nextPageToken
                Log.d(TAG, "onGetYoutubeRecipeSuccess : 추가 데이터 있음")
                binding.youtubeResultFragRecylerview.visibility = View.VISIBLE
                youtubeRecipeList.addAll(response.items)
                youtubeAdapter.notifyItemInserted(youtubeRecipeList.size - 1)

                youtubeAdapter.notifyItemInserted(isScrapList.size - 1)
            }
            Log.d(TAG, "스크랩여부리스트사이즈 : ${isScrapList.size}")


            val result = response.items
            // Youtube 영상 연결
//            youtubeAdapter.youtubeRecipeItemClick = object : YoutubeRecipeRecyclerviewAdapter.YoutubeRecipeItemClick {
//                override fun onClick(view: View, position: Int) {
//                    youtubeUrl = "https://www.youtube.com/watch?v=${result[position].id.videoId}"
//                    startActivity(
//                        Intent(Intent.ACTION_VIEW)
//                            .setData(Uri.parse(youtubeUrl))
//                            .setPackage("com.google.android.youtube")
//                    )
//                }
//            }
            // Youtube 스크랩
            youtubeAdapter.youtubeRecipeScrapItemClick = object : YoutubeRecipeRecyclerviewAdapter.YoutubeRecipeScrapItemClick {
                override fun onClick(view: View, position: Int) {
                    youtubeUrl = "https://www.youtube.com/watch?v=${youtubeAdapter.youtubeRecipeList[position].id.videoId}"
                    Log.d(TAG, "position : $position!!!!")
                    YoutubeRecipeService(this@YoutubeResultFragment).postAddingScrap(
                        YoutubeRecipeScrapRequest(
                            youtubeAdapter.youtubeRecipeList[position].id.videoId,

                            youtubeAdapter.youtubeRecipeList[position].snippet.title,
                            youtubeAdapter.youtubeRecipeList[position].snippet.thumbnails.default.url,
                            youtubeUrl,
                            formatPostDate(youtubeAdapter.youtubeRecipeList[position].snippet.publishTime),
                            youtubeAdapter.youtubeRecipeList[position].snippet.channelTitle,
                            "00:00")
                    )
                    Log.d(TAG, "${youtubeAdapter.youtubeRecipeList[position].id.videoId}, " +
                            "${youtubeAdapter.youtubeRecipeList[position].snippet.title}, " +
                            "${youtubeAdapter.youtubeRecipeList[position].snippet.thumbnails.default.url}, " +
                            "$youtubeUrl, ${formatPostDate(youtubeAdapter.youtubeRecipeList[position].snippet.publishTime)}, " + youtubeAdapter.youtubeRecipeList[position].snippet.channelTitle
                    )
                    // 토스트 메세지
                }
            }
        }
    }

    override fun onGetYoutubeRecipeFailure(message: String) {

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

    override fun onGetYoutubeScrapSuccess(response: YoutubeScrapResponse) {
        if (response.isSuccess) {
            response.result.scrapYoutubeList?.forEach {
                youtubeScrapItemList.add(it)
            }
            Log.d(TAG, "YoutubeScrapFragment - onGetYoutubeScrapSuccess() : ${response.result.scrapYoutubeCount}")
        }
    }

    override fun onGetYoutubeScrapFailure(message: String) {
        showCustomToast(getString(R.string.networkError))
        Log.d(TAG, "YoutubeScrapFragment - onGetYoutubeScrapFailure() : $message")
    }

    override fun onPostYoutubeScrapSuccess(response: PostYoutubeScrapResponse) {

    }
}