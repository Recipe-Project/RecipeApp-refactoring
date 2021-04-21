package com.recipe.android.recipeapp.src.fcm.`interface`

import com.recipe.android.recipeapp.src.fcm.models.FcmResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST

interface FcmInterface {
    @POST("/fridges/notification")
    fun sendNotification(

    )

    @PATCH("/fcm/token")
    fun patchToken(
        @Body param: HashMap<String, Any>
    ): Call<FcmResponse>

}