package com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.viewHolder

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.recipe.android.recipeapp.R
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.databinding.ItemScrapPublicRecipeBinding
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.PublicScrapFragmentView
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.PublicScrapService
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.adapter.PublicScrapRecyclerViewAdapter
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PostPublicScrapResponse
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrap
import com.recipe.android.recipeapp.src.scrapRecipe.publicScrap.models.PublicScrapResponse

class PublicScrapViewHolder(
    val binding: ItemScrapPublicRecipeBinding,
    val publicScrapRecyclerViewAdapter: PublicScrapRecyclerViewAdapter,
    val publicRecipeItemList: ArrayList<PublicScrap>,
    val view: PublicScrapFragmentView
) :
    RecyclerView.ViewHolder(binding.root), PublicScrapFragmentView {

    lateinit var publicScrapItem: PublicScrap

    fun bindWithView(publicScrapItem: PublicScrap) {

        this.publicScrapItem = publicScrapItem

        binding.tvTitle.text = publicScrapItem.title
        if (publicScrapItem.thumbnail != null) {
            Glide.with(ApplicationClass.instance).load(publicScrapItem.thumbnail).transform(
                CenterCrop(), RoundedCorners(20)
            ).into(binding.imgThumbnail)
        }
        binding.tvScrapCnt.text = publicScrapItem.scrapCount.toString()
        binding.tvContent.text = publicScrapItem.content

        binding.btnScrap.setOnClickListener {
            // 스크랩 취소
            val params = HashMap<String, Any>()
            params["recipeId"] = publicScrapItem.recipeId
            PublicScrapService(this).postPublicScrap(params)
        }
    }

    override fun onGetPublicScrapSuccess(response: PublicScrapResponse) {

    }

    override fun onGetPublicScrapFailure(message: String) {
    }

    override fun onPostPublicScrapSuccess(response: PostPublicScrapResponse) {
        if (response.isSuccess) {
            Toast.makeText(ApplicationClass.instance, "스크랩이 취소되었습니다.", Toast.LENGTH_SHORT).show()
            publicRecipeItemList.remove(publicScrapItem)
            publicScrapRecyclerViewAdapter.notifyDataSetChanged()
            view.onPostPublicScrapSuccess(response)
        } else {
            Toast.makeText(ApplicationClass.instance, ApplicationClass.instance.getText(R.string.networkError), Toast.LENGTH_SHORT).show()
        }
    }
}