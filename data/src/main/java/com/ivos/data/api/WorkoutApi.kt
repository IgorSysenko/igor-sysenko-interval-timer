package com.ivos.data.api

import com.ivos.data.dto.WorkoutDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class WorkoutApi  @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun execute(workoutId: String): WorkoutDto {
        return httpClient
            .get("api/interval-timers/$workoutId")
            .body()
    }
}
