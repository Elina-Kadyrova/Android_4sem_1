package com.itis.android_4sem_1.presentation.viewModel

import androidx.lifecycle.*
import com.itis.android_4sem_1.domain.entity.DetailModel
import com.itis.android_4sem_1.domain.usecases.GetWeatherUsecase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class DetailViewModel @AssistedInject constructor(
    @Assisted private val city: String,
    private val getWeatherUsecase: GetWeatherUsecase
):ViewModel() {

    private var _weather: MutableLiveData<Result<DetailModel>> = MutableLiveData()
    val weather: LiveData<Result<DetailModel>> = _weather

    private var _error: MutableLiveData<Exception> = MutableLiveData()
    val error: LiveData<Exception> = _error

    suspend fun getWeatherByName() {
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

    @AssistedFactory
    interface Factory {
        fun create(city: String): DetailViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: Factory,
            city: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                assistedFactory.create(city) as T
        }
    }
}
