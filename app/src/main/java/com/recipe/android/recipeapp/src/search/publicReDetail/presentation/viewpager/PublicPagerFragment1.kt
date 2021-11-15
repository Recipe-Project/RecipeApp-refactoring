package com.recipe.android.recipeapp.src.search.publicReDetail.presentation.viewpager

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentPublicViewpager1Binding
import com.recipe.android.recipeapp.src.search.publicReDetail.model.PublicRecipeDetailResponse
import com.recipe.android.recipeapp.src.search.publicReDetail.model.PublicRecipeIngredient
import com.recipe.android.recipeapp.src.search.publicReDetail.presentation.viewpager.frag1.Frag1Adapter

class PublicPagerFragment1() :
    BaseFragment<FragmentPublicViewpager1Binding>(
        FragmentPublicViewpager1Binding::bind,
        R.layout.fragment_public_viewpager_1
    ) {

    private val ingredientExistList = ArrayList<PublicRecipeIngredient>()
    private val ingredientNoList = ArrayList<PublicRecipeIngredient>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey("data") }?.apply {
            val data = getParcelable<PublicRecipeDetailResponse>("data") as PublicRecipeDetailResponse
            setExistIngredients(data.result.recipeIngredientList)
            setRecyclerView()
        }
    }

    private fun setExistIngredients(ingredientList: ArrayList<PublicRecipeIngredient>) {
        ingredientList.forEach {
            if (it.inFridgeYN == "N") {
                ingredientNoList.add(it)
            } else {
                ingredientExistList.add(it)
            }
        }
    }

    private fun setRecyclerView(){
        val frag1Adapter = Frag1Adapter(requireActivity())
        val frag2Adapter = Frag1Adapter(requireActivity())
        binding.rvIngredientExist.apply {
            adapter = frag1Adapter
            layoutManager = GridLayoutManager(requireActivity(), 4)
        }
        binding.rvIngredientNo.apply {
            adapter = frag2Adapter
            layoutManager = GridLayoutManager(requireActivity(), 4)
        }
        frag1Adapter.ingredientList = ingredientExistList
        frag2Adapter.ingredientList = ingredientNoList
        frag1Adapter.notifyDataSetChanged()
        frag2Adapter.notifyDataSetChanged()
    }
}