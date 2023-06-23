package com.example.serversentevent.data

import com.example.serversentevent.domain.TestRepository
import com.example.serversentevent.utils.apiRequestFlow
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val testDataSource: TestDataSource
): TestRepository {
    override suspend fun getAllQuestions() = apiRequestFlow {
        testDataSource.invoke()
    }
}