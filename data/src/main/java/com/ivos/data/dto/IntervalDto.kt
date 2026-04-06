package com.ivos.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IntervalDto(
    @SerialName("title")
    val title: String,
    @SerialName("time")
    val time: Int
)
