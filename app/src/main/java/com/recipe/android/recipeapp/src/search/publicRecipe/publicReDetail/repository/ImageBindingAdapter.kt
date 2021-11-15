package com.recipe.android.recipeapp.src.search.publicRecipe.publicReDetail.repository

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("setImage")
fun setImg(view: AppCompatImageView, url: String?) {
    Glide.with(view.context)
        .load(url)
        .transform(CenterCrop(), RoundedCorners(5))
        .into(view)
}