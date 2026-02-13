package com.velotravel.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Premium Apple-style colors
private val AppleWhite = Color(0xFFFFFFFF)
private val AppleGray = Color(0xFFF5F5F7)
private val AppleLightGray = Color(0xFFFBFBFD)
private val AppleDarkGray = Color(0xFF1D1D1F)
private val AppleTextPrimary = Color(0xFF1D1D1F)
private val AppleTextSecondary = Color(0xFF86868B)
private val AppleBlue = Color(0xFF007AFF)
private val AppleGreen = Color(0xFF34C759)
private val AppleOrange = Color(0xFFFF9500)
private val ApplePurple = Color(0xFFAF52DE)
private val AppleRed = Color(0xFFFF3B30)

private val LightColorScheme = lightColorScheme(
    primary = AppleBlue,
    onPrimary = Color.White,
    primaryContainer = AppleBlue.copy(alpha = 0.1f),
    onPrimaryContainer = AppleBlue,
    
    secondary = AppleTextSecondary,
    onSecondary = Color.White,
    secondaryContainer = AppleGray,
    onSecondaryContainer = AppleTextPrimary,
    
    tertiary = AppleGreen,
    onTertiary = Color.White,
    tertiaryContainer = AppleGreen.copy(alpha = 0.1f),
    onTertiaryContainer = AppleGreen,
    
    background = AppleWhite,
    onBackground = AppleTextPrimary,
    
    surface = AppleWhite,
    onSurface = AppleTextPrimary,
    surfaceVariant = AppleGray,
    onSurfaceVariant = AppleTextSecondary,
    
    outline = AppleGray,
    outlineVariant = AppleLightGray,
    
    error = AppleRed,
    onError = Color.White
)

private val DarkColorScheme = darkColorScheme(
    primary = AppleBlue,
    onPrimary = Color.White,
    primaryContainer = AppleBlue.copy(alpha = 0.2f),
    onPrimaryContainer = AppleBlue,
    
    secondary = AppleTextSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFF2C2C2E),
    onSecondaryContainer = Color(0xFFE5E5EA),
    
    tertiary = AppleGreen,
    onTertiary = Color.White,
    tertiaryContainer = AppleGreen.copy(alpha = 0.2f),
    onTertiaryContainer = AppleGreen,
    
    background = AppleDarkGray,
    onBackground = Color(0xFFE5E5EA),
    
    surface = AppleDarkGray,
    onSurface = Color(0xFFE5E5EA),
    surfaceVariant = Color(0xFF2C2C2E),
    onSurfaceVariant = AppleTextSecondary,
    
    outline = Color(0xFF3A3A3C),
    outlineVariant = Color(0xFF2C2C2E),
    
    error = AppleRed,
    onError = Color.White
)

@Composable
fun VeloTravelTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        content = content
    )
}
