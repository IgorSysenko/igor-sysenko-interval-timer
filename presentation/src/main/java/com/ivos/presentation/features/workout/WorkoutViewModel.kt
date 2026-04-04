package com.ivos.presentation.features.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivos.domain.usecase.GetCurrentWorkoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel@Inject constructor(
    private val getCurrentWorkoutUseCase: GetCurrentWorkoutUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<WorkoutScreenState> = MutableStateFlow(WorkoutScreenState())
    val state: StateFlow<WorkoutScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrentWorkoutUseCase().collect {
                it?.let { workout ->
                    _state.update { state ->
                        state.copy(workout = workout)
                    }
                }
            }
        }
    }
}
