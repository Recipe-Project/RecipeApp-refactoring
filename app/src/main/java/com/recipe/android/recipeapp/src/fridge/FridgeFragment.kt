package com.recipe.android.recipeapp.src.fridge

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.functions.FirebaseFunctions
import com.opensooq.supernova.gligar.GligarPicker
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentFridgeBinding
import com.recipe.android.recipeapp.src.MainActivity
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeView
import com.recipe.android.recipeapp.src.fridge.home.`interface`.IngredientUpdateView
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeCategoryAdapter
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResponse
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult
import com.recipe.android.recipeapp.src.fridge.home.models.PatchFridgeObject
import com.recipe.android.recipeapp.src.fridge.home.models.PatchFridgeResponse
import com.recipe.android.recipeapp.src.fridge.pickIngredient.PickIngredientActivity
import com.recipe.android.recipeapp.src.fridge.receipt.ReceiptIngredientDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class FridgeFragment :
    BaseFragment<FragmentFridgeBinding>(FragmentFridgeBinding::bind, R.layout.fragment_fridge),
    FridgeView, IngredientUpdateView {

    val TAG = "FridgeFragment"

    // fab
    var isClicked: Boolean = false
    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open)
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close)
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim)
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim)
    }
    lateinit var bitmap : Bitmap
    private lateinit var functions: FirebaseFunctions
    var ingredients = ArrayList<GetFridgeResult>()
    var fridgeUpdateList = ArrayList<PatchFridgeObject>()

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    lateinit var myFridgeCategoryAdapter : MyFridgeCategoryAdapter

    companion object {
        var updateButtonFlag = false
    }

    val PICKER_REQUEST_CODE = 5300

    lateinit var uri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 현재 날짜 세팅
        setCurrentDay()

        // 냉장고 조회
        showLoadingDialog()
        FridgeService(this).tryGetFridge()


        // + 버튼 클릭
        binding.fabAdd.setOnClickListener {
            setVisibility(isClicked)
            setFabAnim(isClicked)
            isClicked = !isClicked

            binding.bgFloating.setOnClickListener {
                setVisibility(isClicked)
                setFabAnim(isClicked)
                isClicked = !isClicked
            }
        }

        // 직접입력 버튼 클릭
        binding.fabAddDirect.setOnClickListener {
            val intent = Intent(requireContext(), PickIngredientActivity::class.java)
            startActivity(intent)
        }

        // 영수증 입력 버튼 클릭
        binding.fabAddRecipe.setOnClickListener {
            GligarPicker().limit(1).requestCode(PICKER_REQUEST_CODE).withActivity(requireActivity())
                .show()
        }

        binding.updateTv.setOnClickListener {
            // 냉장고 수정
            updateButtonFlag = true
            showLoadingDialog()
            FridgeService(this).tryGetFridge()


            // 레이아웃 변경
            binding.saveTv.visibility = View.VISIBLE
            binding.cancelTv.visibility = View.VISIBLE
            binding.updateTv.visibility = View.INVISIBLE
            binding.fridgeFragDateTv.visibility = View.INVISIBLE

            binding.productNameTv.visibility = View.GONE
            binding.productFreshnessTv.visibility = View.GONE
            binding.allCheckTv.visibility = View.VISIBLE
            binding.allCheckCheckbox.visibility = View.VISIBLE
        }

        binding.cancelTv.setOnClickListener {
            // 냉장고 수정 화면에서 취소 버튼
            updateButtonFlag = false
            showLoadingDialog()
            FridgeService(this).tryGetFridge()

            // 레이아웃 변경
            binding.saveTv.visibility = View.INVISIBLE
            binding.cancelTv.visibility = View.INVISIBLE
            binding.updateTv.visibility = View.VISIBLE
            binding.fridgeFragDateTv.visibility = View.VISIBLE

            binding.productNameTv.visibility = View.VISIBLE
            binding.productFreshnessTv.visibility = View.VISIBLE
            binding.allCheckTv.visibility = View.GONE
            binding.allCheckCheckbox.visibility = View.GONE
        }

        binding.saveTv.setOnClickListener {
            // 냉장고 수정 저장하기(냉장고 수정 API 호출)


            // 레이아웃 변경
            binding.saveTv.visibility = View.INVISIBLE
            binding.cancelTv.visibility = View.INVISIBLE
            binding.updateTv.visibility = View.VISIBLE
            binding.fridgeFragDateTv.visibility = View.VISIBLE

            binding.productNameTv.visibility = View.VISIBLE
            binding.productFreshnessTv.visibility = View.VISIBLE
            binding.allCheckTv.visibility = View.GONE
            binding.allCheckCheckbox.visibility = View.GONE
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun setCurrentDay() {
        val calendar = Calendar.getInstance().time

        val today = SimpleDateFormat("yy/MM/dd").format(calendar)
        binding.fridgeFragDateTv.text = today
    }

    private fun setFabAnim(isClicked: Boolean) {
        if (!isClicked) {
            binding.fabAddDirect.startAnimation(fromBottom)
            binding.fabAddRecipe.startAnimation(fromBottom)
            binding.fabAdd.startAnimation(rotateOpen)
            binding.bgFloating.setBackgroundResource(R.drawable.blur_dark)
            binding.bgFloating.visibility = View.VISIBLE
        } else {
            binding.fabAddDirect.startAnimation(toBottom)
            binding.fabAddRecipe.startAnimation(toBottom)
            binding.fabAdd.startAnimation(rotateClose)
            binding.bgFloating.setBackgroundColor(Color.TRANSPARENT)
            binding.bgFloating.visibility = View.GONE
        }
    }

    private fun setVisibility(isClicked: Boolean) {
        if (!isClicked) {
            binding.fabAddDirect.visibility = View.VISIBLE
            binding.fabAddRecipe.visibility = View.VISIBLE
            binding.tvAddDirect.visibility = View.VISIBLE
            binding.tvAddRecipe.visibility = View.VISIBLE
        } else {
            binding.fabAddDirect.visibility = View.INVISIBLE
            binding.fabAddRecipe.visibility = View.INVISIBLE
            binding.tvAddDirect.visibility = View.INVISIBLE
            binding.tvAddRecipe.visibility = View.INVISIBLE
        }
    }

    override fun onGetFridgeSuccess(response: GetFridgeResponse) {
        var tabLayoutTextArray = ArrayList<String>()
        tabLayoutTextArray.add(getString(R.string.all))

        var myFridgeFlag = false

        response.result.fridges.forEach {
            if(it.ingredientList.size != 0) {
                myFridgeFlag = true
                return@forEach
            }
        }
        dismissLoadingDialog()

        if (myFridgeFlag) {
            Log.d(TAG, "FridgeFragment : Flag is true")
            val ingredientResult = response.result.fridges

            ingredients.clear()
            ingredientResult.forEach {
                ingredients.add(it)
                tabLayoutTextArray.add(it.ingredientCategoryName)
            }
            // visibility 변경
            binding.viewPager.visibility = View.VISIBLE
            binding.tabLayout.visibility = View.VISIBLE
            binding.tabLayoutLine.visibility = View.VISIBLE
            binding.fridgeFragDefaultTv.visibility = View.GONE
            if(!updateButtonFlag) binding.updateTv.visibility = View.VISIBLE

            // 카테고리 탭 설정
            tabLayout = binding.tabLayout
            viewPager = binding.viewPager
            myFridgeCategoryAdapter = MyFridgeCategoryAdapter(requireActivity(), this)
            viewPager.adapter = myFridgeCategoryAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabLayoutTextArray[position]
            }.attach()

            // 리사이클러뷰
            myFridgeCategoryAdapter.submitList(ingredients)
        } else {
            Log.d(TAG, "Flag False : 냉장고에 재료 없음")
            // visibility 변경
            binding.viewPager.visibility = View.GONE
            binding.tabLayout.visibility = View.GONE
            binding.tabLayoutLine.visibility = View.GONE
            binding.updateTv.visibility = View.GONE
            binding.fridgeFragDefaultTv.visibility = View.VISIBLE
        }
    }

    override fun onGetFridgeFailure(message: String) {

    }

    override fun onPatchFridgeSuccess(response: PatchFridgeResponse) {

    }

    override fun onPatchFridgeFailure(message: String) {

    }

    override fun onClickStorageMethod(method: String, position: Int) {

    }

    override fun onClickCount(cnt: Int, position: Int) {

    }

    override fun onSetExpiredAt(date: String, position: Int) {

    }
}