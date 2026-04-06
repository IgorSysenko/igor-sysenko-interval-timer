package com.ivos.presentation.features.workout

import com.ivos.domain.model.Workout

data class WorkoutScreenState(
    val workout: Workout = Workout(),
    val workoutState: WorkoutState = WorkoutState.RUNNING
)

enum class WorkoutState {
    DEFAULT, RUNNING, PAUSED, COMPLETED
}
