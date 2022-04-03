package com.itis.android_4sem_1.domain.usecases

import com.itis.android_4sem_1.domain.entity.ListModel
import com.itis.android_4sem_1.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetWeatherListUsecase @Inject constructor(
    private val repository: WeatherRepository,
     private val scope: CoroutineDispatcher = Dispatchers.Main
) {

    suspend operator fun invoke(latitude: Double?, longitude: Double?): ListModel=
        withContext(scope) {
            repository.getWeatherList(latitude, longitude, 10)
        }

}
