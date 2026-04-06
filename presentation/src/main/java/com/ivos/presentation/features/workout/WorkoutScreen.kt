package com.ivos.presentation.features.workout

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
                    text = when (state.workoutState) {
                        WorkoutState.DEFAULT -> formatDuration(state.workout.timer.totalTime)
                        WorkoutState.RUNNING -> formatDuration(state.workout.timer.totalTime)
                        WorkoutState.PAUSED -> stringResource(R.string.state_paused_header_text)
                        WorkoutState.COMPLETED -> stringResource(R.string.state_completed_header_text)
                    },
                    state = state.workoutState,
                )
            }
        )

        TimerCard(
            workoutState = state.workoutState
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(when (state.workoutState) {
                    WorkoutState.DEFAULT -> R.string.state_ready_to_start_card_title
                    WorkoutState.RUNNING -> R.string.state_running_card_title
                    WorkoutState.PAUSED -> R.string.state_paused_card_title
                    WorkoutState.COMPLETED -> R.string.state_completed_card_title
                }).uppercase(),
                color = when (state.workoutState) {
                    WorkoutState.DEFAULT -> LocalExtraColors.current.textTertiary
                    WorkoutState.RUNNING -> MaterialTheme.colorScheme.primary
                    WorkoutState.PAUSED -> LocalExtraColors.current.orange
                    WorkoutState.COMPLETED -> MaterialTheme.colorScheme.secondary
                },
                textAlign = TextAlign.Center,
                style = LocalExtraTypography.current.state,
            )

            Text(
                modifier = Modifier
                    .padding(top = LocalSpacing.current.s),
                text = state.workout.currentInterval.title,
                style = MaterialTheme.typography.labelLarge,
            )

            Spacer(Modifier.height(LocalSpacing.current.m))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = when (state.workoutState) {
                    WorkoutState.DEFAULT -> formatDuration(state.workout.timer.totalTime)
                    WorkoutState.RUNNING -> formatDuration(state.workout.timer.totalTime)
                    WorkoutState.PAUSED -> formatDuration(state.workout.timer.totalTime)
                    WorkoutState.COMPLETED -> formatDuration(state.workout.timer.totalTime)
                },
                color = when (state.workoutState) {
                    WorkoutState.DEFAULT -> MaterialTheme.colorScheme.onBackground
                    WorkoutState.RUNNING -> MaterialTheme.colorScheme.primary
                    WorkoutState.PAUSED -> LocalExtraColors.current.orange
                    WorkoutState.COMPLETED -> MaterialTheme.colorScheme.secondary
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
            )

            Text(
                modifier = Modifier,
                text = if (state.workoutState == WorkoutState.DEFAULT) {
                    stringResource(R.string.workout_duration_title)
                } else {
                    stringResource(
                        id = R.string.workout_duration_text,
                        formatDuration(state.workout.timer.totalTime), formatDuration(state.workout.timer.totalTime)
                    )
                },
                style = LocalExtraTypography.current.mono,
                color = LocalExtraColors.current.textTertiary
            )

            Spacer(Modifier.height(LocalSpacing.current.l))

            TimerProgressBar(
                percent = 0.8f,
                workoutState = state.workoutState
            )
        }

        IntervalListHeaderRow(
            workoutState = state.workoutState,
            intervalCount = 7,
            completedIntervalCount = 3
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            IntervalsList(
                intervals = state.workout.timer.intervals,
                isPaused = state.workoutState == WorkoutState.PAUSED,
                currentIndex = 0,
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
            onPrimaryClick = {},
            onGhostClick = {}
        )
    }
}
