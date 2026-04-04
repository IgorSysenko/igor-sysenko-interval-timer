package com.ivos.presentation.features.load_workout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadWorkoutScreen(
    navigate: () -> Unit,
) {
    val viewModel: LoadWorkoutViewModel = hiltViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    LaunchedEffect(state.value.isLoaded) {
        if (state.value.isLoaded) navigate()
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    viewModel.reduceState(LoadWorkoutEvent.SetId(68))
                    delay(1000)
                    viewModel.reduceState(LoadWorkoutEvent.StartLoading)
                }
            }
        ) {
            Text("First")
        }
    }
}
