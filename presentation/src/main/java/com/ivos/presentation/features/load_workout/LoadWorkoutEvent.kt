package com.ivos.presentation.features.load_workout

sealed interface LoadWorkoutEvent {
    data class SetId(val id: Int): LoadWorkoutEvent
    data object StartLoading: LoadWorkoutEvent
}
