package com.ivos.domain.sound_manager

interface SoundManager {
    fun playStartSound()
    fun playNextIntervalSound()
    fun playFinishSound()
    fun release()
}
