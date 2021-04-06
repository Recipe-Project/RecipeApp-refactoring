package com.recipe.android.recipeapp.config

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.recipe.android.recipeapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {

    // 실 서버
    // var BASE_URL = ""

    // 테스트 서버
     var BASE_URL = "https://juna052.shop:9000"


    companion object{
        const val TAG = "LOG"

        lateinit var instance: ApplicationClass
            private set

        lateinit var sRetrofit: Retrofit

        lateinit var yRetrofit: Retrofit

        lateinit var sSharedPreferences: SharedPreferences

        // SharedPreferences 키 값
        const val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"
        val USER_IDX = "USER_IDX"

    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        sSharedPreferences =
            applicationContext.getSharedPreferences(
                applicationContext.packageName,
                MODE_PRIVATE
            )

        // 레트로핏 인스턴스 생성
        initRetrofitInstance()

        // 카카오 SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }


    // 연결타임아웃시간 5초
    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            .addInterceptor(HttpLoggingInterceptor { message: String ->
                Log.d(
                    "network_info",
                    message
                )
            }.setLevel(HttpLoggingInterceptor.Level.BODY)) // API Response 로그 작성용
            .addNetworkInterceptor(XAccessTokenInterceptor())
            .build()

        sRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        yRetrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}