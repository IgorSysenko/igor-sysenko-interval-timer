package com.ivos.data.api

import com.ivos.data.di.KtorModule
import com.ivos.data.dto.WorkoutResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class WorkoutApi  @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun execute(workoutId: String): WorkoutResponseDto {
        return httpClient
            .get("api/interval-timers/$workoutId")
            .body()
    }

    companion object {
        suspend fun getExecute() = WorkoutApi(KtorModule.provideHttpClient()).execute("68")
    }
}
