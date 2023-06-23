package com.example.serversentevent.di

import com.example.serversentevent.network.api.MatchApiService
import com.example.serversentevent.network.api.TestApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideTestApiService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): TestApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(TestApiService::class.java)

    @Provides
    @Singleton
    fun provideMatchApiService(@SSEOkHttpClient okHttpClient: OkHttpClient, request: Request.Builder): Request =
        request.build()
}
