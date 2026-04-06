package com.ivos.data.repo

import com.ivos.data.api.WorkoutApi
import com.ivos.data.mapper.toDomainModel
import com.ivos.data.utils.safeApiRequest
import com.ivos.domain.model.Workout
import com.ivos.domain.repo.WorkoutRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepoImpl @Inject constructor(
    private val api: WorkoutApi,
): WorkoutRepo {

    private val _currentWorkout: MutableStateFlow<Workout?> = MutableStateFlow(null)
    override fun getCurrentWorkout(): StateFlow<Workout?> = _currentWorkout.asStateFlow()

    override suspend fun getWorkoutById(id: Long) = safeApiRequest { api.execute("$id").toDomainModel() }
    override fun setCurrentWorkout(workout: Workout): Result<Unit> {
        _currentWorkout.value = workout
        return Result.success(Unit)
    }
}
