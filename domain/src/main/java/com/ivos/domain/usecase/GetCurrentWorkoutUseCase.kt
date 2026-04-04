package com.ivos.domain.usecase

import com.ivos.domain.repo.WorkoutRepo
import javax.inject.Inject

class GetCurrentWorkoutUseCase @Inject constructor(
    private val repo: WorkoutRepo,
) {
    operator fun invoke() = repo.getCurrentWorkout()
}
