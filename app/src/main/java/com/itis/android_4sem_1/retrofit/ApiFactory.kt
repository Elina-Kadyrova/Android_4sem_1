package com.itis.android_4sem_1.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiFactory {

    private const val QUERY_API_KEY = "appid"
    private const val API_URL = "https://api.openweathermap.org/data/2.5/"
    private const val API_KEY = "56fc6c6cb76c0864b4cd055080568268"

    private val apiKeyInterceptor = Interceptor{chain ->
        val original = chain.request()
        original.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()
            .let { chain.proceed(
                original.newBuilder().url(it).build()
            ) }
    }

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}
