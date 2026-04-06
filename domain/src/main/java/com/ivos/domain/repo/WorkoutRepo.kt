package com.ivos.domain.repo

import com.ivos.domain.model.Workout
import kotlinx.coroutines.flow.StateFlow

interface WorkoutRepo {
    fun getCurrentWorkout(): StateFlow<Workout?>
    suspend fun getWorkoutById(id: Long): Result<Workout>
    fun setCurrentWorkout(workout: Workout): Result<Unit>
}
