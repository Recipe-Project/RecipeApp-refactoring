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
import com.recipe.android.recipeapp.src.fridge.basket.BasketActivity
import com.recipe.android.recipeapp.src.fridge.home.`interface`.FridgeView
import com.recipe.android.recipeapp.src.fridge.home.`interface`.IngredientUpdateView
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeCategoryAdapter
import com.recipe.android.recipeapp.src.fridge.home.dialog.DeleteDialog
import com.recipe.android.recipeapp.src.fridge.home.models.*
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
    var patchIngredientList = mutableListOf<PatchFridgeObject>()


    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    lateinit var myFridgeCategoryAdapter : MyFridgeCategoryAdapter

    companion object {
        var updateButtonFlag = false
        var patchFridgeList = mutableListOf<GetFridgeResult>()
        var checkboxList = mutableListOf<CheckboxData>()
    }

    val PICKER_REQUEST_CODE = 5300

    lateinit var uri: Uri

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Button Fix
        updateButtonFlag = false

        // 현재 날짜 세팅
        setCurrentDay()

        binding.tvAddDirect.visibility = View.INVISIBLE
        binding.fabAddDirect.isClickable = false
        binding.tvAddRecipe.visibility = View.INVISIBLE
        binding.fabAddRecipe.isClickable = false

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
            binding.productCountTv.visibility = View.GONE
            binding.deleteTv.visibility = View.VISIBLE
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
            binding.productCountTv.visibility = View.VISIBLE
            binding.deleteTv.visibility = View.GONE
            binding.allCheckTv.visibility = View.GONE
            binding.allCheckCheckbox.visibility = View.GONE
        }

        binding.saveTv.setOnClickListener {
            // 냉장고 수정 저장하기(냉장고 수정 API 호출 / 수정 완료되면 냉장고 조회 API 호출하기)
            updateButtonFlag = false
            showLoadingDialog()

            patchFridgeList.forEach { p ->
                p.ingredientList.forEach {

                    if(it.expiredAt != null) {
                        it.expiredAt = it.expiredAt.substring(0, it.expiredAt.length-2)
                        it.expiredAt = "20${it.expiredAt}"
                    }
                    Log.d(TAG, "유통기한 : ${it.expiredAt}")

                    patchIngredientList.add(PatchFridgeObject(
                        it.ingredientName,
                        it.expiredAt,
                        it.storageMethod,
                        it.count
                    ))
                }
            }


            FridgeService(this).tryPatchFridge(PatchFridgeRequest(patchIngredientList))

            // 레이아웃 변경
            binding.saveTv.visibility = View.INVISIBLE
            binding.cancelTv.visibility = View.INVISIBLE
            binding.updateTv.visibility = View.VISIBLE
            binding.fridgeFragDateTv.visibility = View.VISIBLE

            binding.productNameTv.visibility = View.VISIBLE
            binding.productFreshnessTv.visibility = View.VISIBLE
            binding.productCountTv.visibility = View.VISIBLE
            binding.deleteTv.visibility = View.GONE
            binding.allCheckTv.visibility = View.GONE
            binding.allCheckCheckbox.visibility = View.GONE
        }

        // 전체 선택
        binding.allCheckCheckbox.setOnClickListener {
            FridgeService(this).tryGetFridge()
        }

        // 선택 삭제
        binding.deleteTv.setOnClickListener {
            val intent = Intent(requireContext(), DeleteDialog::class.java)
            startActivity(intent)
        }

        // 바구니 액티비티
        binding.btnBasket.setOnClickListener {
            val intent = Intent(requireContext(), BasketActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // 냉장고 조회
        showLoadingDialog()
        FridgeService(this).tryGetFridge()
        binding.scrollTop.scrollTo(0,0)
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
            binding.fabAddDirect.isClickable = true
            binding.fabAddRecipe.isClickable = true
        } else {
            binding.fabAddDirect.visibility = View.INVISIBLE
            binding.fabAddRecipe.visibility = View.INVISIBLE
            binding.tvAddDirect.visibility = View.INVISIBLE
            binding.tvAddRecipe.visibility = View.INVISIBLE
            binding.fabAddDirect.isClickable = false
            binding.fabAddRecipe.isClickable = false
        }
    }

    override fun onGetFridgeSuccess(response: GetFridgeResponse) {
        if(response.isSuccess) {
            // List clear
            patchIngredientList.clear()
            FridgeFragment.patchFridgeList.clear()
            FridgeFragment.checkboxList.clear()

            var tabLayoutTextArray = ArrayList<String>()
            tabLayoutTextArray.add(getString(R.string.all))

            var myFridgeFlag = false

            binding.tvBasketCnt.text = response.result.fridgeBasketCount.toString()

            response.result.fridges.forEach {
                if(it.ingredientList.size != 0) {
                    myFridgeFlag = true
                    return@forEach
                }
            }

            if (myFridgeFlag) {
                Log.d(TAG, "FridgeFragment : Flag is true")
                val ingredientResult = response.result.fridges
                var cnt = 0
                var index = 0

                ingredients.clear()

                // 가져온 식재료 데이터
                ingredientResult.forEach { p ->
                    ingredients.add(p)

                    // 탭 레이아웃 text array 에 category 이름 하나씩 추가
                    tabLayoutTextArray.add(p.ingredientCategoryName)

                    // 식재료 수정 리스트
                    patchFridgeList.add(p)

                    val checkboxObject = CheckboxData(mutableListOf())
                    if(p.ingredientList.size != 0) {
                        p.ingredientList.forEach{
                            if(binding.allCheckCheckbox.isChecked) {
                                checkboxObject.checkList.add(CheckboxObject(cnt++, true, it.ingredientName))

                                //checkboxList[index].checkList.add(CheckboxObject(cnt++, true, it.ingredientName))
                                Log.d(TAG, "체크박스 디버깅 / cnt : $cnt")
                            } else {
                                checkboxObject.checkList.add(CheckboxObject(cnt++, false, it.ingredientName))

                                //checkboxList[index].checkList.add(CheckboxObject(cnt++, false, it.ingredientName))
                                Log.d(TAG, "체크박스 디버깅 / cnt : $cnt")
                            }
                        }
                        FridgeFragment.checkboxList.add(checkboxObject)
                        cnt = 0
                    } else {
                        checkboxList.add(CheckboxData(mutableListOf()))
                    }
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
            dismissLoadingDialog()
            binding.scrollTop.scrollTo(0,0)
        }
    }

    override fun onGetFridgeFailure(message: String) {

    }

    override fun onPatchFridgeSuccess(response: PatchFridgeResponse) {
        if(response.isSuccess) {
            FridgeService(this).tryGetFridge()
        } else {
            showCustomToast("식재료 수정에 실패했습니다.")
            Log.d(TAG, response.message)
        }


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