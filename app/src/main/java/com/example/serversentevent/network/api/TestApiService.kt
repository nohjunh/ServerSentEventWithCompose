package com.example.serversentevent.network.api

import com.example.serversentevent.network.ApiResult
import com.example.serversentevent.network.models.QuestionItem
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface TestApiService {
    @GET("test.json")
    suspend fun getAllQuestions(): ApiResult<List<QuestionItem>>

}