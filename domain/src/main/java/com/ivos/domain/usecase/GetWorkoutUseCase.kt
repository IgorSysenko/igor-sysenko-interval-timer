package com.ivos.domain.usecase

import com.ivos.domain.repo.WorkoutRepo
import javax.inject.Inject

class GetWorkoutUseCase @Inject constructor(
    private val repo: WorkoutRepo,
) {
    suspend operator fun invoke(id: Long) = repo.getWorkoutById(id)
}
