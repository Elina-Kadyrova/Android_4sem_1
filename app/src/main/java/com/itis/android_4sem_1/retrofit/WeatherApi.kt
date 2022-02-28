package com.itis.android_4sem_1.retrofit

import com.itis.android_4sem_1.data.WeatherDetailModel
import com.itis.android_4sem_1.data.WeatherListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("find?units=metric&lang=ru")
    suspend fun getWeatherList(
        @Query("lat") latitude:Double,
        @Query("lon") longitude:Double,
        @Query("cnt") count:Int
    ): WeatherListModel

    @GET("weather?units=metric")
    suspend fun getWeather(
        @Query("q") cityName: String
    ) : WeatherDetailModel
}
