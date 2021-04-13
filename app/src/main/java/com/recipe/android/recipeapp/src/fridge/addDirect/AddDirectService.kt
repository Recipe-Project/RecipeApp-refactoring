package com.recipe.android.recipeapp.src.fridge.addDirect

import com.recipe.android.recipeapp.config.ApplicationClass
import com.recipe.android.recipeapp.src.fridge.pickIngredient.models.PostIngredientsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AddDirectRetrofitInterface {
    @POST("/fridges/direct-basket")
    fun addDirect(
        @Body param: HashMap<String, Any>
    ): Call<PostIngredientsResponse>
}
class AddDirectService {
    val TAG = "AddDirectService"
    fun addDirectService(ingredientName: String, ingredientIcon: String, ingredientCategoryIdx: Int){

        var param = HashMap<String, Any>()
        param["ingredientName"] = ingredientName
        param["ingredientIcon"] = ingredientIcon
        param["ingredientCategoryIdx"] = ingredientCategoryIdx

        val addDirectRetrofitInterface =
            ApplicationClass.sRetrofit.create(AddDirectRetrofitInterface::class.java)
        addDirectRetrofitInterface.addDirect(param).enqueue(object : Callback<PostIngredientsResponse>{
            override fun onResponse(
                call: Call<PostIngredientsResponse>,
                response: Response<PostIngredientsResponse>
            ) {

            }

            override fun onFailure(call: Call<PostIngredientsResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}