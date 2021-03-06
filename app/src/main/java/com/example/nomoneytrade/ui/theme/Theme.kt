package com.example.nomoneytrade.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = PrimaryDark,
    primaryVariant = Purple700,
    secondary = Color.White,
    onPrimary = OnPrimaryDark,
    surface = SurfaceDark,
    onSurface = AccentDark,
)

private val LightColorPalette = lightColors(
    primary = PrimaryLight,
    primaryVariant = Purple700,
    secondary = Color.Black,
    onPrimary = OnPrimaryLight,
    surface = SurfaceLight,
    onSurface = AccentLight

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}