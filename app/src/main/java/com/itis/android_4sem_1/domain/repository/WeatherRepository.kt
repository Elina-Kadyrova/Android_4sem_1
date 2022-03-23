package com.itis.android_4sem_1.domain.repository

import com.itis.android_4sem_1.domain.entity.DetailModel
import com.itis.android_4sem_1.domain.entity.ListModel

interface WeatherRepository {
    suspend fun getWeather(city: String): DetailModel
    suspend fun getWeatherList(longitude: Double?, latitude: Double?, count: Int): ListModel
}
