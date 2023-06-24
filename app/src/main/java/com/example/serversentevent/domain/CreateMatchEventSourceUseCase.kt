package com.example.serversentevent.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.sse.EventSourceListener
import javax.inject.Inject

class CreateMatchEventSourceUseCase @Inject constructor(
    private val matchRepository: MatchRepository
) {
    suspend fun execute(listener: EventSourceListener) = withContext(Dispatchers.IO) {
        matchRepository.createEventSource(listener)
    }
}