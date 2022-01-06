package com.recipe.android.recipeapp.src.search.searchResult.blogRecipe

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.search.searchResult.blogRecipe.`interface`.BlogRecipeInterface
import com.recipe.android.recipeapp.src.search.searchResult.blogRecipe.models.BlogRecipeResponse
import com.recipe.android.recipeapp.src.search.searchResult.blogRecipe.models.BlogRecipeScrapRequest
import com.recipe.android.recipeapp.src.search.searchResult.blogRecipe.models.BlogRecipeScrapResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BlogRecipeService(val view: BlogRecipeView?) {

    val TAG = "BlogRecipeService"

    fun getBlogRecipe(keyword : String, display : Int, start : Int) {
        val blogRecipeInterface = ApplicationClass.sRetrofit.create(BlogRecipeInterface::class.java)
        blogRecipeInterface.getBlogRecipe(keyword, display, start).enqueue(object : Callback<BlogRecipeResponse>{
            override fun onResponse(call: Call<BlogRecipeResponse>, response: Response<BlogRecipeResponse>) {
                Log.d(TAG, "BlogRecipeService - onResponse() : 블로그레시피 조회 성공")
                view?.onGetBlogRecipeSuccess(response.body() as BlogRecipeResponse)
            }

            override fun onFailure(call: Call<BlogRecipeResponse>, t: Throwable) {
                view?.onGetBlogRecipeFailure(t.message ?: "통신오류")
            }
        })
    }

    fun tryPostAddingScrap(request : BlogRecipeScrapRequest) {
        val blogRecipeInterface = ApplicationClass.sRetrofit.create(BlogRecipeInterface::class.java)
        blogRecipeInterface.postAddingScrap(request).enqueue(object : Callback<BlogRecipeScrapResponse>{
            override fun onResponse(call: Call<BlogRecipeScrapResponse>, response: Response<BlogRecipeScrapResponse>) {
                Log.d(TAG, "BlogRecipeService - onResponse() : 블로그레시피 스크랩 성공")
                view?.onPostBlogRecipeScrapSuccess(response.body() as BlogRecipeScrapResponse)
            }

            override fun onFailure(call: Call<BlogRecipeScrapResponse>, t: Throwable) {
                view?.onPostBlogRecipeScrapFailure(t.message ?: "통신오류")
            }

        })
    }


}