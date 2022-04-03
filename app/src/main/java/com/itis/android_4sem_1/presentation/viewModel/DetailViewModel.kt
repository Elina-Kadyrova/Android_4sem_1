package com.itis.android_4sem_1.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itis.android_4sem_1.domain.entity.DetailModel
import com.itis.android_4sem_1.domain.usecases.GetWeatherUsecase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getWeatherUsecase: GetWeatherUsecase
):ViewModel() {

    private var _weather: MutableLiveData<Result<DetailModel>> = MutableLiveData()
    val weather: LiveData<Result<DetailModel>> = _weather

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    suspend fun getWeatherByName(city: String) {
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
