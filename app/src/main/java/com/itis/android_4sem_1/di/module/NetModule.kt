package com.itis.android_4sem_1.di.module

import com.itis.android_4sem_1.BuildConfig
import com.itis.android_4sem_1.data.api.WeatherApi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

private const val QUERY_KEY = "appid"
private const val API_URL = "https://api.openweathermap.org/data/2.5/"
private const val API_KEY = "56fc6c6cb76c0864b4cd055080568268"

@Module
class NetModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyInterceptor(): Interceptor =
        Interceptor{
                chain ->
            val originalRequest = chain.request()
            originalRequest.url.newBuilder()
                .addQueryParameter(QUERY_KEY, API_KEY)
                .build()
                .let {
                    chain.proceed(
                        originalRequest.newBuilder().url(it).build()
                    ) }
        }

    @Provides
    @Singleton
    @Named("logger")
    fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor()
            .setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
    }

    @Provides
    @Singleton
    fun okhttp(
        @Named("apiKey") apiKeyInterceptor: Interceptor,
        @Named("logger") loggingInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(loggingInterceptor)
                }
            }
            .build()

    @Provides
    @Singleton
    fun provideGsonConverter(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun weatherApi(
        okHttpClient: OkHttpClient,
        gsonConverter: GsonConverterFactory,
    ): WeatherApi = Retrofit.Builder()
        .baseUrl(API_URL)
        .client(okHttpClient)
        .addConverterFactory(gsonConverter)
        .build()
        .create(WeatherApi::class.java)
}
