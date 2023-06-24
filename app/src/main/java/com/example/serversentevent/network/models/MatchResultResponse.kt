package com.example.serversentevent.network.models

import com.google.gson.annotations.SerializedName

data class MatchResultResponse(
    @SerializedName("status")
    val status: String = "WAIT",
    @SerializedName("location")
    val location: String? = null
)