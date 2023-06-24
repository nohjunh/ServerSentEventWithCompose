package com.example.serversentevent.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.serversentevent.domain.CreateMatchEventSourceUseCase
import com.example.serversentevent.domain.TestUseCase
import com.example.serversentevent.network.ApiResponse
import com.example.serversentevent.network.models.MatchResponse
import com.example.serversentevent.network.models.QuestionItem
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

data class TestState(
    val dataSet: List<QuestionItem> = emptyList()
)

data class MatchState(
    val response: MatchResponse = MatchResponse()

)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val testUseCase: TestUseCase,
    private val createMatchEventSourceUseCase: CreateMatchEventSourceUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow(TestState())
    val uiState: StateFlow<TestState> = _uiState.asStateFlow()

    private val _matchState = MutableStateFlow(MatchState())
    val matchState: StateFlow<MatchState> = _matchState.asStateFlow()

    private var matchEventSource: EventSource? = null

    private val eventSourceListener = object : EventSourceListener() {
        override fun onOpen(eventSource: EventSource, response: Response) {
            Timber.tag("TEST").d("Connection Success")
        }

        override fun onClosed(eventSource: EventSource) {
            Timber.tag("TEST").d("Connection closed")
        }

        override fun onEvent(
            eventSource: EventSource,
            id: String?,
            type: String?,
            data: String
        ) {
            // Parse JSON data
            val gson = Gson()
            val eventData = gson.fromJson(data, MatchResponse::class.java)
            Timber.tag("TEST").d("On Event Received! isSuccess -: ${eventData.isSuccess}")
            Timber.tag("TEST").d("On Event Received! message -: ${eventData.message}")
        }

        override fun onFailure(
            eventSource: EventSource,
            t: Throwable?,
            response: Response?
        ) {
            Timber.tag("TEST").d("On Failure -: ${response?.body?.string()}")
        }
    }

    fun createEventSource() {
        viewModelScope.launch {
            matchEventSource = createMatchEventSourceUseCase.execute(eventSourceListener)
        }
    }

    fun closeEventSource() {
        viewModelScope.launch {
            matchEventSource?.cancel()
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

}
