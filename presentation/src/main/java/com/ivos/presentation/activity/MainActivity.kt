package com.ivos.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ivos.presentation.navigation.AppNavHost
import com.ivos.presentation.theme.IgorSysenkoIntervalTimerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            IgorSysenkoIntervalTimerTheme {
                ScreenContainer {
                    AppNavHost()
                }
            }
        }
    }
}
