package com.example.serversentevent.data

import okhttp3.sse.EventSourceListener

interface MatchDataSource {
    fun getEventSource(listener: EventSourceListener)
}
