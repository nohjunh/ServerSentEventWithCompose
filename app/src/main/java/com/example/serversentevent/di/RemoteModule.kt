package com.example.serversentevent.di

import com.example.serversentevent.data.MatchDataSource
import com.example.serversentevent.data.MatchDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideMatchDataSource(@SSEOkHttpClient okHttpClient: OkHttpClient,
                               @SSERequestBuilder request: Request.Builder): MatchDataSource {
        return MatchDataSourceImpl(okHttpClient, request)
    }
}