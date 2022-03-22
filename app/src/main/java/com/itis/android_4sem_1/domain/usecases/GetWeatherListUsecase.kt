package com.itis.android_4sem_1.domain.usecases

import com.itis.android_4sem_1.domain.entity.ListModel
import com.itis.android_4sem_1.domain.repository.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetWeatherListUsecase(private val repository: WeatherRepository,
                            private val scope: CoroutineDispatcher = Dispatchers.Main
) {

    suspend operator fun invoke(longitude: Double?, latitude: Double?): ListModel=
        withContext(scope) {
            repository.getWeatherList(longitude, latitude, 10)
        }

}
