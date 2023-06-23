package com.example.serversentevent.screens

data class MatchState(
    val isSuccess: Boolean = false,
    val message: String = ""
)

data class MatchResultState(
    val status: String = "WAIT",
    val location: String? = null
)