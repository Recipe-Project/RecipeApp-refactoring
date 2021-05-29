package com.recipe.android.recipeapp.src.fridge.home.fragment

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentMyFridgeAllCategoryBinding
import com.recipe.android.recipeapp.src.fridge.home.*
import com.recipe.android.recipeapp.src.fridge.home.`interface`.IngredientUpdateView
import com.recipe.android.recipeapp.src.fridge.home.adapter.MyFridgeAllIngredientRecyclerviewAdapter
import com.recipe.android.recipeapp.src.fridge.home.models.GetFridgeResult
import com.recipe.android.recipeapp.src.fridge.pickIngredient.`interface`.PickIngredientActivityView


class MyFridgeAllCategoryFragment
    : BaseFragment<FragmentMyFridgeAllCategoryBinding>(FragmentMyFridgeAllCategoryBinding::bind, R.layout.fragment_my_fridge_all_category) {

    val TAG = "MyFridgeAllCategoryFragment"

    var resultList = ArrayList<GetFridgeResult>()
    var updateView: IngredientUpdateView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey("resultList") }?.apply {
            resultList = getParcelableArrayList<Parcelable>("resultList") as ArrayList<GetFridgeResult>
        }

//        val myFridgeAllCategoryAdapter = updateView?.let {
//            MyFridgeAllIngredientRecyclerviewAdapter(
//                it
//            )
//        }

        val myFridgeAllCategoryAdapter = MyFridgeAllIngredientRecyclerviewAdapter()
        binding.rvAll.adapter = myFridgeAllCategoryAdapter
        binding.rvAll.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        myFridgeAllCategoryAdapter.submitList(resultList)


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is IngredientUpdateView) {
            updateView = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        updateView = null
    }
}