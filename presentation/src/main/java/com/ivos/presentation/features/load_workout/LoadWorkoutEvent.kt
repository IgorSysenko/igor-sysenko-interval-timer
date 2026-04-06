package com.ivos.presentation.features.load_workout

sealed interface LoadWorkoutEvent {
    data class SetId(val id: String): LoadWorkoutEvent
    data object StartLoading: LoadWorkoutEvent
}
