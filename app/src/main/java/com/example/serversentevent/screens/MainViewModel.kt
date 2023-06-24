
package com.example.serversentevent.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serversentevent.domain.ConnectMatchEventUseCase
import com.example.serversentevent.domain.ConnectMatchResultEventUseCase
import com.example.serversentevent.network.models.MatchResponse
import com.example.serversentevent.network.models.MatchResultResponse
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val connectMatchEventUseCase: ConnectMatchEventUseCase,
    private val connectMatchResultEventUseCase: ConnectMatchResultEventUseCase
): ViewModel() {

    private val _matchEventState = MutableStateFlow(MatchState())
    val matchEventState: StateFlow<MatchState> = _matchEventState.asStateFlow()

    private val _matchResultEventState = MutableStateFlow(MatchResultState())
    val matchResultEventState: StateFlow<MatchResultState> = _matchResultEventState.asStateFlow()

    private lateinit var connectMatchEventSource: EventSource
    lateinit var connectMatchResultSource: EventSource

    private val gson = Gson()

    private fun createEventListener(
        onEvent: (data: String) -> Unit
    ): EventSourceListener {
        return object : EventSourceListener() {
            override fun onOpen(eventSource: EventSource, response: Response) {
                Timber.tag("Event").d("Connection Success")
            }

            override fun onClosed(eventSource: EventSource) {
                Timber.tag("Event").d("Connection closed")
            }

            override fun onEvent(
                eventSource: EventSource,
                id: String?,
                type: String?,
                data: String
            ) {
                onEvent(data)
            }

            override fun onFailure(
                eventSource: EventSource,
                t: Throwable?,
                response: Response?
            ) {
                Timber.tag("Event").d("On Failure -: ${response?.body?.string()}")
            }
        }
    }

    private fun handleMatchEvent(data: String) {
        val eventData = gson.fromJson(data, MatchResponse::class.java)
        Timber.tag("Event").d("Event Received: isSuccess -: ${eventData.isSuccess}")
        Timber.tag("Event").d("Event Received: message -: ${eventData.message}")
        _matchEventState.update {
            it.copy(
                isSuccess = eventData.isSuccess,
                message = eventData.message
            )
        }
    }

    private fun handleMatchResultEvent(data: String) {
        val eventData = gson.fromJson(data, MatchResultResponse::class.java)
        Timber.tag("Event").d("Event Received: status -: ${eventData.status}")
        Timber.tag("Event").d("Event Received: location -: ${eventData.location}")
        _matchResultEventState.update {
            it.copy(
                status = eventData.status,
                location = eventData.location
            )
        }
    }

    fun connectMatchEventSource() {
        viewModelScope.launch {
            val listener = createEventListener(
                onEvent = ::handleMatchEvent
            )
            connectMatchEventSource = connectMatchEventUseCase.execute(listener)
        }
    }

    fun connectMatchResultEventSource() {
        viewModelScope.launch {
            val listener = createEventListener(
                onEvent = ::handleMatchResultEvent
            )
            connectMatchResultSource = connectMatchResultEventUseCase.execute(listener)
        }
    }

    fun closeMatchEventSource() {
        viewModelScope.launch {
            connectMatchEventSource.cancel()
        }
    }

    fun closeMatchResultEventSource() {
        viewModelScope.launch {
            connectMatchResultSource.cancel()
        }
    }

}
