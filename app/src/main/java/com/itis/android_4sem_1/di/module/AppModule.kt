package com.itis.android_4sem_1.di.module

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
class AppModule {

    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}
