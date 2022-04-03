package com.itis.android_4sem_1.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.android_4sem_1.domain.entity.DetailModel
import com.itis.android_4sem_1.domain.entity.ListModel
import com.itis.android_4sem_1.domain.usecases.GetWeatherListUsecase
import com.itis.android_4sem_1.domain.usecases.GetWeatherUsecase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getWeatherUsecase: GetWeatherUsecase,
    private val getWeatherListUsecase: GetWeatherListUsecase
): ViewModel()  {

    private var _weather: MutableLiveData<Result<DetailModel>> = MutableLiveData()
    val weather: LiveData<Result<DetailModel>> = _weather

    private var _weatherList:MutableLiveData<Result<ListModel>> = MutableLiveData()
    val weatherList:LiveData<Result<ListModel>> = _weatherList

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    fun getWeatherList(latitude:Double?,longitude:Double?){
        viewModelScope.launch {
            try {
                val weatherList = getWeatherListUsecase(latitude, longitude)
                _weatherList.value = Result.success(weatherList)
            } catch (ex: Exception) {
                _weatherList.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }

    suspend fun getWeather(city: String) {
        viewModelScope.launch {
            try {
                val weather = getWeatherUsecase(city)
                _weather.value = Result.success(weather)
            } catch (ex: Exception) {
                _weather.value = Result.failure(ex)
                _error.value = ex
            }
        }
    }
}
