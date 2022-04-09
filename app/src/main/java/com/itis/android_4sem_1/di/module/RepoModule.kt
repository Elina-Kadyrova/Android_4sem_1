package com.itis.android_4sem_1.di.module

import com.itis.android_4sem_1.data.api.WeatherRepositoryImpl
import com.itis.android_4sem_1.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl) : WeatherRepository
}
