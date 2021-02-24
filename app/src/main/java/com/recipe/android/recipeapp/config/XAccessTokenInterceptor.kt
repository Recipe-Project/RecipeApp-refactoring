package com.recipe.android.recipeapp.config

import android.util.Log
import com.recipe.android.recipeapp.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor: Interceptor {

    val TAG = "XAccessTokenInterceptor"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val jwtToken: String? = sSharedPreferences.getString("X_ACCESS_TOKEN", null)
        if (jwtToken != null) {
            builder.addHeader("X-ACCESS-TOKEN", jwtToken)
            Log.d(TAG, "TOKEN : $jwtToken")
        }
        return chain.proceed(builder.build())
    }
}