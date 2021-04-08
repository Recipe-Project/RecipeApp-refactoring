package com.recipe.android.recipeapp.src.search.youtubeRecipe

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
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

    private var display = 10
    private var youtubeUrl = ""
    private lateinit var adapter : YoutubeRecipeRecyclerviewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = YoutubeRecipeRecyclerviewAdapter()
        binding.youtubeResultFragRecylerview.adapter = adapter

        // 최초로 데이터 load
        YoutubeRecipeService(this).getYoutubeRecipe("id, snippet", "video", display, "AIzaSyBFZkXxJXrTs-eGruMKCGsA8cn7iJDkwuI", keyword)
    }

    override fun onGetYoutubeRecipeSuccess(response: YoutubeRecipeResponse) {
        binding.youtubeFragItemCnt.text = response.pageInfo.totalResults.toString()
        binding.youtubeFragItemCntUnit.visibility = View.VISIBLE

        val result = response.items
        adapter.setYoutubeRecipe(result)

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
            }
        }
    }

    override fun onGetYoutubeRecipeFailure(message: String) {

    }

    override fun onGetYoutubeRecipeMoreSuccess(response: YoutubeRecipeResponse) {

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