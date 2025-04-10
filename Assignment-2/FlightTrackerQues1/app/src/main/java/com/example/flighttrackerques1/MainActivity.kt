package com.example.flighttrackerques1


import android.os.Build
import com.example.flighttrackerques1.ui.FlightTrackerScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.example.flighttrackerques1.ui.theme.FlightTrackerTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val isSystemDark = isSystemInDarkTheme()
            var isDarkTheme by remember { mutableStateOf(isSystemDark) }

            FlightTrackerTheme(darkTheme = isDarkTheme) {
                Surface {
                    FlightTrackerScreen(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { isDarkTheme = !isDarkTheme }
                    )
                }
            }
        }
    }
}