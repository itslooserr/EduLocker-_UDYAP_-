package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

val UdyapInk = TextDark

private val UdyapColorScheme = lightColorScheme(
    primary = UdyapCrimson,
    onPrimary = PureWhite,
    primaryContainer = UdyapBlush,
    onPrimaryContainer = UdyapCrimsonDark,
    secondary = UdyapCrimsonDark,
    onSecondary = PureWhite,
    secondaryContainer = UdyapPeachSoft,
    onSecondaryContainer = UdyapInk,
    tertiary = UdyapIndigoCta,
    background = PureWhite,
    onBackground = TextDark,
    surface = PureWhite,
    onSurface = TextDark,
    surfaceVariant = UdyapPeachSoft,
    onSurfaceVariant = TextMuted,
    outline = HairlineDivider
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = Color.Transparent.toArgb()
                window.navigationBarColor = PureWhite.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = true
                controller.isAppearanceLightNavigationBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = UdyapColorScheme,
        typography = Typography,
        content = content
    )
}
