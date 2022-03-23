package com.itis.android_4sem_1.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.itis.android_4sem_1.di.DIContainer
import com.itis.android_4sem_1.presentation.viewModel.DetailViewModel
import com.itis.android_4sem_1.presentation.viewModel.SearchViewModel

class ViewModelFactory(
    private val di: DIContainer,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(SearchViewModel::class.java) ->
                SearchViewModel(di.getWeatherUsecase, di.getWeatherListUsecase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            modelClass.isAssignableFrom(DetailViewModel::class.java) ->
                DetailViewModel(di.getWeatherUsecase)
                        as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
            else ->
                throw IllegalArgumentException("Unknown ViewModel class")
        }
}

