package com.ivos.domain.model

data class Timer(
    val timerId: Int = 0,
    val title: String = "",
    val totalTime: Int = 0,
    val intervals: List<Interval> = emptyList(),
)
