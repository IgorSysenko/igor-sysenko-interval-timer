package com.ivos.data.mapper

import com.ivos.data.dto.TimerDto
import com.ivos.data.dto.WorkoutDto
import com.ivos.domain.model.Interval
import com.ivos.domain.model.Timer
import com.ivos.domain.model.Workout

fun WorkoutDto.toDomainModel() = Workout(
    timer = timer.toDomainModel(),
)

private fun TimerDto.toDomainModel() = Timer(
    timerId = timerId,
    title = title,
    totalTime = totalTime,
    intervals = (intervals + intervals + intervals + intervals + intervals).map {
        Interval(
            title = it.title,
            time = it.time
        )
    }
)
