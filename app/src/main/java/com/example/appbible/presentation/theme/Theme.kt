package com.example.appbible.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Light Color Scheme - Pergamino Dorado
private val LightColorScheme = lightColorScheme(
    primary = DoradoPrimario,
    onPrimary = Blanco,
    primaryContainer = DoradoClaro,
    onPrimaryContainer = DoradoOscuro,
    secondary = MarronClaro,
    onSecondary = Blanco,
    secondaryContainer = PergaminoClaro,
    onSecondaryContainer = MarronTexto,
    tertiary = Carmesi,
    onTertiary = Blanco,
    tertiaryContainer = Color(0xFFFFDAD6),
    onTertiaryContainer = Color(0xFF410002),
    error = Error,
    onError = Blanco,
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = PergaminoFondo,
    onBackground = MarronTexto,
    surface = PergaminoClaro,
    onSurface = MarronTexto,
    surfaceVariant = Color(0xFFE7E0EC),
    onSurfaceVariant = MarronClaro,
    outline = MarronClaro,
    outlineVariant = Color(0xFFCAC4D0),
    inverseSurface = MarronTexto,
    inverseOnSurface = PergaminoClaro,
    inversePrimary = DoradoClaro,
    surfaceTint = DoradoPrimario
)

// Dark Color Scheme - Pergamino Oscuro
private val DarkColorScheme = darkColorScheme(
    primary = DoradoSuave,
    onPrimary = MarronOscuro,
    primaryContainer = DoradoOscuro,
    onPrimaryContainer = DoradoSuave,
    secondary = TextoSecundario,
    onSecondary = MarronOscuro,
    secondaryContainer = PergaminoMedio,
    onSecondaryContainer = TextoClaro,
    tertiary = Color(0xFFFFB4AB),
    onTertiary = Color(0xFF690005),
    tertiaryContainer = Color(0xFF93000A),
    onTertiaryContainer = Color(0xFFFFDAD6),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = PergaminoOscuro,
    onBackground = TextoClaro,
    surface = PergaminoMedio,
    onSurface = TextoClaro,
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = TextoSecundario,
    outline = TextoSecundario,
    outlineVariant = Color(0xFF49454F),
    inverseSurface = TextoClaro,
    inverseOnSurface = MarronOscuro,
    inversePrimary = DoradoPrimario,
    surfaceTint = DoradoSuave
)

// Marron Oscuro para dark mode
val MarronOscuro = Color(0xFF1A0F0A)

@Composable
fun AppBibleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // No usar dynamic color para mantener paleta pergamino
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
