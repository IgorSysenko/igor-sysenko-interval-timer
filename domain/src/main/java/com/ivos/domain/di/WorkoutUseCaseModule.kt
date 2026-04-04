package com.ivos.domain.di

import com.ivos.domain.repo.WorkoutRepo
import com.ivos.domain.usecase.GetWorkoutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@[Module InstallIn(ViewModelComponent::class)]
interface WorkoutUseCaseModule {

    @Provides
    fun provideWorkoutUseCase(
        workoutRepo: WorkoutRepo,
    ) = GetWorkoutUseCase(workoutRepo)
}
