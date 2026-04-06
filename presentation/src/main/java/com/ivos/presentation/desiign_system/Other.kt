package com.ivos.presentation.desiign_system

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ivos.presentation.R
import com.ivos.presentation.features.workout.WorkoutState
import com.ivos.presentation.theme.LocalExtraColors
import com.ivos.presentation.theme.LocalExtraTypography
import com.ivos.presentation.theme.LocalSpacing
import com.ivos.presentation.utils.formatDuration
import com.ivos.presentation.utils.getIntervalString

@Composable
fun ErrorMessageRow(
    modifier: Modifier = Modifier,
    message: String,
    startContent: @Composable () -> Unit = {
        Icon(
            modifier = Modifier
                .size(16.dp),
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
        )
    },
    endContent: @Composable () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalSpacing.current.xs),
    ) {
        startContent()

        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start
        )

        endContent()
    }
}

@Composable
fun IntervalListHeaderRow(
    modifier: Modifier = Modifier,
    workoutState: WorkoutState,
    intervalCount: Int = 0,
    completedIntervalCount: Int = 0,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = LocalSpacing.current.xl,
                bottom = LocalSpacing.current.xs,
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier,
            text = stringResource(R.string.intervals_list_header_text),
            color = LocalExtraColors.current.textSecondary,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Start
        )

        Spacer(Modifier.weight(1f))

        if (workoutState == WorkoutState.DEFAULT) {
            Text(
                modifier = Modifier,
                text = getIntervalString(intervalCount).lowercase(),
                color = LocalExtraColors.current.textTertiary,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start
            )
        } else {
            Text(
                modifier = Modifier,
                text = stringResource(
                    id = R.string.intervals_list_completed_count,
                    if (workoutState == WorkoutState.COMPLETED) intervalCount else completedIntervalCount, intervalCount
                ),
                color = LocalExtraColors.current.textTertiary,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Start
            )
        }

        if (workoutState == WorkoutState.COMPLETED) {
            Icon(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .size(16.dp),
                imageVector = Icons.Default.Done,
                contentDescription = null,
                tint = LocalExtraColors.current.textTertiary
            )
        }
    }
}

@Composable
fun CompletedWorkoutTabsRow(
    modifier: Modifier = Modifier,
    totalTime: Int = 0,
    intervalsCount: Int = 0,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = LocalSpacing.current.xl)
    ) {
        CompletedWorkoutTab(
            topText = formatDuration(totalTime),
            bottomText = stringResource(R.string.workout_duration_title)
        )
        Spacer(Modifier.width(LocalSpacing.current.s))

        CompletedWorkoutTab(
            topText = "$intervalsCount",
            bottomText = getIntervalString(intervalsCount, false)
        )
    }
}

@Composable
private fun RowScope.CompletedWorkoutTab(
    topText: String,
    bottomText: String,
) {
    Box(
        modifier = Modifier
            .clip(shape = MaterialTheme.shapes.medium)
            .height(64.dp)
            .weight(1f)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                border = BorderStroke(
                    width = 1.5.dp,
                    color = LocalExtraColors.current.border
                ),
                shape = MaterialTheme.shapes.medium
            ),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = topText,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                style = LocalExtraTypography.current.mono.copy(fontSize = 24.sp),
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = bottomText,
                color = LocalExtraColors.current.textTertiary,
                textAlign = TextAlign.Center,
                style = LocalExtraTypography.current.mono.copy(fontSize = 14.sp),
            )
        }
    }
}
