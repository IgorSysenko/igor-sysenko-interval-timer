package com.ivos.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ivos.presentation.R
import com.ivos.presentation.features.workout.WorkoutState

@Composable
fun getIntervalString(
    intervalCount: Int,
) = "$intervalCount ${stringResource(when (intervalCount) {
    1 -> R.string.intervals_list_count_1
    2, 3, 4 -> R.string.intervals_list_count_2_4
    else ->  R.string.intervals_list_count_many
})}"

@Composable
fun getTextForBigTimerText(
    workoutState: WorkoutState,
) = stringResource(when (workoutState) {
    WorkoutState.DEFAULT -> R.string.state_ready_to_start_card_title
    WorkoutState.RUNNING -> R.string.state_running_card_title
    WorkoutState.PAUSED -> R.string.state_paused_card_title
    WorkoutState.COMPLETED -> R.string.state_completed_card_title
}).uppercase()

@Composable
fun getTextForHeader(
    workoutState: WorkoutState,
    time: Int = 0,
) = when (workoutState) {
    WorkoutState.DEFAULT -> formatDuration(time)
    WorkoutState.RUNNING -> formatDuration(time)
    WorkoutState.PAUSED -> stringResource(R.string.state_paused_header_text)
    WorkoutState.COMPLETED -> stringResource(R.string.state_completed_header_text)
}

@Composable
fun getDurationText(
    workoutState: WorkoutState,
    elapsedTime: Int = 0,
    totalTime: Int = 0,
) = when (workoutState) {
    WorkoutState.DEFAULT -> stringResource(R.string.workout_duration_title)
    WorkoutState.COMPLETED -> stringResource(
        R.string.intervals_list_completed_count,
        formatDuration(elapsedTime), formatDuration(totalTime)
    )
    else -> stringResource(
        id = R.string.workout_duration_text,
        formatDuration(elapsedTime), formatDuration(totalTime)
    )
}
