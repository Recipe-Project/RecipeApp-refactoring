package com.recipe.android.recipeapp.config

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass: Application() {

    // 실 서버
     var BASE_URL = ""

    // 테스트 서버
    // var BASE_URL = ""


    companion object{
        const val TAG = "LOG"

        lateinit var instance: ApplicationClass
            private set

        lateinit var sRetrofit: Retrofit

        lateinit var sSharedPreferences: SharedPreferences

        // JWT Token 값
        var X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

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

        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        sRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}