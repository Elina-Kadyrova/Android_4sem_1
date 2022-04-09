package com.itis.android_4sem_1.data.api

import com.itis.android_4sem_1.domain.entity.DetailModel
import com.itis.android_4sem_1.domain.entity.ListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("find?units=metric&lang=ru")
    suspend fun getWeatherList(
        @Query("lat") latitude:Double?,
        @Query("lon") longitude:Double?,
        @Query("cnt") count:Int
    ): ListModel

    @GET("weather?units=metric")
    suspend fun getWeather(
        @Query("q") city: String) : DetailModel
}
