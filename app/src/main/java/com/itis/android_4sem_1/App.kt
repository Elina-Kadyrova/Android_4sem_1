package com.itis.android_4sem_1

import android.app.Application
import com.itis.android_4sem_1.di.AppComponent
import com.itis.android_4sem_1.di.DaggerAppComponent
import com.itis.android_4sem_1.di.module.AppModule
import com.itis.android_4sem_1.di.module.NetModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule())
            .netModule(NetModule())
            .build()
    }

    companion object{
        lateinit var appComponent: AppComponent
    }
}
