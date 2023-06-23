package com.example.serversentevent.di

import com.example.serversentevent.data.TestRepositoryImpl
import com.example.serversentevent.domain.TestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
    interface인 Repository를 주입하기 위한
    Repository Module 작성
*/
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindTestRepository (
        testRepositoryImpl: TestRepositoryImpl
    ): TestRepository

}
