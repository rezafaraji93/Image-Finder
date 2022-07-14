package io.reza.paybackgroup.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import io.reza.core_ui.Dimensions
import io.reza.core_ui.LocalSpacing
import io.reza.core_ui.DarkBlue
import io.reza.core_ui.LightBlue
import io.reza.core_ui.LightPink

private val DarkColorPalette = darkColors(
    primary = LightPink,
    surface = DarkBlue,
    onSurface = LightBlue,
    background = Color.Black,
    onBackground = Color.White
)

private val LightColorPalette = lightColors(
    primary = LightPink,
    surface = LightBlue,
    onSurface = DarkBlue,
    background = Color.White,
    onBackground = DarkBlue
)


@Composable
fun PayBackGroupTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(LocalSpacing provides Dimensions()) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }

}