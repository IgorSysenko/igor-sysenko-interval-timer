package com.ivos.presentation.features.load_workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivos.domain.model.Workout
import com.ivos.domain.usecase.GetWorkoutUseCase
import com.ivos.domain.usecase.SetWorkoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadWorkoutViewModel @Inject constructor(
    private val getWorkoutUseCase: GetWorkoutUseCase,
    private val setWorkoutUseCase: SetWorkoutUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<LoadWorkoutScreenState> = MutableStateFlow(LoadWorkoutScreenState())
    val state: StateFlow<LoadWorkoutScreenState> = _state.asStateFlow()

    fun reduceState(event: LoadWorkoutEvent) = when (event) {
        is LoadWorkoutEvent.StartLoading -> loadWorkout()
        is LoadWorkoutEvent.SetId -> {
            _state.update {
                it.copy(
                    workoutId = event.id,
                    isError = false,
                )
            }
        }
    }

    private fun loadWorkout() {
        _state.update {
            it.copy(
                isError = false,
                isLoading = true,
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            /*_state.update {
                it.copy(
                    isError = true,
                    isLoading = false,
                )
            }*/
            getWorkoutUseCase(_state.value.workoutId.toInt())
                .onSuccess {
                    println("workout $it")
                    saveWorkout(workout = it)
                }
                .onFailure {
                    _state.update { it.copy(isError = true) }
                }
        }
    }

    private fun saveWorkout(workout: Workout) {
        _state.update {
            it.copy(
                isLoading = false,
                workout = workout,
            )
        }

        _state.value.workout?.let { workout ->
            setWorkoutUseCase(workout).onSuccess {
                _state.update {
                    it.copy(
                        isLoaded = true,
                    )
                }
            }
        }
    }
}
