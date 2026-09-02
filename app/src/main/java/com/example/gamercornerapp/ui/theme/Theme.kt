package com.example.gamercornerapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

// Local para que los componentes sepan si estamos en modo oscuro de forma interna
val LocalIsDarkTheme = staticCompositionLocalOf { false }


private val DarkColorScheme = darkColorScheme(

    primary = BrandPrimary,
    onPrimary = DarkTextPrimary,

    secondary = BrandPurple,
    onSecondary = DarkTextPrimary,

    tertiary = BrandYellow,
    onTertiary = DarkBackground,

    background = DarkBackground,
    onBackground = DarkTextPrimary,

    surface = DarkSurface,
    onSurface = DarkTextPrimary,

    surfaceVariant = DarkSurface,
    onSurfaceVariant = DarkTextSecondary,

    outline = DarkTextSecondary
)


private val LightColorScheme = lightColorScheme(

    primary = BrandPrimary,
    onPrimary = LightSurface,

    secondary = BrandPurple,
    onSecondary = LightSurface,

    tertiary = BrandYellow,
    onTertiary = LightTextPrimary,

    background = LightBackground,
    onBackground = LightTextPrimary,

    surface = LightSurface,
    onSurface = LightTextPrimary,

    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,

    outline = LightTextSecondary
)


@Composable
fun GamerCornerAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }


    CompositionLocalProvider(LocalIsDarkTheme provides darkTheme) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}