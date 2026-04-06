package com.ivos.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutDto(
    @SerialName("timer")
    val timer: TimerDto,
)
