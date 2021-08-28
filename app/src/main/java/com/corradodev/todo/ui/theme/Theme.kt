package com.corradodev.todo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Indigo500,
    primaryVariant = Indigo700,
    secondary = Pink200,
    onSecondary = Color.White
)

private val LightColorPalette = lightColors(
    primary = Indigo500,
    primaryVariant = Indigo700,
    secondary = Pink200,
    onSecondary = Color.White
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        content = content
    )
}
