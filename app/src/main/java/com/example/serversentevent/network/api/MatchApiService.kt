package com.example.serversentevent.network.api

import com.example.serversentevent.network.ApiResult
import okhttp3.ResponseBody
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface MatchApiService {
    @POST("/api/match")
    suspend fun getEvents(): ApiResult<ResponseBody>
}
