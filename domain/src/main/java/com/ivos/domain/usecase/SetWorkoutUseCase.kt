package com.ivos.domain.usecase

import com.ivos.domain.model.Workout
import com.ivos.domain.repo.WorkoutRepo
import javax.inject.Inject

class SetWorkoutUseCase @Inject constructor(
    private val repo: WorkoutRepo,
) {
    operator fun invoke(workout: Workout) = repo.setCurrentWorkout(workout)
}
