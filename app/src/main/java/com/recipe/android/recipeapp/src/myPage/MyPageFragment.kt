package com.recipe.android.recipeapp.src.myPage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.USER_IDX
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sSharedPreferences
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentMyPageBinding
import com.recipe.android.recipeapp.src.myPage.`interface`.MyPageFragmentView
import com.recipe.android.recipeapp.src.myPage.adapter.MyPageRecipeRecyclerViewAdapter
import com.recipe.android.recipeapp.src.myPage.models.MyRecipe
import com.recipe.android.recipeapp.src.myPage.models.UserInfoResponse
import com.recipe.android.recipeapp.src.myRecipe.MyRecipeActivity
import com.recipe.android.recipeapp.src.scrapRecipe.ScrapRecipeActivity
import com.recipe.android.recipeapp.src.setting.SettingActivity

class MyPageFragment :
    BaseFragment<FragmentMyPageBinding>(FragmentMyPageBinding::bind, R.layout.fragment_my_page),
    MyPageFragmentView {

    val TAG = "MyPageFragment"

    // 나의 레시피 5개
    var myRecipeItemList = ArrayList<MyRecipe>()
    lateinit var myPageRecipeRecyclerViewAdapter: MyPageRecipeRecyclerViewAdapter

    val userIdx = sSharedPreferences.getInt(USER_IDX, 0)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 나의 레시피 5개
        myPageRecipeRecyclerViewAdapter = MyPageRecipeRecyclerViewAdapter()
        binding.rvMyRecipe.apply {
            adapter = myPageRecipeRecyclerViewAdapter
            layoutManager = GridLayoutManager(context, 3)
        }


        // 설정 버튼 클릭
        binding.btnSetting.setOnClickListener {
            val intent = Intent(context, SettingActivity::class.java)
            startActivity(intent)
        }

        // 스크랩 레시피 전체보기 버튼 클릭
        binding.btnAllScrapRecipe.setOnClickListener {
            val intent = Intent(context, ScrapRecipeActivity::class.java)
            startActivity(intent)
        }

        // 나만의 레시피 전체보기 버튼 클릭
        binding.btnAllMyRecipe.setOnClickListener {
            val intent = Intent(context, MyRecipeActivity::class.java)
            startActivity(intent)
        }

        // 프로필 수정 버튼 클릭
        binding.btnEditProfile.setOnClickListener {

        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MyPageFragment - onStart() : ")
        // 마이페이지 조회 api
        MyPageService(this).getUserInfo(userIdx)

        myPageRecipeRecyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "MyPageFragment - onResume() : ")

    }

    override fun onGetUserInfoSuccess(response: UserInfoResponse) {
        if (response.isSuccess) {
            val userInfoResult = response.result
            if (userInfoResult.profilePhoto != "") {
                Glide.with(requireContext()).load(userInfoResult.profilePhoto).into(binding.imgProfile)
            }
            binding.tvUserName.text = userInfoResult.userName
            binding.tvCntYoutube.text = userInfoResult.youtubeScrapCnt.toString()
            binding.tvCntBlog.text = userInfoResult.blogScrapCnt.toString()
            binding.tvCntRecipe.text = userInfoResult.recipeScrapCnt.toString()

            // 나의 레시피 5개
            myRecipeItemList.clear()
            userInfoResult.myRecipeList.forEach {
                myRecipeItemList.add(it)
            }
            myPageRecipeRecyclerViewAdapter.submitList(myRecipeItemList, userInfoResult.myRecipeTotalSize)




        }
    }

    override fun onGetUserInfoFailure(message: String) {
        showCustomToast(getString(R.string.networkError))
        Log.d(TAG, "MyPageFragment - onGetUserInfoFailure() : $message")
    }
}