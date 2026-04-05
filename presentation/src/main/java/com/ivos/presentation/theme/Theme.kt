package com.ivos.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val LightColors = lightColorScheme(
    background = Color(0xFFF5F5F7),
    surface = Color(0xFFFFFFFF),

    primary = Color(0xFF1B9E5A),
    secondary = Color(0xFF3B82F6),
    error = Color(0xFFDC3545),

    onBackground = Color(0xFF1A1D24),
    onSurface = Color(0xFF1A1D24),
    onPrimary = Color.White,

    outline = Color(0x14000000),
)

val AppTypography = Typography(
    // Timer
    displayLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 68.sp,
        lineHeight = 68.sp
    ),

    // H1
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        lineHeight = 32.sp
    ),

    // Title
    titleMedium = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 21.sp
    ),

    // Body
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp
    ),

    // Label
    labelLarge = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),

    // Caption
    bodySmall = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 13.sp,
        lineHeight = 18.sp
    ),
)

data class ExtraTypography(
    val state: TextStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        letterSpacing = 1.5.sp
    ),
    val button: TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 15.sp
    ),
    val mono: TextStyle = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    )
)

data class ExtraColors(
    val primaryLight: Color = Color(0x141B9E5A),
    val orange: Color = Color(0xFFE67E22),
    val textSecondary: Color = Color(0xFF5F6776),
    val textTertiary: Color = Color(0xFF939BAA),
    val disabledBg: Color = Color(0xFFEDEDEF),
    val disabledText: Color = Color(0xFFB0B5BF),
)

data class AppShapes(
    val small: Dp = 12.dp,
    val medium: Dp = 16.dp,
    val round: Dp = 100.dp,
)

data class AppSpacing(
    val xs: Dp = 4.dp,
    val s: Dp = 8.dp,
    val m: Dp = 12.dp,
    val l: Dp = 16.dp,
    val xl: Dp = 20.dp,
    val xxl: Dp = 24.dp,
)

val LocalSpacing = staticCompositionLocalOf { AppSpacing() }
val LocalShapes = staticCompositionLocalOf { AppShapes() }
val LocalExtraColors = staticCompositionLocalOf { ExtraColors() }
val LocalExtraTypography = staticCompositionLocalOf { ExtraTypography() }

@Composable
fun IgorSysenkoIntervalTimerTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalSpacing provides AppSpacing(),
        LocalShapes provides AppShapes(),
        LocalExtraColors provides ExtraColors(),
        LocalExtraTypography provides ExtraTypography()
    )  {
        MaterialTheme(
            colorScheme = LightColors,
            typography = AppTypography,
            content = content
        )
    }
}
