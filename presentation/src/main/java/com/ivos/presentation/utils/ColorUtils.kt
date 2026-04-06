package com.ivos.presentation.utils

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.ivos.presentation.features.workout.WorkoutState
import com.ivos.presentation.theme.LocalExtraColors

@Composable
fun getColorForBigTimer(
    workoutState: WorkoutState,
) = when (workoutState) {
    WorkoutState.DEFAULT -> MaterialTheme.colorScheme.onBackground
    WorkoutState.RUNNING -> MaterialTheme.colorScheme.primary
    WorkoutState.PAUSED -> LocalExtraColors.current.orange
    WorkoutState.COMPLETED -> MaterialTheme.colorScheme.secondary
}

@Composable
fun getColorForBigTimerText(
    workoutState: WorkoutState,
) = when (workoutState) {
    WorkoutState.DEFAULT -> LocalExtraColors.current.textTertiary
    WorkoutState.RUNNING -> MaterialTheme.colorScheme.primary
    WorkoutState.PAUSED -> LocalExtraColors.current.orange
    WorkoutState.COMPLETED -> MaterialTheme.colorScheme.secondary
}
