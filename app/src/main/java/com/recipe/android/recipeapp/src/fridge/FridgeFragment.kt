package com.recipe.android.recipeapp.src.fridge

import android.annotation.SuppressLint
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
import com.recipe.android.recipeapp.databinding.FragmentFridgeNewBinding
import com.recipe.android.recipeapp.src.fridge.basket.BasketActivity
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeCategoryAdapter
import com.recipe.android.recipeapp.src.fridge.home.dialog.DeleteDialog
import com.recipe.android.recipeapp.src.fridge.home.models.*
import com.recipe.android.recipeapp.src.fridge.pickIngredient.PickIngredientActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

interface IngredientUpdateView {
    fun onClickStorageMethod(string: String, position: Int)
    fun onClickCount(cnt: Int, position: Int)
    fun onSetExpiredAt(date: String, position: Int)
}

interface FridgeView {
    fun onGetFridgeSuccess(response : GetFridgeResponse)
    fun onGetFridgeFailure(message : String)
    fun onPatchFridgeSuccess(response : PatchFridgeResponse)
    fun onPatchFridgeFailure(message : String)
}

class FridgeFragment :
    BaseFragment<FragmentFridgeNewBinding>(FragmentFridgeNewBinding::bind, R.layout.fragment_fridge_new),
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

    var isEditMode = false


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

        // ?????? ?????? ??????
        setCurrentDay()

        binding.tvAddDirect.visibility = View.INVISIBLE
        binding.fabAddDirect.isClickable = false
        binding.tvAddRecipe.visibility = View.INVISIBLE
        binding.fabAddRecipe.isClickable = false

        // + ?????? ??????
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

        // ???????????? ?????? ??????
        binding.fabAddDirect.setOnClickListener {
            val intent = Intent(requireContext(), PickIngredientActivity::class.java)
            startActivity(intent)
        }

        // ????????? ?????? ?????? ??????
        binding.fabAddRecipe.setOnClickListener {
            GligarPicker().limit(1).requestCode(PICKER_REQUEST_CODE).withActivity(requireActivity())
                .show()
        }


        binding.updateTv.setOnClickListener {

            isEditMode = true

            // ????????? ??????
            updateButtonFlag = true
            showLoadingDialog()
            FridgeService(this).tryGetFridge()

            // ???????????? ??????
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
            // ????????? ?????? ???????????? ?????? ??????
            updateButtonFlag = false
            showLoadingDialog()
            FridgeService(this).tryGetFridge()

            // ???????????? ??????
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
            // ????????? ?????? ????????????(????????? ?????? API ?????? / ?????? ???????????? ????????? ?????? API ????????????)
            isEditMode = false

            updateButtonFlag = false
            showLoadingDialog()

            patchFridgeList.forEach { p ->
                p.ingredientList.forEach {

                    if(it.expiredAt != null) {
                        it.expiredAt = it.expiredAt.substring(0, it.expiredAt.length-2)
                        it.expiredAt = "20${it.expiredAt}"
                    }
                    Log.d(TAG, "???????????? : ${it.expiredAt}")

                    patchIngredientList.add(PatchFridgeObject(
                        it.ingredientName,
                        it.expiredAt,
                        it.storageMethod,
                        it.count
                    ))
                }
            }


            FridgeService(this).tryPatchFridge(PatchFridgeRequest(patchIngredientList))

            // ???????????? ??????
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

        // ?????? ??????
        binding.allCheckCheckbox.setOnClickListener {
            FridgeService(this).tryGetFridge()
        }

        // ?????? ??????
        binding.deleteTv.setOnClickListener {
            val intent = Intent(requireContext(), DeleteDialog::class.java)
            startActivity(intent)
        }

        // ????????? ????????????
        binding.btnBasket.setOnClickListener {
            val intent = Intent(requireContext(), BasketActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // ????????? ??????
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

        if (activity != null) {

            binding.tvBasketCnt.text = response.result.fridgeBasketCount.toString()

            // List clear
            patchIngredientList.clear()
            FridgeFragment.patchFridgeList.clear()
            FridgeFragment.checkboxList.clear()

            var tabLayoutTextArray = ArrayList<String>()
            tabLayoutTextArray.add(getString(R.string.all))

            var myFridgeFlag = false
            response.result.fridges.forEach {
                if(it.ingredientList.size != 0) {
                    myFridgeFlag = true
                    return@forEach
                }
            }

            if (myFridgeFlag && activity != null) {
                Log.d(TAG, "FridgeFragment : Flag is true")
                val ingredientResult = response.result.fridges
                var cnt = 0
                var index = 0

                ingredients.clear()

                // ????????? ????????? ?????????
                ingredientResult.forEach { p ->
                    ingredients.add(p)

                    // ??? ???????????? text array ??? category ?????? ????????? ??????
                    tabLayoutTextArray.add(p.ingredientCategoryName)

                    // ????????? ?????? ?????????
                    patchFridgeList.add(p)

                    val checkboxObject = CheckboxData(mutableListOf())
                    if(p.ingredientList.size != 0) {
                        p.ingredientList.forEach{
                            if(binding.allCheckCheckbox.isChecked) {
                                checkboxObject.checkList.add(CheckboxObject(cnt++, true, it.ingredientName))

                                //checkboxList[index].checkList.add(CheckboxObject(cnt++, true, it.ingredientName))
                                Log.d(TAG, "???????????? ????????? / cnt : $cnt")
                            } else {
                                checkboxObject.checkList.add(CheckboxObject(cnt++, false, it.ingredientName))

                                //checkboxList[index].checkList.add(CheckboxObject(cnt++, false, it.ingredientName))
                                Log.d(TAG, "???????????? ????????? / cnt : $cnt")
                            }
                        }
                        FridgeFragment.checkboxList.add(checkboxObject)
                        cnt = 0
                    } else {
                        checkboxList.add(CheckboxData(mutableListOf()))
                    }
                }
                // visibility ??????
                binding.viewPager.visibility = View.VISIBLE
                binding.tabLayout.visibility = View.VISIBLE
                binding.tabLayoutLine.visibility = View.VISIBLE
                binding.fridgeFragDefaultTv.visibility = View.GONE
                if(!updateButtonFlag) binding.updateTv.visibility = View.VISIBLE

                // ???????????? ??? ??????
                tabLayout = binding.tabLayout
                viewPager = binding.viewPager
                myFridgeCategoryAdapter = MyFridgeCategoryAdapter(requireActivity(), this)
                viewPager.adapter = myFridgeCategoryAdapter

                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = tabLayoutTextArray[position]
                }.attach()

                // ??????????????????
                myFridgeCategoryAdapter.submitList(ingredients, isEditMode)

                viewPager.offscreenPageLimit = 5

            } else if (activity != null) {
                Log.d(TAG, "Flag False : ???????????? ?????? ??????")
                // visibility ??????
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
            showCustomToast("????????? ????????? ??????????????????.")
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