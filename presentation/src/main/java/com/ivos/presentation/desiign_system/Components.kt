package com.ivos.presentation.desiign_system

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.ivos.domain.model.Interval
import com.ivos.presentation.R
import com.ivos.presentation.features.workout.WorkoutState
import com.ivos.presentation.theme.LocalExtraColors
import com.ivos.presentation.theme.LocalExtraTypography
import com.ivos.presentation.theme.LocalSpacing
import com.ivos.presentation.utils.formatDuration

@Composable
fun TimerCard(
    modifier: Modifier = Modifier,
    workoutState: WorkoutState,
    content: @Composable ColumnScope.() -> Unit
) {
    val borderColor = when (workoutState) {
        WorkoutState.DEFAULT -> LocalExtraColors.current.border
        WorkoutState.RUNNING -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        WorkoutState.PAUSED -> LocalExtraColors.current.orange.copy(alpha = 0.2f)
        WorkoutState.COMPLETED -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
    }

    val backgroundBrush: Brush = when (workoutState) {
        WorkoutState.DEFAULT -> SolidColor(MaterialTheme.colorScheme.surface)

        WorkoutState.RUNNING -> Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
                MaterialTheme.colorScheme.surface
            )
        )

        WorkoutState.PAUSED -> Brush.verticalGradient(
            colors = listOf(
                LocalExtraColors.current.orange.copy(alpha = 0.04f),
                MaterialTheme.colorScheme.surface
            )
        )

        WorkoutState.COMPLETED -> Brush.verticalGradient(
            colors = listOf(
                MaterialTheme.colorScheme.secondary.copy(alpha = 0.04f),
                MaterialTheme.colorScheme.surface
            )
        )
    }

    Card(
        modifier = modifier
            .padding(top = LocalSpacing.current.xxl)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        border = BorderStroke(
            width = 1.5.dp,
            color = borderColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(brush = backgroundBrush)
                .padding(LocalSpacing.current.xxl)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                content = content
            )
        }
    }
}

@Composable
fun IntervalsList(
    modifier: Modifier = Modifier,
    intervals: List<Interval>,
    currentIndex: Int,
    isWorkoutPaused: Boolean,
    isWorkoutFinished: Boolean,
    isWorkoutRunning: Boolean,
    intervalElapsedTime: Int = 0,
    intervalRemainingTime: Int = 0,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.xs),
        contentPadding = PaddingValues(vertical = LocalSpacing.current.s)
    ) {
        itemsIndexed(intervals) { index, interval ->
            val isActive = index == currentIndex
            val isCompleted = index < currentIndex

            IntervalItem(
                interval = interval,
                index = index,
                isActive = isActive,
                isCompleted = isCompleted,
                isWorkoutPaused = isWorkoutPaused,
                isWorkoutRunning = isWorkoutRunning,
                isWorkoutFinished = isWorkoutFinished,
                progress = if (isActive) (intervalElapsedTime / interval.time.toFloat()) else 0f,
                intervalRemainingTime = intervalElapsedTime, //intervalRemainingTime?
            )

            if (index == intervals.lastIndex) {
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun IntervalItem(
    modifier: Modifier = Modifier,
    interval: Interval,
    index: Int,
    isActive: Boolean,
    isWorkoutPaused: Boolean,
    isWorkoutFinished: Boolean,
    isWorkoutRunning: Boolean,
    isCompleted: Boolean,
    progress: Float,
    intervalRemainingTime: Int = 0,
) {
    val progressColor = if (isWorkoutPaused) {
        LocalExtraColors.current.orange.copy(alpha = 0.1f)
    } else {
        LocalExtraColors.current.primaryLight
    }
    val borderColor = when {
        isCompleted || isWorkoutFinished -> Color.Transparent
        isActive && isWorkoutPaused -> LocalExtraColors.current.orange.copy(alpha = 0.2f)
        isActive -> MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        else -> Color.Transparent
    }

    val backgroundColor = if (isCompleted) {
        MaterialTheme.colorScheme.surface.copy(alpha = 0.45f)
    } else {
        MaterialTheme.colorScheme.surface
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .border(
                border = BorderStroke(1.5.dp, borderColor),
                shape = MaterialTheme.shapes.small,
            )
            .background(backgroundColor)
            .drawBehind {
                if (isActive && progress > 0f && !isWorkoutFinished) {
                    val progressWidth = size.width * progress

                    drawRect(
                        color = progressColor,
                        size = Size(progressWidth, size.height)
                    )
                }
            },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .background(Color.Transparent)
                .padding(LocalSpacing.current.m),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(
                        when {
                            isActive && isWorkoutPaused -> LocalExtraColors.current.orange
                            isCompleted || isWorkoutFinished -> Color.Transparent
                            isActive -> MaterialTheme.colorScheme.primary
                            else -> LocalExtraColors.current.border
                        }
                    )
            ) {
                if (!isCompleted && !isWorkoutFinished) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        text = "${index + 1}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isActive) MaterialTheme.colorScheme.onPrimary else LocalExtraColors.current.textTertiary
                    )
                } else {
                    Icon(
                        modifier = Modifier
                            .size(16.dp)
                            .align(Alignment.Center),
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        tint = if (isWorkoutFinished) {
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                        } else LocalExtraColors.current.textTertiary
                    )
                }
            }

            Text(
                modifier = Modifier
                    .padding(start = LocalSpacing.current.m),
                text = interval.title,
                textDecoration = if (isCompleted || isWorkoutFinished) TextDecoration.LineThrough else null,
                style = MaterialTheme.typography.titleMedium,
                color = if (isCompleted || isWorkoutFinished)
                    LocalExtraColors.current.textTertiary
                else
                    MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.weight(1f))

            Text(
                modifier = Modifier,
                text = formatDuration(if (isActive && (isWorkoutRunning || isWorkoutPaused) && !isWorkoutFinished) intervalRemainingTime else interval.time),
                style = LocalExtraTypography.current.mono.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = when {
                    isActive && isWorkoutPaused -> LocalExtraColors.current.orange
                    isCompleted || isWorkoutFinished -> LocalExtraColors.current.textTertiary
                    isActive -> MaterialTheme.colorScheme.primary
                    else -> LocalExtraColors.current.textSecondary
                }
            )
        }
    }
}

