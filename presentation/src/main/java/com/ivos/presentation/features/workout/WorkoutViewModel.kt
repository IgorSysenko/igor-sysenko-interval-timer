package com.ivos.presentation.features.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ivos.domain.model.Interval
import com.ivos.domain.usecase.GetCurrentWorkoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel@Inject constructor(
    private val getCurrentWorkoutUseCase: GetCurrentWorkoutUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<WorkoutScreenState> = MutableStateFlow(WorkoutScreenState())
    val state: StateFlow<WorkoutScreenState> = _state.asStateFlow()

    private var timerJob: Job? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getCurrentWorkoutUseCase().collect {
                it?.let { workout ->
                    _state.update { state ->
                        state.copy(
                            workout = workout,
                            workoutProgress = WorkoutProgress(
                                totalTime = workout.timer.totalTime,
                                remainingTime = workout.timer.totalTime,
                                currentIntervalRemaining = (workout.timer.intervals.firstOrNull() ?: Interval()).time
                            )
                        )
                    }
                }
            }
        }
    }

    fun reduceState(event: WorkoutEvent) = when (event) {
        is WorkoutEvent.StartWorkout -> startWorkout()
        is WorkoutEvent.PauseWorkout -> pauseWorkout()
        is WorkoutEvent.ResetWorkout -> resetWorkout()
        is WorkoutEvent.RestartWorkout -> {
            resetWorkout()
            startWorkout()
        }
        else -> {}
    }

    private fun startWorkout() {
        if (state.value.workoutState == WorkoutState.RUNNING) return

        _state.update { it.copy(workoutState = WorkoutState.RUNNING) }

        timerJob = viewModelScope.launch {
            while (this.isActive) {
                delay(1000)
                tick()
            }
        }
    }

    private fun pauseWorkout() {
        timerJob?.cancel()
        _state.update { it.copy(workoutState = WorkoutState.PAUSED) }
    }

    private fun resetWorkout() {
        timerJob?.cancel()
        _state.value = WorkoutScreenState(
            workout = state.value.workout,
            workoutProgress = WorkoutProgress(
                totalTime = state.value.workout.timer.totalTime,
                remainingTime = state.value.workout.timer.totalTime,
                currentIntervalRemaining = (state.value.workout.timer.intervals.firstOrNull() ?: Interval()).time
            )
        )
    }

    private suspend fun tick() {
        val current = _state.value
        val workout = current.workout.timer
        val intervals = workout.intervals
        val elapsed = current.workoutProgress.elapsedTime + 1

        if (elapsed >= workout.totalTime) {
            _state.update {
                it.copy(
                    workoutState = WorkoutState.COMPLETED,
                    workoutProgress = it.workoutProgress.copy(
                        elapsedTime = workout.totalTime,
                        remainingTime = 0
                    )
                )
            }
            timerJob?.cancel()
            return
        }

        var accumulated = 0
        var index = 0

        for (i in intervals.indices) {
            val intervalTime = intervals[i].time
            if (elapsed < accumulated + intervalTime) {
                index = i
                break
            }
            accumulated += intervalTime
        }

        val currentInterval = intervals[index]
        val intervalElapsed = elapsed - accumulated

        _state.update {
            it.copy(
                workoutProgress = WorkoutProgress(
                    totalTime = workout.totalTime,
                    elapsedTime = elapsed,
                    remainingTime = workout.totalTime - elapsed,

                    currentIntervalIndex = index,
                    currentIntervalElapsed = intervalElapsed,
                    currentIntervalRemaining = currentInterval.time - intervalElapsed,
                )
            )
        }
    }
}
