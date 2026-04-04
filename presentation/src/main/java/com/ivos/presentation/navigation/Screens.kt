package com.ivos.presentation.navigation

sealed class Screen(val route: String) {
    object LoadWorkoutScreen: Screen("load_work_out")
    object WorkoutScreen: Screen("work_out")
}
