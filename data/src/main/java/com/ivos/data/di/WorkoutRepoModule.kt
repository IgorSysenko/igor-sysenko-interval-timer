package com.ivos.data.di

import com.ivos.data.repo.WorkoutRepoImpl
import com.ivos.domain.repo.WorkoutRepo
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface WorkoutRepoModule {
    @Binds
    fun bindWorkoutRepo(repo: WorkoutRepoImpl): WorkoutRepo
}

