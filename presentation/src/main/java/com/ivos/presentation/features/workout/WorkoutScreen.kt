package com.ivos.presentation.features.workout

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ivos.presentation.R
import com.ivos.presentation.desiign_system.CompletedWorkoutTabsRow
import com.ivos.presentation.desiign_system.IntervalListHeaderRow
import com.ivos.presentation.desiign_system.IntervalsList
import com.ivos.presentation.desiign_system.TimerCard
import com.ivos.presentation.desiign_system.TimerProgressBar
import com.ivos.presentation.desiign_system.WorkoutScreenButtonsLayout
import com.ivos.presentation.desiign_system.WorkoutScreenTopBar
import com.ivos.presentation.desiign_system.WorkoutStateHeader
import com.ivos.presentation.theme.LocalExtraColors
import com.ivos.presentation.theme.LocalExtraTypography
import com.ivos.presentation.theme.LocalSpacing
import com.ivos.presentation.utils.formatDuration
import com.ivos.presentation.utils.getColorForBigTimer
import com.ivos.presentation.utils.getColorForBigTimerText
import com.ivos.presentation.utils.getDurationText
import com.ivos.presentation.utils.getTextForBigTimerText
import com.ivos.presentation.utils.getTextForHeader

@Composable
fun WorkoutScreen(
    navigate: () -> Unit,
) {
    val viewModel: WorkoutViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        WorkoutScreenTopBar(
            modifier = Modifier
                .padding(top = LocalSpacing.current.xxl),
            headerText = state.workout.timer.title,
            onBackClick = navigate,
            endContent = {
                WorkoutStateHeader(
                    text = getTextForHeader(state.workoutState, state.headerTimer),
                    state = state.workoutState,
                )
            }
        )

        TimerCard(
            workoutState = state.workoutState
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = getTextForBigTimerText(state.workoutState),
                color = getColorForBigTimerText(state.workoutState),
                textAlign = TextAlign.Center,
                style = LocalExtraTypography.current.state,
            )

            Text(
                modifier = Modifier
                    .padding(top = LocalSpacing.current.s),
                text = if (state.workoutState == WorkoutState.COMPLETED) {
                    stringResource(R.string.state_completed_card_desc)
                } else {
                    state.workout.timer.intervals
                        .getOrNull(state.workoutProgress.currentIntervalIndex)?.title ?: ""
                },
                color = if (state.workoutState == WorkoutState.COMPLETED) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(Modifier.height(LocalSpacing.current.m))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = if (state.workoutState == WorkoutState.COMPLETED) {
                    formatDuration(state.workoutProgress.remainingTime)
                } else {
                    formatDuration(state.mainTimer)
                },
                color = getColorForBigTimer(state.workoutState),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
            )

            Text(
                modifier = Modifier,
                text = getDurationText(
                    workoutState = state.workoutState,
                    elapsedTime = state.workoutProgress.elapsedTime,
                    totalTime = state.workout.timer.totalTime
                ),
                style = LocalExtraTypography.current.mono,
                color = LocalExtraColors.current.textTertiary
            )

            Spacer(Modifier.height(LocalSpacing.current.l))

            TimerProgressBar(
                percent = state.workoutProgress.elapsedTime.toFloat() / (state.workout.timer.totalTime.takeIf { it > 0 } ?: 1),
                workoutState = state.workoutState
            )
        }

        AnimatedVisibility(
            visible = state.workoutState == WorkoutState.COMPLETED
        ) {
            CompletedWorkoutTabsRow(
                totalTime = state.workout.timer.totalTime,
                intervalsCount = state.workout.timer.intervals.size
            )
        }

        IntervalListHeaderRow(
            workoutState = state.workoutState,
            intervalCount = state.workout.timer.intervals.size,
            completedIntervalCount = state.workoutProgress.currentIntervalIndex
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            IntervalsList(
                intervals = state.workout.timer.intervals,
                workoutState = state.workoutState,
                currentIndex = state.workoutProgress.currentIntervalIndex,
                intervalElapsedTime = state.workoutProgress.currentIntervalElapsed,
                intervalRemainingTime = state.workoutProgress.currentIntervalRemaining,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.background.copy(alpha = 0.6f),
                                MaterialTheme.colorScheme.background,
                                MaterialTheme.colorScheme.background
                            ),
                            startY = 0f,
                            endY = Float.POSITIVE_INFINITY
                        )
                    )
            )
        }

        WorkoutScreenButtonsLayout(
            workoutState = state.workoutState,
            onPrimaryClick = {
                viewModel.reduceState(
                    when (state.workoutState) {
                        WorkoutState.DEFAULT -> WorkoutEvent.StartWorkout
                        WorkoutState.RUNNING -> WorkoutEvent.PauseWorkout
                        WorkoutState.PAUSED -> WorkoutEvent.StartWorkout
                        WorkoutState.COMPLETED -> WorkoutEvent.RestartWorkout
                    }
                )
            },
            onGhostClick = {
                if (state.workoutState == WorkoutState.COMPLETED) {
                    navigate()
                } else {
                    viewModel.reduceState(WorkoutEvent.ResetWorkout)
                }
            }
        )
    }
}
