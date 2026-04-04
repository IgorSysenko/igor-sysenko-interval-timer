package com.ivos.data.repo

import com.ivos.data.api.WorkoutApi
import com.ivos.data.mapper.toDomainModel
import com.ivos.data.utils.safeApiRequest
import com.ivos.domain.repo.WorkoutRepo
import javax.inject.Inject

class WorkoutRepoImpl @Inject constructor(
    private val api: WorkoutApi,
): WorkoutRepo {
    /*override suspend fun getWorkoutById(id: Int) = try {
        val dto = api.execute("$id")
        Result.success(dto.toDomainModel())
    } catch (e: Exception) {
        Result.failure(e)
    }*/
    override suspend fun getWorkoutById(id: Int) = safeApiRequest { api.execute("$id").toDomainModel() }
}
