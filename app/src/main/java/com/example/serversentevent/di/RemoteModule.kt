package com.example.serversentevent.di

import com.example.serversentevent.data.MatchDataSource
import com.example.serversentevent.data.MatchDataSourceImpl
import com.example.serversentevent.network.api.TestApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideTestApiService(@NormalOkHttpClient okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): TestApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(TestApiService::class.java)


    @Provides
    @Singleton
    fun provideMatchDataSource(@SSEOkHttpClient okHttpClient: OkHttpClient,
                               @SSERequestBuilder request: Request): MatchDataSource {
        return MatchDataSourceImpl(okHttpClient, request)
    }
}
