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
    onTertiary = Color(0xFFFDFDFD),
    onBackground = Color(0xFF000000),
    onSurface = Color(0xFF000000),
    error = Color(0xFF800020),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFF800020),
    onErrorContainer = Color.White,
    primaryContainer = Color(0xFF537A5A),
    scrim = Color(0xFFA5A5A5)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF000000),
    primaryContainer = Color(0xFF000000),
    onPrimary = Color.White,
    onPrimaryContainer = Color.White,
    secondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFFFFF),
    onSecondary = Color.Black,
    onSecondaryContainer = Color.Black,
    tertiary = Color(0xFF282828),
    tertiaryContainer = Color(0xFFFFFFFF),
    onTertiary = Color.Black,
    onTertiaryContainer = Color.Black,
    background = Color(0xFFFFFFFF),
    onBackground = Color(0xFF000000),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF000000),
    error = Color(0xFFee0231),
    errorContainer = Color(0xFFf49399),
    onError = Color.White,
    onErrorContainer = Color.Black,
    scrim = Color(0xFF5DD13D)
)


/*


{
  "Primary": {
    "50": "#ece7fd",
    "100": "#cec4fb",
    "200": "#ab9cf9",
    "300": "#8473f8",
    "400": "#6154f7",
    "500": "#2c36f4",
    "600": "#0232ee",
    "700": "#002be5",
    "800": "#0026e0",
    "900": "#001ad9"
  },
  "Complementary": {
    "50": "#fcf6df",
    "100": "#f9e8ad",
    "200": "#f4d978",
    "300": "#f0cb40",
    "400": "#eebf02",
    "500": "#ecb400",
    "600": "#eca600",
    "700": "#ed9400",
    "800": "#ed8300",
    "900": "#ee6200"
  },
  "Analogous - 1": {
    "50": "#e1f5fd",
    "100": "#b3e4fa",
    "200": "#81d3f6",
    "300": "#4fc1f2",
    "400": "#2ab4f0",
    "500": "#02a7ee",
    "600": "#0099df",
    "700": "#0086cb",
    "800": "#0075b7",
    "900": "#005695"
  },
  "Analogous - 2": {
    "50": "#efe5fe",
    "100": "#d4bffb",
    "200": "#b694f9",
    "300": "#9566f8",
    "400": "#7940f7",
    "500": "#5808f4",
    "600": "#4902ee",
    "700": "#2e00e6",
    "800": "#0000e1",
    "900": "#0000dd"
  },
  "Triadic - 1": {
    "50": "#f7e3fc",
    "100": "#e9b9f8",
    "200": "#db89f4",
    "300": "#cb51f1",
    "400": "#bf02ee",
    "500": "#b100e9",
    "600": "#9e00e4",
    "700": "#8300df",
    "800": "#6a00dc",
    "900": "#2400d1"
  },
  "Triadic - 2": {
    "50": "#ffeaee",
    "100": "#ffc9d2",
    "200": "#f49399",
    "300": "#ec6771",
    "400": "#f73d4e",
    "500": "#fd1a32",
    "600": "#ee0231",
    "700": "#dc002b",
    "800": "#cf0024",
    "900": "#c10016"
  }
}
 */

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}