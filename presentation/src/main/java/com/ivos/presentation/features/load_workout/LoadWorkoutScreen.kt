package com.ivos.presentation.features.load_workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ivos.presentation.R
import com.ivos.presentation.desiign_system.ErrorMessageRow
import com.ivos.presentation.desiign_system.PrimaryButton
import com.ivos.presentation.desiign_system.WorkoutIdInput
import com.ivos.presentation.theme.LocalExtraColors
import com.ivos.presentation.theme.LocalSpacing

@Composable
fun LoadWorkoutScreen(
    navigate: () -> Unit,
) {
    val viewModel: LoadWorkoutViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isLoaded) {
        if (state.isLoaded) navigate()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                modifier = Modifier.size(40.dp),
                imageVector = Icons.Default.AccessTime,
                tint = MaterialTheme.colorScheme.surface,
                contentDescription = stringResource(R.string.app_icon_cd),
            )
        }

        Text(
            modifier = Modifier.padding(top = 40.dp),
            text = stringResource(R.string.interval_timer_title),
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(top = LocalSpacing.current.l),
            text = stringResource(R.string.interval_timer_desc),
            color = LocalExtraColors.current.textSecondary,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
            text = stringResource(R.string.id_field_title),
            color = LocalExtraColors.current.textSecondary,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Start
        )

        WorkoutIdInput(
            modifier = Modifier.padding(top = LocalSpacing.current.m),
            value = state.workoutId,
            isError = state.isError,
            enabled = !state.isLoading,
            onValueChange = {
                if (it.isDigitsOnly() && it.length < MAX_ID_LENGTH) {
                    viewModel.reduceState(LoadWorkoutEvent.SetId(it))
                }
            }
        )

        if (state.isError) {
            ErrorMessageRow(
                modifier = Modifier.padding(top = LocalSpacing.current.m),
                message = stringResource(R.string.load_error_text)
            )
        }

        PrimaryButton(
            modifier = Modifier.padding(top = LocalSpacing.current.m),
            text = stringResource(
                when {
                    state.isLoading -> R.string.loading_in_progress_button_title
                    state.isError -> R.string.rerty_load_button_title
                    else -> R.string.load_button_title
                }
            ),
            enabled = !state.isLoading,
            isLoading = state.isLoading,
            onClick = {
                viewModel.reduceState(LoadWorkoutEvent.StartLoading)
            }
        )

        Spacer(modifier = Modifier.weight(2f))
    }
}
