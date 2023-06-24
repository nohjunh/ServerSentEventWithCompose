package com.example.serversentevent.data

import com.example.serversentevent.domain.MatchRepository
import com.example.serversentevent.utils.Constants.BASE_URL
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import javax.inject.Inject

class MatchRepositoryImpl @Inject constructor(
   private val dataSource: MatchDataSource
) : MatchRepository {
   override fun connectEventSource(listener: EventSourceListener): EventSource {
      val url = BASE_URL.plus("/api/match/waiting-runner/event")
      return dataSource.connectEvent(url = url, listener = listener)
   }

   override fun connectResultEventSource(listener: EventSourceListener): EventSource {
      val url = BASE_URL.plus("/api/match/result/event")
      return dataSource.connectEvent(url = url, listener = listener)
   }
}