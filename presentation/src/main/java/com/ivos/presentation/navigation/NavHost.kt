package com.ivos.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ivos.presentation.features.load_workout.LoadWorkoutScreen
import com.ivos.presentation.features.workout.WorkoutScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.LoadWorkoutScreen.route
    ) {

        composable(Screen.LoadWorkoutScreen.route) {
            LoadWorkoutScreen(
                navigate = { navController.navigate(Screen.WorkoutScreen.route) }
            )
        }

        composable(Screen.WorkoutScreen.route) {
            WorkoutScreen(
                navigate = { navController.navigate(Screen.LoadWorkoutScreen.route) }
            )
        }
    }
}
