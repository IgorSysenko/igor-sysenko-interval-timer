package com.ivos.presentation.features.workout

import com.ivos.domain.model.Workout

data class WorkoutScreenState(
    val workout: Workout = Workout(),
    val workoutState: WorkoutState = WorkoutState.DEFAULT,
    val workoutProgress: WorkoutProgress = WorkoutProgress(),
) {
    val headerTimer
        get() = if (workoutState != WorkoutState.DEFAULT) workoutProgress.remainingTime else workout.timer.totalTime

    val mainTimer
        get() = if (workoutState != WorkoutState.DEFAULT) workoutProgress.currentIntervalElapsed else workout.timer.totalTime

    val workoutRunning
        get() = workoutState == WorkoutState.RUNNING

    val workoutPaused
        get() = workoutState == WorkoutState.PAUSED

    val workoutFinished
        get() = workoutState == WorkoutState.COMPLETED
}

enum class WorkoutState {
    DEFAULT, RUNNING, PAUSED, COMPLETED
}
