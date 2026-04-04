package com.ivos.presentation.features.load_workout

import com.ivos.domain.model.Workout

data class LoadWorkoutScreenState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isLoaded: Boolean = false,
    val workoutId: Int = 0,
    val workout: Workout? = null,
)
