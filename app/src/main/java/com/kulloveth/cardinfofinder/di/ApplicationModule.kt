package com.kulloveth.cardinfofinder.di

import com.kulloveth.cardinfofinder.BuildConfig
import com.kulloveth.cardinfofinder.data.CardRepository
import com.kulloveth.cardinfofinder.data.Repository
import com.kulloveth.cardinfofinder.network.RetrofitService
import com.kulloveth.cardinfofinder.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {
    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, BASE_URL: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    @Provides
    @Singleton
    fun provideRetrofitService(retrofit: Retrofit) = retrofit.create(RetrofitService::class.java)

    @Provides
    @Singleton
    fun provideCardRepository(repository: Repository):CardRepository=repository


}