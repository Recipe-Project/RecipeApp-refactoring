package com.recipe.android.recipeapp.src.search.publicRe.presentation.viewpager.frag2

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

@BindingAdapter("processImg")
fun setProcessImg(view: AppCompatImageView, url: String?) {
    if (!url.isNullOrBlank()) {
        Glide.with(view.context)
            .load(url)
            .transform(CenterCrop(), RoundedCorners(5))
            .into(view)
    } else {
        view.visibility = View.GONE
    }
}