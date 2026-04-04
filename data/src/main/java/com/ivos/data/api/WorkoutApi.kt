package com.ivos.data.api

import com.ivos.data.dto.IntervalDto
import com.ivos.data.dto.TimerDto
import com.ivos.data.dto.WorkoutDto
import io.ktor.client.HttpClient
import javax.inject.Inject

class WorkoutApi  @Inject constructor(
    private val httpClient: HttpClient
) {
    suspend fun execute(workoutId: String): WorkoutDto {
        /*return httpClient
            .get("api/interval-timers/$workoutId")
            .body() */
        return WorkoutDto(
            timer = TimerDto(
                timerId = 68,
                title = "Тренировка 7",
                totalTime = 900,
                intervals = listOf(
                    IntervalDto(
                        title = "Ходьба в среднем темпе",
                        time = 300,
                    ),
                    IntervalDto(
                        title = "Ходьба в интенсивном темпе",
                        time = 300,
                    ),
                    IntervalDto(
                        title = "Ходьба в среднем темпе",
                        time = 120,
                    ),
                    IntervalDto(
                        title = "Медленный бег",
                        time = 30,
                    ),
                )
            )
        )
    }
}
