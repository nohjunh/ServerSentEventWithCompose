package com.example.serversentevent.data

import com.example.serversentevent.network.ApiResult
import com.example.serversentevent.network.api.TestApiService
import com.example.serversentevent.network.models.QuestionItem
import javax.inject.Inject

class TestDataSource @Inject constructor(
    private val testApi: TestApiService
) {
    suspend operator fun invoke(): ApiResult<List<QuestionItem>> =
        testApi.getAllQuestions()
}