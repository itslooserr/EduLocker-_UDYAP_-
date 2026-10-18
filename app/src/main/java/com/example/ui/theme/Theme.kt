package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val EduLockerColorScheme = lightColorScheme(
    primary = OdishaMagenta,
    onPrimary = PureWhite,
    primaryContainer = SoftBlushPink,
    onPrimaryContainer = OdishaMagentaDark,
    secondary = OdishaMagentaDark,
    onSecondary = PureWhite,
    secondaryContainer = LightBlushPink,
    onSecondaryContainer = OdishaMagentaDeep,
    tertiary = OdishaMagentaDeep,
    background = CanvasCream,
    onBackground = TextDark,
    surface = PureWhite,
    onSurface = TextDark,
    surfaceVariant = SoftBlushPink,
    onSurfaceVariant = TextDark,
    outline = DarkBorder
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
                window.statusBarColor = CanvasCream.toArgb()
                window.navigationBarColor = CanvasCream.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = true
            }
        }
    }

    MaterialTheme(
        colorScheme = EduLockerColorScheme,
        typography = Typography,
        content = content
    )
}
