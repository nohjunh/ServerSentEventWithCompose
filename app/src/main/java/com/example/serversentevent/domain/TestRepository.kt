package com.example.serversentevent.domain

import com.example.serversentevent.network.ApiResponse
import com.example.serversentevent.network.models.QuestionItem
import kotlinx.coroutines.flow.Flow

interface TestRepository {
    suspend fun getAllQuestions(): Flow<ApiResponse<List<QuestionItem>>>
}
