package com.example.serversentevent.data

import com.example.serversentevent.network.ApiResult
import com.example.serversentevent.network.models.MatchResponse
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener

interface MatchDataSource {
    fun createEventSource(listener: EventSourceListener): EventSource
}