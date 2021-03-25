package com.recipe.android.recipeapp.src.myPage.myRecipe.myRecipeCreate

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.recipe.android.recipeapp.config.BaseActivity
import com.recipe.android.recipeapp.databinding.ActivityMyRecipeCreateBinding
import gun0912.tedimagepicker.builder.TedImagePicker

class MyRecipeCreateActivity :
    BaseActivity<ActivityMyRecipeCreateBinding>(ActivityMyRecipeCreateBinding::inflate) {

    val TAG = "MyRecipeCreateActivity"
    
    private var selectedUriList: List<Uri>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.imgPick.setOnClickListener {
            TedImagePicker.with(this)
                .startMultiImage { uriList ->
                    // showMultiImage(uriList)
                }
        }


    }

//    private fun showMultiImage(uriList: List<Uri>) {
//        this.selectedUriList = uriList
//        Log.d("ted", "uriList: $uriList")
//        binding.ivImage.visibility = View.GONE
//        binding.containerSelectedPhotos.visibility = View.VISIBLE
//
//        binding.containerSelectedPhotos.removeAllViews()
//
//        val viewSize =
//            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics)
//                .toInt()
//        uriList.forEach {
//            val itemImageBinding = ItemImageBinding.inflate(LayoutInflater.from(this))
//            Glide.with(this)
//                .load(it)
//                .apply(RequestOptions().fitCenter())
//                .into(itemImageBinding.ivMedia)
//            itemImageBinding.root.layoutParams = FrameLayout.LayoutParams(viewSize, viewSize)
//            binding.containerSelectedPhotos.addView(itemImageBinding.root)
//        }
//
//    }
}