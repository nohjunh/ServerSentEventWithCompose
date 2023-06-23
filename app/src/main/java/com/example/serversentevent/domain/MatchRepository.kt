package com.example.serversentevent.domain

import com.example.serversentevent.network.ApiResponse
import com.example.serversentevent.network.models.MatchResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.sse.EventSourceListener

interface MatchRepository {
    suspend fun getEventSource(listener: EventSourceListener) : Flow<ApiResponse<MatchResponse>>
}
