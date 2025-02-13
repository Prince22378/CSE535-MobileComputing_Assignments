package com.example.app

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Delay for 3 seconds before navigating to RouteSelectionActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, RouteSelectionActivity::class.java)
            startActivity(intent)
            finish() // Close SplashActivity so it doesn't stay in the back stack
        }, 3000)

        // Set the UI content for Splash Screen
        setContent {
            SplashScreen()
        }
    }
}

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0D47A1) // Deep Blue Background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo centered
            Image(
                painter = painterResource(id = R.drawable.logo), // Replace with your actual logo
                contentDescription = "App Logo",
                modifier = Modifier
                    .size(350.dp) // Adjust logo size if needed
            )

//            Spacer(modifier = Modifier.height(24.dp))

            // Welcome message
            Text(
                text = "Welcome to DistTrack!",
                fontSize = 28.sp,
                color = Color(0xFF80C784)
            )

//            Spacer(modifier = Modifier.height(8.dp))

//            // Subtext message
//            Text(
//                text = "Plan your stops effortlessly!",
//                fontSize = 16.sp,
//                color = Color.White
//            )
        }
    }
}
