package com.example.serversentevent.data

import com.example.serversentevent.domain.MatchRepository
import com.example.serversentevent.network.ApiResponse
import com.example.serversentevent.network.models.MatchResponse
import com.example.serversentevent.utils.apiRequestFlow
import kotlinx.coroutines.flow.Flow
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import javax.inject.Inject
import javax.inject.Singleton

class MatchRepositoryImpl @Inject constructor(
   private val dataSource: MatchDataSource
) : MatchRepository {
   override fun createEventSource(listener: EventSourceListener): EventSource {
      return dataSource.createEventSource(listener)
   }
}
