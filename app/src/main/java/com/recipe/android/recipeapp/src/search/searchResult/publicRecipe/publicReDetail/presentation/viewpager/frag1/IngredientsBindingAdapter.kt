package com.recipe.android.recipeapp.src.search.searchResult.publicRecipe.publicReDetail.presentation.viewpager.frag1

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("ingredientIcon")
fun ingredientIcon(view: AppCompatImageView, url: String?) {
    url?.let {
        Glide.with(view.context)
            .load(url)
            .into(view)
    }
}