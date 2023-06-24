package com.example.serversentevent.domain

import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener

interface MatchRepository {
    fun connectEventSource(listener: EventSourceListener): EventSource
    fun connectResultEventSource(listener: EventSourceListener): EventSource
}