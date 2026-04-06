package com.ivos.presentation.features.workout

data class WorkoutProgress(
    val totalTime: Int = 0,
    val elapsedTime: Int = 0,
    val remainingTime: Int = 0,

    val currentIntervalIndex: Int = 0,
    val currentIntervalElapsed: Int = 0,
    val currentIntervalRemaining: Int = 0,
)
