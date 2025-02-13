package com.example.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.io.BufferedReader
import java.io.InputStreamReader

class RouteSelectionActivity : ComponentActivity() {
    private lateinit var availableRoutes: Map<Int, List<Stop>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load routes from file
        availableRoutes = loadRoutesFromFile()

        setContent {
            RouteSelectionScreen(availableRoutes) { selectedStops ->
                val intent = Intent(this, MainActivity::class.java)
                intent.putParcelableArrayListExtra("SELECTED_STOPS", ArrayList(selectedStops))
                startActivity(intent)
                finish()
            }
        }
    }

    private fun loadRoutesFromFile(): Map<Int, List<Stop>> {
        val routes = mutableMapOf<Int, List<Stop>>()
        val inputStream = resources.openRawResource(R.raw.stops)
        val reader = BufferedReader(InputStreamReader(inputStream))

        reader.useLines { lines ->
            lines.forEach { line ->
                val parts = line.split("|").map { it.trim() }

                if (parts.size > 2) {
                    val routeIndex = parts[0].toIntOrNull() ?: return@forEach

                    val stopsList = parts.drop(1).mapNotNull { stopData ->
                        val stopDetails = stopData.split(";")
                        if (stopDetails.size == 3) {
                            Stop(
                                name = stopDetails[0].trim(),
                                visaRequired = stopDetails[1].trim().toBoolean(),
                                distanceFromPrevious = if (stopDetails[2].trim().isNotEmpty()) stopDetails[2].trim().toDoubleOrNull() ?: 0.0 else 0.0
                            )
                        } else {
                            null
                        }
                    }

                    if (stopsList.isNotEmpty()) {
                        routes[routeIndex] = stopsList
                    }
                }
            }
        }
        return routes
    }
}

@Composable
fun RouteSelectionScreen(availableRoutes: Map<Int, List<Stop>>, onRouteSelected: (List<Stop>) -> Unit) {
    var startLocation by remember { mutableStateOf(TextFieldValue("")) }
    var destination by remember { mutableStateOf(TextFieldValue("")) }
    var filteredRoutes by remember { mutableStateOf<Map<Int, List<Stop>>>(emptyMap()) }
    var routeNotFound by remember { mutableStateOf(false) } // Handle route not available case

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Dark Blue Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF0D47A1)), // Dark Blue Header
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Route Selection",
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Enter Journey Details", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = startLocation,
                onValueChange = { startLocation = it },
                label = { Text("Starting Location") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(autoCorrect = false) // Disable suggestions
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = destination,
                onValueChange = { destination = it },
                label = { Text("Destination Location") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(autoCorrect = false) // Disable suggestions
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                val routes = filterRoutes(startLocation.text, destination.text, availableRoutes)
                filteredRoutes = routes
                routeNotFound = routes.isEmpty() // Set flag if no route is found
            }, modifier = Modifier.width(200.dp).height(55.dp)
            ) {
                Text("Find Routes", fontSize = 20.sp)
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (routeNotFound) {
                // Show "Route Not Available" message when no route is found
                Text(
                    text = "Route Not Available",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                LazyColumn {
                    items(filteredRoutes.keys.toList()) { index ->
                        RouteOptionItem(index, filteredRoutes[index]!!) {
                            onRouteSelected(filteredRoutes[index]!!)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RouteOptionItem(routeIndex: Int, route: List<Stop>, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
            .shadow(6.dp, shape = RoundedCornerShape(12.dp)), // **Improved Shadows & Rounded Corners**
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)) // **Light Blue Background for Visibility**
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Stops Label (Normal) + Stops List (Bold)
            Text(
                text = buildAnnotatedString {
                    append("Stops: ") // Normal Text
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(route.joinToString(" → ") { it.name }) // Bold Stops
                    }
                },
                fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Total Distance: ${route.sumOf { it.distanceFromPrevious }} Km",
                fontSize = 16.sp,
                color = Color.DarkGray
            )
        }
    }
}


fun filterRoutes(start: String, end: String, availableRoutes: Map<Int, List<Stop>>): Map<Int, List<Stop>> {
    return availableRoutes.filter { (_, routeStops) ->
        val firstStop = routeStops.first().name.equals(start, ignoreCase = true)
        val lastStop = routeStops.last().name.equals(end, ignoreCase = true)
        firstStop && lastStop
    }
}
