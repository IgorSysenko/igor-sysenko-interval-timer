package com.ivos.domain.repo

import com.ivos.domain.model.Workout

interface WorkoutRepo {
    suspend fun getWorkoutById(id: Int): Result<Workout>
}
