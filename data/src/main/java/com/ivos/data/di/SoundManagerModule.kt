package com.ivos.data.di

import com.ivos.data.sound_manager.SoundManagerImpl
import com.ivos.domain.sound_manager.SoundManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@[Module InstallIn(SingletonComponent::class)]
interface SoundManagerModule {
    @Binds
    fun bindSoundManager(repo: SoundManagerImpl): SoundManager
}
