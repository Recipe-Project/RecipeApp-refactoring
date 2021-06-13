package com.recipe.android.recipeapp.src.emptyFridge

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentEmptyFridgeBinding
import com.recipe.android.recipeapp.src.MainActivity
import com.recipe.android.recipeapp.src.emptyFridge.`interface`.EmptyFridgeView
import com.recipe.android.recipeapp.src.emptyFridge.adapter.EmptyFridgeRecyclerviewAdapter
import com.recipe.android.recipeapp.src.emptyFridge.models.EmptyFridgeResponse
import com.recipe.android.recipeapp.src.emptyFridge.models.EmptyFridgeResult
import com.recipe.android.recipeapp.src.search.SearchFragment
import com.recipe.android.recipeapp.src.search.SearchResultFragment
import com.recipe.android.recipeapp.src.search.publicRecipe.recipeDetail.RecipeDetailActivity

class EmptyFridgeFragment : BaseFragment<FragmentEmptyFridgeBinding>(FragmentEmptyFridgeBinding::bind, R.layout.fragment_empty_fridge), EmptyFridgeView {

    val TAG = "EmptyFridgeFragment"
    private var start = 0
    private var display = 10
    // private var totalCount = 0
    private var isEnd = false
    private var emptyFridgeList = mutableListOf<EmptyFridgeResult>()
    private lateinit var emptyAdapter : EmptyFridgeRecyclerviewAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = binding.emptyFridgeFragRecyclerview

        layoutManager = LinearLayoutManager(requireContext())
        setUpRecyclerView()
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = emptyAdapter.itemCount

                if(!rv.canScrollVertically(1)) {
                    if(visibleItemCount + pastVisibleItem >= total) {
                        if(!isEnd) {
                            start += display
                            EmptyFridgeService(this@EmptyFridgeFragment).tryGetEmptyFridge(start, display)
                        }
                    }
                }
            }
        })
    }

    override fun onGetEmptyFridgeSuccess(response: EmptyFridgeResponse) {

        if (activity != null) {
            val newEmptyFridgeList = response.result.recipeList
            if(response.result.recipeList.isNullOrEmpty() && start == 0) {
                Log.d(TAG, "onGetEmptyFridgeSuccess : 데이터 없음")
                if (activity != null) {
                    binding.emptyFridgeFragRecyclerview.visibility = View.INVISIBLE
                    binding.emptyFridgeFragDefaultIv.visibility = View.VISIBLE
                    binding.emptyFridgeFragDefaultTv.visibility = View.VISIBLE
                }
            } else if (response.result.recipeList.isNotEmpty() && start == 0) {
                Log.d(TAG, "onGetEmptyFridgeSuccess : 데이터 있음")
                if (activity != null) {
                    binding.emptyFridgeFragRecyclerview.visibility = View.VISIBLE
                    binding.emptyFridgeFragDefaultIv.visibility = View.INVISIBLE
                    binding.emptyFridgeFragDefaultTv.visibility = View.INVISIBLE
                    emptyFridgeList.clear()
                    emptyFridgeList.addAll(newEmptyFridgeList)
                    emptyAdapter.submitList(emptyFridgeList)
                }

            } else if (response.result.recipeList.isNotEmpty() && start != 0) {
                Log.d(TAG, "onGetEmptyFridgeSuccess : 추가 데이터 있음")

                if (activity != null) {
                    binding.emptyFridgeFragRecyclerview.visibility = View.VISIBLE
                    emptyFridgeList.clear()
                    emptyFridgeList.addAll(newEmptyFridgeList)
                    emptyAdapter.notifyItemInserted(emptyFridgeList.size - 1)
                }
            }

            if(response.result.recipeList.isNullOrEmpty() && start != 0) {
                Log.d(TAG, "onGetEmptyFridgeSuccess : 추가 데이터 없음")

                isEnd = true
            }
        }

        dismissLoadingDialog()
    }

    override fun onGetEmptyFridgeFailure(message: String) {

    }

    private fun setUpRecyclerView() {
        if (activity != null) {
            val rv = binding.emptyFridgeFragRecyclerview
            rv.setHasFixedSize(true)
            rv.layoutManager = layoutManager
            emptyAdapter = EmptyFridgeRecyclerviewAdapter(this)
            rv.adapter = emptyAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        if (activity != null) {
            start = 0
            isEnd = false
            showLoadingDialog()
            EmptyFridgeService(this).tryGetEmptyFridge(start, display)
        }
    }



    override fun getPublicRecipeDetail(id : Int) {
        val intent = Intent(requireContext(), RecipeDetailActivity::class.java)
        intent.putExtra("index", id)
        requireActivity().startActivity(intent)
    }

    override fun getBlogRecipe(keyword : String) {
        // 네비게이션 호스트
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController
        val bundle = bundleOf("searchType" to "blog", "searchKeyword" to keyword)
        navController.navigate(R.id.searchFragment, bundle)
//        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.search_frag_frame_layout, SearchResultFragment(keyword)).commitAllowingStateLoss()
    }

    override fun getYoutubeRecipe(keyword : String) {
        // 네비게이션 호스트
        val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController
        val bundle = bundleOf("searchType" to "youtube", "searchKeyword" to keyword)
        navController.navigate(R.id.searchFragment, bundle)
//        requireActivity().supportFragmentManager.beginTransaction().replace(R.id.search_frag_frame_layout, SearchResultFragment(keyword)).commitAllowingStateLoss()
    }
}




