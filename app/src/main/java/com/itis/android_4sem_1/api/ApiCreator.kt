package com.itis.android_4sem_1.api

import com.itis.android_4sem_1.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiCreator {

    private const val QUERY_API_KEY = "appid"
    private const val API_URL = "https://api.openweathermap.org/data/2.5/"
    private const val API_KEY = "56fc6c6cb76c0864b4cd055080568268"

    private val interceptor =
        Interceptor{
                chain ->
        val originalRequest = chain.request()
        originalRequest.url.newBuilder()
            .addQueryParameter(QUERY_API_KEY, API_KEY)
            .build()
            .let {
                chain.proceed(
                originalRequest.newBuilder().url(it).build()
                ) }
    }

    private val okHttpClient by lazy {
        /*OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()*/
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(
                        HttpLoggingInterceptor()
                            .setLevel(
                                HttpLoggingInterceptor.Level.BODY
                            )
                    )
                }
            }
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}
