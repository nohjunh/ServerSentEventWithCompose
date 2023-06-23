package com.example.serversentevent.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serversentevent.domain.MatchUseCase
import com.example.serversentevent.domain.TestUseCase
import com.example.serversentevent.network.ApiResponse
import com.example.serversentevent.network.models.MatchResponse
import com.example.serversentevent.network.models.QuestionItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

data class TestState(
    val dataSet: List<QuestionItem> = emptyList()
)

data class MatchState(
    val response: MatchResponse
)


@HiltViewModel
class MainViewModel @Inject constructor(
    private val testUseCase: TestUseCase,
    private val matchUseCase: MatchUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(TestState())
    val uiState: StateFlow<TestState> = _uiState.asStateFlow()

    private val _matchState = MutableStateFlow(MatchState())
    val matchState: StateFlow<MatchState> = _matchState.asStateFlow()

    private val eventSourceListener = object : EventSourceListener() {
        override fun onOpen(eventSource: EventSource, response: Response) {
            super.onOpen(eventSource, response)
            Timber.tag("TEST").d("Connection Opened")
        }

        override fun onClosed(eventSource: EventSource) {
            super.onClosed(eventSource)
            Timber.tag("TEST").d("Connection Closed")
        }

        override fun onEvent(
            eventSource: EventSource,
            id: String?,
            type: String?,
            data: String
        ) {
            super.onEvent(eventSource, id, type, data)
            Timber.tag("TEST").d("On Event Received! Data -: $data")
        }

        override fun onFailure(eventSource: EventSource, t: Throwable?, response: Response?) {
            super.onFailure(eventSource, t, response)
            Timber.tag("TEST").d("On Failure -: ${response?.body}")
        }
    }

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() = viewModelScope.launch {
        testUseCase.getAllQuestions().collect() {
            when (it) {
                is ApiResponse.Success -> {
                    Timber.tag("ViewModel").d("$it")
                    _uiState.update { state ->
                        state.copy(
                            dataSet = it.data
                        )
                    }
                }

                is ApiResponse.Failure -> {
                    Timber.tag("getAllQuestion_ApiFailure").e("${it.code} ${it.errorMessage}")
                }

                ApiResponse.Loading -> {
                    Timber.tag("ViewModel").e("Loading")
                }
            }
        }
    }

    private fun connectSSEServer() = viewModelScope.launch {
        matchUseCase.getEventSource(eventSourceListener)
    }
}