package com.ivos.presentation.desiign_system

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ivos.presentation.R
import com.ivos.presentation.features.workout.WorkoutState
import com.ivos.presentation.theme.LocalExtraColors
import com.ivos.presentation.theme.LocalSpacing
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
            .padding(vertical = LocalSpacing.current.xl),
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
                    completedIntervalCount, intervalCount
                ),
                color = LocalExtraColors.current.textTertiary,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Start
            )
        }

    }
}
