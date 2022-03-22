package com.itis.android_4sem_1.di

import com.itis.android_4sem_1.BuildConfig
import com.itis.android_4sem_1.data.api.WeatherApi
import com.itis.android_4sem_1.data.api.WeatherRepositoryImpl
import com.itis.android_4sem_1.domain.repository.WeatherRepository
import com.itis.android_4sem_1.domain.usecases.GetWeatherListUsecase
import com.itis.android_4sem_1.domain.usecases.GetWeatherUsecase
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DIContainer {

    private const val QUERY_KEY = "appid"
    private const val API_URL = "https://api.openweathermap.org/data/2.5/"
    private const val API_KEY = "56fc6c6cb76c0864b4cd055080568268"

    private val interceptor =
        Interceptor{
                chain ->
        val originalRequest = chain.request()
        originalRequest.url.newBuilder()
            .addQueryParameter(QUERY_KEY, API_KEY)
            .build()
            .let {
                chain.proceed(
                originalRequest.newBuilder().url(it).build()
                ) }
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
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

    val weatherApi: WeatherApi by lazy {
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    val weatherRepository: WeatherRepository = WeatherRepositoryImpl(
        api = weatherApi,
    )

    val getWeatherListUsecase: GetWeatherListUsecase = GetWeatherListUsecase(
        repository = weatherRepository
    )
    val getWeatherUsecase: GetWeatherUsecase = GetWeatherUsecase(
        repository= weatherRepository
    )
}
