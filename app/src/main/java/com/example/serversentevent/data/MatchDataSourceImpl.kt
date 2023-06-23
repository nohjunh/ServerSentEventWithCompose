package com.example.serversentevent.data

import com.example.serversentevent.di.SSEOkHttpClient
import com.example.serversentevent.di.SSERequestBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import javax.inject.Inject

class MatchDataSourceImpl @Inject constructor(
    @SSEOkHttpClient private val okHttpClient: OkHttpClient,
    @SSERequestBuilder private val request: Request
) : MatchDataSource {

    override fun getEventSource(listener: EventSourceListener) {
        EventSources.createFactory(okHttpClient)
            .newEventSource(request = request, listener = listener)
    }
}