package com.example.serversentevent.domain

import com.example.serversentevent.network.ApiResponse
import com.example.serversentevent.network.models.QuestionItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TestUseCase @Inject constructor(
    private val testRepository: TestRepository
) {
    suspend fun getAllQuestions(): Flow<ApiResponse<List<QuestionItem>>> =
        testRepository.getAllQuestions()
}
