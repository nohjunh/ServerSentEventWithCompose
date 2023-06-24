package com.example.serversentevent.screens

import android.service.autofill.FieldClassification.Match
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.serversentevent.network.models.QuestionItem
import okhttp3.Response
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import timber.log.Timber

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val matchState by viewModel.matchState.collectAsState()

    Content(viewModel, uiState, matchState)
}

@Composable
fun Content(
    viewModel: MainViewModel,
    state: TestState,
    matchState: MatchState
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val dataSet: List<QuestionItem> = state.dataSet

        Button(
            onClick = {
                viewModel.createEventSource() }
        ) {
            Text("Create EventSource")
        }


        Button(
            onClick = {
                viewModel.closeEventSource() }
        ) {
            Text("Close EventSource")
        }

        LazyColumn {
            item {
                Text(text = "Ready Screen")
            }
            items(dataSet) { item ->
                QuestionCard(item = item)
            }
        }

    }
}

@Composable
fun QuestionCard(item: QuestionItem) {
    Card(
        modifier = Modifier.padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(5.dp)
        ) {
            Text(
                text = item.question,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = item.answer
            )
        }
    }
}
