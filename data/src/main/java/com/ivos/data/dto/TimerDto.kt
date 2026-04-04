package com.ivos.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TimerDto(
    @SerialName("timer_id")
    val timerId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("total_time")
    val totalTime: Int,
    @SerialName("intervals")
    val intervals: List<IntervalDto>
)
