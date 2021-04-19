package com.recipe.android.recipeapp.src.fcm.`interface`

import retrofit2.http.POST

interface FcmInterface {
    @POST("/fridges/notification")
    fun sendNotification(

    )
}