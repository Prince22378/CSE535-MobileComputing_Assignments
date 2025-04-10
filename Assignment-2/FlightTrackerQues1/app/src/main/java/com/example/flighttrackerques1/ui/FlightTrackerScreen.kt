package com.example.flighttrackerques1.ui


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Brightness7
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flighttrackerques1.viewmodel.FlightViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter




@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightTrackerScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val viewModel: FlightViewModel = viewModel()
    val flightData by viewModel.flightData.collectAsState()
    val isTracking by viewModel.isTracking.collectAsState()
    val lastUpdated by viewModel.lastUpdated.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    var flightNumber by remember { mutableStateOf("") }
    var searched by remember { mutableStateOf(false) }
//    var isLoading by remember { mutableStateOf(false) }

//    LaunchedEffect(flightData) {
//        if (flightData != null && isLoading) {
//            isLoading = false
//        }
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flight Tracker") },
                actions = {
                    IconButton(onClick = { onToggleTheme() }) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Filled.Brightness7 else Icons.Filled.Brightness4,
                            contentDescription = "Toggle Theme"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = flightNumber,
                onValueChange = { flightNumber = it },
                label = { Text("Flight Number") },
                placeholder = { Text("e.g. UA849") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                enabled = !isTracking // disable input while tracking
            )

            Spacer(modifier = Modifier.height(16.dp))

//            Button(onClick = {
//                if (isTracking) {
//                    viewModel.stopTracking()
////                    isLoading = false
//                } else {
//                    viewModel.trackFlight(flightNumber)
//                    searched = true
////                    isLoading = true
//                }
//            }) {
//                Text(if (isTracking) "Stop Tracking" else "Track Flight")
//            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    if (isTracking) {
                        viewModel.stopTracking()
//                        searched = false
//                        flightNumber = ""
                    } else {
//                        viewModel.clearState()
                        viewModel.trackFlight(flightNumber)
                        searched = true
                    }
                }) {
                    Text(if (isTracking) "Stop Tracking" else "Track Flight")
                }
            }


            if(errorMessage != null){
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Show time since last update
            if (isTracking && lastUpdated != null) {
                Text("🔄 Flight updates since $lastUpdated")
                Spacer(modifier = Modifier.height(12.dp))
            }

            flightData?.let { flight ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium,
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("✈️ Flight: ${flight.flight?.iata ?: "-"}", style = MaterialTheme.typography.titleMedium)
                        Text("🛫 Airline: ${flight.airline?.name ?: "-"}")
                        Text("   Date: ${flight.flight_date ?: "-"}")
                        Text("   Status: ${flight.flight_status ?: "-"}")

                        Spacer(Modifier.height(12.dp))
                        Text("🛫 Departure", style = MaterialTheme.typography.labelMedium)
                        Text("Airport: ${flight.departure?.airport ?: "-"} (${flight.departure?.iata})")
                        Text("Terminal: ${flight.departure?.terminal ?: "-"}   Gate: ${flight.departure?.gate ?: "-"}")
                        Text("Scheduled: ${formatDate(flight.departure?.scheduled)}")
                        Text("Actual: ${formatDate(flight.departure?.actual)}")
                        Text("Delay: ${flight.departure?.delay ?: 0} min")

                        Spacer(Modifier.height(12.dp))
                        Text("🛬 Arrival", style = MaterialTheme.typography.labelMedium)
                        Text("Airport: ${flight.arrival?.airport ?: "-"} (${flight.arrival?.iata})")
                        Text("Terminal: ${flight.arrival?.terminal ?: "-"}   Gate: ${flight.arrival?.gate ?: "-"}")
                        Text("Scheduled: ${formatDate(flight.arrival?.scheduled)}")
                        Text("Estimated: ${formatDate(flight.arrival?.estimated)}")
                        Text("Baggage: ${flight.arrival?.baggage ?: "-"}")

                        flight.flight?.codeshared?.let {
                            Spacer(Modifier.height(12.dp))
                            Text("🤝 Codeshare: ${it.airline_name} - ${it.flight_iata}")
                        }

                        Spacer(Modifier.height(12.dp))
                        flight.live?.let { live ->
                            Text("🛰 Live Data", style = MaterialTheme.typography.labelMedium)
                            Text("Location: ${live.latitude}, ${live.longitude}")
                            Text("Altitude: ${live.altitude} ft")
                            Text("Speed: ${live.speed_horizontal} km/h")
                            Text("Direction: ${live.direction}°")
                            Text("Grounded: ${live.is_ground}")
                        } ?: Text("No live tracking available.")
                    }
                }
            }
                ?: if (searched && isLoading) {
                Text("⏳ Tracking flight... Please wait.")
            } else if (searched && !isLoading && flightData == null) {
                Text("No flight data found.", color = MaterialTheme.colorScheme.error)
            } else {

            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(isoString: String?): String {
    return try {
        if (isoString == null) return "-"
        val parsed = ZonedDateTime.parse(isoString)
        parsed.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    } catch (e: Exception) {
        "-"
    }
}