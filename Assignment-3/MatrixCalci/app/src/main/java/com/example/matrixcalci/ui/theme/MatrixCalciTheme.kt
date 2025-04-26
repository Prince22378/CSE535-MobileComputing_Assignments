//package com.example.matrixcalci.ui.theme
//
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.darkColorScheme
//import androidx.compose.material3.lightColorScheme
//import androidx.compose.runtime.Composable
//
//private val LightColors = lightColorScheme()
//private val DarkColors  = darkColorScheme()
//
//@Composable
//fun MatrixCalciTheme(
//    darkTheme: Boolean = false,
//    content: @Composable () -> Unit
//) {
//    MaterialTheme(
//        colorScheme = if (darkTheme) DarkColors else LightColors
//    ) {
//        content()
//    }
//}


// app/src/main/java/.../ui/theme/MatrixCalciTheme.kt
package com.example.matrixcalci.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun MatrixCalciTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    // Use Material3's default light/dark schemes
    val colors = if (darkTheme) darkColorScheme() else lightColorScheme()

    MaterialTheme(
        colorScheme = colors,
        typography = MaterialTheme.typography,
        content = content
    )
}
