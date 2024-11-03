package pl.anatorini.grimoire.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2C302E),
    secondary = Color(0xFF9AE19D),
    tertiary = Color(0xFF537A5A),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFF2C302E),
    onTertiary = Color(0xFF000000),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    error = Color(0xFF800020),
    onError = Color(0xFFFFFFFF),
)

private val LightColorScheme = lightColorScheme(
    primary = Color.Red,
    secondary = Color.Red,
    tertiary = Color.Red,
    background = Color.Red,
    surface = Color.Red,
    onPrimary = Color.Red,
    onSecondary = Color.Red,
    onTertiary = Color.Red,
    onBackground = Color.Red,
    onSurface = Color.Red,
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}