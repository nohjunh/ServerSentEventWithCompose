package com.example.serversentevent.network.models

import com.google.gson.annotations.SerializedName

data class MatchResponse(
    @SerializedName("isSuccess")
    val isSuccess: Boolean = false,
    @SerializedName("message")
    val message: String = ""
)
