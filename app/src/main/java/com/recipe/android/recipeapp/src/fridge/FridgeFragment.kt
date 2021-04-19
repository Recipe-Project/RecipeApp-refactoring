package com.recipe.android.recipeapp.src.fridge

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.tasks.Task
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.functions.FirebaseFunctions
import com.google.gson.*
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentFridgeBinding
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeCategoryAdapter
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResponse
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult
import com.recipe.android.recipeapp.src.fridge.home.service.FridgeView
import com.recipe.android.recipeapp.src.fridge.pickIngredient.PickIngredientActivity
import com.recipe.android.recipeapp.src.fridge.receipt.ReceiptIngredientDialog
import com.recipe.android.recipeapp.src.fridge.receipt.ReceiptIngredientService
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.PostReceiptIngredientRequest
import com.recipe.android.recipeapp.src.fridge.receipt.`interface`.ReceiptIngredientView
import com.recipe.android.recipeapp.src.fridge.receipt.models.PostReceiptIngredientResponse
import gun0912.tedimagepicker.builder.TedImagePicker
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class FridgeFragment :
    BaseFragment<FragmentFridgeBinding>(FragmentFridgeBinding::bind, R.layout.fragment_fridge),
    FridgeView {

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 현재 날짜 세팅
        setCurrentDay()

        // 냉장고 조회
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
            TedImagePicker.with(requireContext()).start { uri ->
                Log.d(TAG, uri.toString())
                val intent = Intent(requireActivity(), ReceiptIngredientDialog::class.java)
                intent.putExtra("uri", uri.toString())
                startActivity(intent)
            }
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

        lateinit var tabLayout: TabLayout
        lateinit var viewPager: ViewPager2
        lateinit var myFridgeCategoryAdapter : MyFridgeCategoryAdapter
        var myFridgeFlag = false

        myFridgeFlag = response.result.fridges.size != 0
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
            binding.fridgeFragDefaultTv.visibility = View.INVISIBLE

            // 카테고리 탭 설정
            tabLayout = binding.tabLayout
            viewPager = binding.viewPager
            myFridgeCategoryAdapter = MyFridgeCategoryAdapter(requireActivity())
            viewPager.adapter = myFridgeCategoryAdapter

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.text = tabLayoutTextArray[position]
            }.attach()

            // 리사이클러뷰
            myFridgeCategoryAdapter.submitList(ingredients)
        } else {
            Log.d(TAG, "Flag False : 냉장고에 재료 없음")
        }
    }

    override fun onGetFridgeFailure(message: String) {

    }
}