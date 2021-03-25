package com.recipe.android.recipeapp.src.search

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.BaseFragment
import com.recipe.android.recipeapp.databinding.FragmentPublicResultBinding
import com.recipe.android.recipeapp.src.search.adapter.PublicResultRecyclerviewAdapter
import com.recipe.android.recipeapp.src.search.models.PublicRecipeResult

class PublicResultFragment(private val publicResultList : ArrayList<PublicRecipeResult>) : BaseFragment<FragmentPublicResultBinding>(FragmentPublicResultBinding::bind, R.layout.fragment_public_result) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PublicResultRecyclerviewAdapter(publicResultList)
        binding.publicResultFragRecylerview.adapter = adapter
    }
}