package com.example.serversentevent.domain

import com.example.serversentevent.network.ApiResponse
import com.example.serversentevent.network.models.MatchResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener

interface MatchRepository {
    fun createEventSource(listener: EventSourceListener): EventSource
}