package com.example.serversentevent.di

import android.content.Context
import com.example.serversentevent.network.apiCallAdapter.ApiResultCallAdapterFactory
import com.example.serversentevent.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConfigModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResultCallAdapterFactory.create()) // 빌더에 ApiResultCallAdapterFactory 적용

    @SSEOkHttpClient
    @Provides
    @Singleton
    fun provideSSEOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.MINUTES)
        .writeTimeout(10, TimeUnit.MINUTES)
        .build()

    @SSERequestBuilder
    @Provides
    @Singleton
    fun provideRequestBuilder(): Request =
        Request.Builder()
            .url("http://172.20.10.11:8080/api/match/event")
            //.header("Accept", "application/json; q=0.5")
            .addHeader("Content-Type", "text/event-stream")
            .build()

    @NormalOkHttpClient
    @Singleton
    @Provides
    fun provideNormalOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            this.setLevel(HttpLoggingInterceptor.Level.BODY)
        }
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }
}
