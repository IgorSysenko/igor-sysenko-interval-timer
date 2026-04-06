package com.ivos.presentation.features.workout

interface WorkoutEvent {
    data object StartWorkout: WorkoutEvent
    data object PauseWorkout: WorkoutEvent
    data object ResetWorkout: WorkoutEvent
    data object RestartWorkout: WorkoutEvent
}
