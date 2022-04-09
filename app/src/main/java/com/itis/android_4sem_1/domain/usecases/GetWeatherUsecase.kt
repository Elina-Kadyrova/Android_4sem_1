package com.itis.android_4sem_1.domain.usecases

import com.itis.android_4sem_1.domain.entity.DetailModel
import com.itis.android_4sem_1.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherUsecase @Inject constructor(
    private val repository: WeatherRepository,
    private val scope: CoroutineDispatcher = Dispatchers.Main
) {

    suspend operator fun invoke(city: String): DetailModel =
        withContext(scope) {
            repository.getWeather(city)
        }
}