@Composable
fun TimerProgressBar(
    modifier: Modifier = Modifier,
    percent: Float,
    workoutState: WorkoutState,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(LocalExtraColors.current.border)
    ) {
        Box(
            modifier = modifier
                .align(Alignment.CenterStart)
                .fillMaxWidth(fraction = percent)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(
                    when (workoutState) {
                        WorkoutState.DEFAULT -> Color.Transparent
                        WorkoutState.RUNNING -> MaterialTheme.colorScheme.primary
                        WorkoutState.PAUSED -> LocalExtraColors.current.orange
                        WorkoutState.COMPLETED -> MaterialTheme.colorScheme.secondary
                    }
                )
        )
    }
}

@Composable
fun WorkoutScreenButtonsLayout(
    modifier: Modifier = Modifier,
    workoutState: WorkoutState,
    onPrimaryClick: () -> Unit,
    onGhostClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(bottom = LocalSpacing.current.xxl)
    ) {
        PrimaryButton(
            modifier = Modifier
                .padding(bottom = LocalSpacing.current.m),
            text = stringResource(
                when (workoutState) {
                    WorkoutState.DEFAULT -> R.string.workout_button_start_title
                    WorkoutState.RUNNING -> R.string.workout_button_pause_title
                    WorkoutState.PAUSED -> R.string.workout_button_continue_title
                    WorkoutState.COMPLETED ->R.string.workout_button_completed_title
                }
            ),
            enabled = true,
            isLoading = false,
            containerColor = when (workoutState) {
                WorkoutState.DEFAULT -> MaterialTheme.colorScheme.primary
                WorkoutState.RUNNING -> LocalExtraColors.current.orange
                WorkoutState.PAUSED -> MaterialTheme.colorScheme.primary
                WorkoutState.COMPLETED -> MaterialTheme.colorScheme.secondary
            },
            onClick = onPrimaryClick,
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = LocalSpacing.current.s),
                imageVector = when (workoutState) {
                    WorkoutState.DEFAULT -> Icons.Default.PlayArrow
                    WorkoutState.RUNNING -> Icons.Default.Pause
                    WorkoutState.PAUSED -> Icons.Default.PlayArrow
                    WorkoutState.COMPLETED -> Icons.Default.Replay
                },
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surface
            )

        }

        AnimatedVisibility(
            visible = workoutState != WorkoutState.DEFAULT
        ) {
            GhostButton(
                modifier = Modifier
                    .padding(bottom = LocalSpacing.current.xxl),
                text = stringResource(
                    when (workoutState) {
                        WorkoutState.COMPLETED -> R.string.workout_ghost_button_new_workout_title
                        else -> R.string.workout_ghost_button_reset_title
                    }
                ),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = when (workoutState) {
                    WorkoutState.COMPLETED -> LocalExtraColors.current.textSecondary
                    else -> MaterialTheme.colorScheme.error
                },
                borderColor = when (workoutState) {
                    WorkoutState.COMPLETED -> LocalExtraColors.current.border
                    else -> MaterialTheme.colorScheme.error.copy(alpha = 0.2f)
                },
                onClick = onGhostClick,
            )
        }
    }
}

@Composable
fun WorkoutStateHeader(
    modifier: Modifier = Modifier,
    text: String,
    state: WorkoutState,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        when (state) {
            WorkoutState.RUNNING -> RunningBadge()
            WorkoutState.PAUSED -> PausedBadge()
            else -> {}
        }

        Text(
            modifier = Modifier,
            text = text,
            color = when (state) {
                WorkoutState.DEFAULT -> LocalExtraColors.current.textSecondary
                WorkoutState.RUNNING -> MaterialTheme.colorScheme.primary
                WorkoutState.PAUSED -> LocalExtraColors.current.orange
                WorkoutState.COMPLETED -> MaterialTheme.colorScheme.secondary
            },
            style = LocalExtraTypography.current.button,
        )
    }
}

@Composable
fun RunningBadge() {
    Box(
        modifier = Modifier
            .size(10.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(MaterialTheme.colorScheme.primary)
    )
}

@Composable
private fun PausedBadge() {
    Box(
        modifier = Modifier.width(9.dp)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(4.dp)
                .height(10.dp)
                .background(LocalExtraColors.current.orange)
        )

        Box(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .width(4.dp)
                .height(10.dp)
                .background(LocalExtraColors.current.orange)
        )
    }
}
