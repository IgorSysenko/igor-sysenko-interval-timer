package com.ivos.domain.model

data class Timer(
    val timerId: Int,
    val title: String,
    val totalTime: Int,
    val intervals: List<Interval>
)
