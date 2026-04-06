package com.ivos.data.sound_manager

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.provider.Settings
import com.ivos.domain.sound_manager.SoundManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManagerImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
) : SoundManager {

    override fun playBeep() {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build()
            )
            setDataSource(context, Settings.System.DEFAULT_NOTIFICATION_URI)
            prepare()
            start()
            setOnCompletionListener { release() }
        }
    }
}
