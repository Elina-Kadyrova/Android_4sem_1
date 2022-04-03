package com.itis.android_4sem_1.di

import com.itis.android_4sem_1.di.module.AppModule
import com.itis.android_4sem_1.di.module.NetModule
import com.itis.android_4sem_1.di.module.RepoModule
import com.itis.android_4sem_1.di.module.ViewModelModule
import com.itis.android_4sem_1.presentation.ui.DetailFragment
import com.itis.android_4sem_1.presentation.ui.SearchCityFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetModule::class,
        RepoModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(SearchCityFragment: SearchCityFragment)
    fun inject(detailFragment: DetailFragment)
}
