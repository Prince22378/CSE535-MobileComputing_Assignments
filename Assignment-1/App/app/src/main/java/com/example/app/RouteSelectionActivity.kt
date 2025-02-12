//////package com.example.app
//////
//////import android.content.Intent
//////import android.os.Bundle
//////import androidx.activity.ComponentActivity
//////import androidx.activity.compose.setContent
//////import androidx.compose.foundation.clickable
//////import androidx.compose.foundation.layout.*
//////import androidx.compose.foundation.lazy.LazyColumn
//////import androidx.compose.foundation.lazy.items
//////import androidx.compose.material3.*
//////import androidx.compose.runtime.*
//////import androidx.compose.ui.Alignment
//////import androidx.compose.ui.Modifier
//////import androidx.compose.ui.text.input.TextFieldValue
//////import androidx.compose.ui.unit.dp
//////
//////class RouteSelectionActivity : ComponentActivity() {
//////    private val allStops = listOf(
//////        Stop("New Delhi", true, 1000.0),
//////        Stop("Dubai", true, 2500.0),
//////        Stop("Frankfurt", true, 3000.0),
//////        Stop("London", true, 800.0)
//////    )
//////
//////    override fun onCreate(savedInstanceState: Bundle?) {
//////        super.onCreate(savedInstanceState)
//////
//////        setContent {
//////            RouteSelectionScreen { selectedStops ->
//////                val intent = Intent(this, MainActivity::class.java)
//////                intent.putParcelableArrayListExtra("SELECTED_STOPS", ArrayList(selectedStops))
//////                startActivity(intent)
//////                finish()
//////            }
//////        }
//////    }
//////}
//////
//////@Composable
//////fun RouteSelectionScreen(onRouteSelected: (List<Stop>) -> Unit) {
//////    var startLocation by remember { mutableStateOf(TextFieldValue("")) }
//////    var destination by remember { mutableStateOf(TextFieldValue("")) }
//////    var filteredRoutes by remember { mutableStateOf<List<List<Stop>>>(emptyList()) }
//////
//////    Column(
//////        modifier = Modifier.fillMaxSize().padding(16.dp),
//////        horizontalAlignment = Alignment.CenterHorizontally
//////    ) {
//////        Text(text = "Enter Journey Details", style = MaterialTheme.typography.headlineMedium)
//////
//////        Spacer(modifier = Modifier.height(10.dp))
//////
//////        OutlinedTextField(
//////            value = startLocation,
//////            onValueChange = { startLocation = it },
//////            label = { Text("Starting Location") },
//////            modifier = Modifier.fillMaxWidth()
//////        )
//////
//////        Spacer(modifier = Modifier.height(10.dp))
//////
//////        OutlinedTextField(
//////            value = destination,
//////            onValueChange = { destination = it },
//////            label = { Text("Destination Location") },
//////            modifier = Modifier.fillMaxWidth()
//////        )
//////
//////        Spacer(modifier = Modifier.height(20.dp))
//////
//////        Button(onClick = {
//////            filteredRoutes = generateRouteOptions(startLocation.text, destination.text)
//////        }) {
//////            Text("Find Routes")
//////        }
//////
//////        Spacer(modifier = Modifier.height(20.dp))
//////
//////        LazyColumn {
//////            items(filteredRoutes) { route ->
//////                RouteOptionItem(route) {
//////                    onRouteSelected(route)
//////                }
//////            }
//////        }
//////    }
//////}
//////
//////@Composable
//////fun RouteOptionItem(route: List<Stop>, onClick: () -> Unit) {
//////    Card(
//////        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() },
//////        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
//////    ) {
//////        Column(modifier = Modifier.padding(16.dp)) {
//////            Text(text = "Stops: ${route.size}", style = MaterialTheme.typography.titleMedium)
//////            Text(text = "Total Distance: ${route.sumOf { it.distanceFromPrevious }} Km")
//////        }
//////    }
//////}
//////
//////fun generateRouteOptions(start: String, end: String): List<List<Stop>> {
//////    val availableRoutes = listOf(
//////        listOf(Stop("New Delhi", true, 1000.0), Stop("London", true, 8000.0)),
//////        listOf(Stop("New Delhi", true, 1000.0), Stop("Dubai", true, 2500.0), Stop("London", true, 8000.0)),
//////        listOf(Stop("New Delhi", true, 1000.0), Stop("Dubai", true, 2500.0), Stop("Frankfurt", true, 3000.0), Stop("London", true, 8000.0))
//////    )
//////
//////    return availableRoutes.filter { it.first().name == start && it.last().name == end }
//////}
////
//////
//////package com.example.app
//////
//////import android.content.Intent
//////import android.os.Bundle
//////import androidx.activity.ComponentActivity
//////import androidx.activity.compose.setContent
//////import androidx.compose.foundation.clickable
//////import androidx.compose.foundation.layout.*
//////import androidx.compose.foundation.lazy.LazyColumn
//////import androidx.compose.foundation.lazy.items
//////import androidx.compose.material3.*
//////import androidx.compose.runtime.*
//////import androidx.compose.ui.Alignment
//////import androidx.compose.ui.Modifier
//////import androidx.compose.ui.text.input.TextFieldValue
//////import androidx.compose.ui.unit.dp
//////import java.io.BufferedReader
//////import java.io.InputStreamReader
//////
//////class RouteSelectionActivity : ComponentActivity() {
//////    private lateinit var availableRoutes: List<List<Stop>>
//////
//////    override fun onCreate(savedInstanceState: Bundle?) {
//////        super.onCreate(savedInstanceState)
//////
//////        // Load routes from file
//////        availableRoutes = loadRoutesFromFile()
//////
//////        setContent {
//////            RouteSelectionScreen(availableRoutes) { selectedStops ->
//////                val intent = Intent(this, MainActivity::class.java)
//////                intent.putParcelableArrayListExtra("SELECTED_STOPS", ArrayList(selectedStops))
//////                startActivity(intent)
//////                finish()
//////            }
//////        }
//////    }
//////
//////    private fun loadRoutesFromFile(): List<List<Stop>> {
//////        val routes = mutableListOf<List<Stop>>()
//////        val inputStream = resources.openRawResource(R.raw.stops)
//////        val reader = BufferedReader(InputStreamReader(inputStream))
//////
//////        reader.useLines { lines ->
//////            lines.forEach { line ->
//////                val stopsList = line.split("|").mapIndexed { index, stopData ->
//////                    val parts = stopData.trim().split(";")
//////                    if (parts.size == 3) {
//////                        Stop(
//////                            name = parts[0].trim(),
//////                            visaRequired = parts[1].trim().toBoolean(),
//////                            distanceFromPrevious = if (index == 0) 0.0 else parts[2].trim().toDoubleOrNull() ?: 0.0
//////                        )
//////                    } else {
//////                        null
//////                    }
//////                }.filterNotNull()
//////
//////                if (stopsList.isNotEmpty()) {
//////                    routes.add(stopsList)
//////                }
//////            }
//////        }
//////        return routes
//////    }
//////}
//////
//////@Composable
//////fun RouteSelectionScreen(availableRoutes: List<List<Stop>>, onRouteSelected: (List<Stop>) -> Unit) {
//////    var startLocation by remember { mutableStateOf(TextFieldValue("")) }
//////    var destination by remember { mutableStateOf(TextFieldValue("")) }
//////    var filteredRoutes by remember { mutableStateOf<List<List<Stop>>>(availableRoutes) }
//////
//////    Column(
//////        modifier = Modifier.fillMaxSize().padding(16.dp),
//////        horizontalAlignment = Alignment.CenterHorizontally
//////    ) {
//////        Text(text = "Enter Journey Details", style = MaterialTheme.typography.headlineMedium)
//////
//////        Spacer(modifier = Modifier.height(10.dp))
//////
//////        OutlinedTextField(
//////            value = startLocation,
//////            onValueChange = { startLocation = it },
//////            label = { Text("Starting Location") },
//////            modifier = Modifier.fillMaxWidth()
//////        )
//////
//////        Spacer(modifier = Modifier.height(10.dp))
//////
//////        OutlinedTextField(
//////            value = destination,
//////            onValueChange = { destination = it },
//////            label = { Text("Destination Location") },
//////            modifier = Modifier.fillMaxWidth()
//////        )
//////
//////        Spacer(modifier = Modifier.height(20.dp))
//////
//////        Button(onClick = {
//////            filteredRoutes = generateRouteOptions(startLocation.text, destination.text, availableRoutes)
//////        }) {
//////            Text("Find Routes")
//////        }
//////
//////        Spacer(modifier = Modifier.height(20.dp))
//////
//////        LazyColumn {
//////            items(filteredRoutes) { route ->
//////                RouteOptionItem(route) {
//////                    onRouteSelected(route)
//////                }
//////            }
//////        }
//////    }
//////}
//////
//////@Composable
//////fun RouteOptionItem(route: List<Stop>, onClick: () -> Unit) {
//////    Card(
//////        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() },
//////        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
//////    ) {
//////        Column(modifier = Modifier.padding(16.dp)) {
//////            Text(text = "Stops: ${route.joinToString(" → ") { it.name }}", style = MaterialTheme.typography.titleMedium)
//////            Text(text = "Total Distance: ${route.sumOf { it.distanceFromPrevious }} Km")
//////        }
//////    }
//////}
//////
//////fun generateRouteOptions(start: String, end: String, availableRoutes: List<List<Stop>>): List<List<Stop>> {
//////    return availableRoutes.filter { route ->
//////        route.first().name.equals(start, ignoreCase = true) && route.last().name.equals(end, ignoreCase = true)
//////    }
//////}
////
//////
//////package com.example.app
//////
//////import android.content.Intent
//////import android.os.Bundle
//////import androidx.activity.ComponentActivity
//////import androidx.activity.compose.setContent
//////import androidx.compose.foundation.clickable
//////import androidx.compose.foundation.layout.*
//////import androidx.compose.foundation.lazy.LazyColumn
//////import androidx.compose.foundation.lazy.items
//////import androidx.compose.material3.*
//////import androidx.compose.runtime.*
//////import androidx.compose.ui.Alignment
//////import androidx.compose.ui.Modifier
//////import androidx.compose.ui.text.input.TextFieldValue
//////import androidx.compose.ui.unit.dp
//////import java.io.BufferedReader
//////import java.io.InputStreamReader
//////
//////class RouteSelectionActivity : ComponentActivity() {
//////    private lateinit var availableRoutes: Map<Int, List<Stop>> // Store routes indexed by their index number
//////
//////    override fun onCreate(savedInstanceState: Bundle?) {
//////        super.onCreate(savedInstanceState)
//////
//////        // Load routes from file
//////        availableRoutes = loadRoutesFromFile()
//////
//////        setContent {
//////            RouteSelectionScreen(availableRoutes) { selectedStops ->
//////                val intent = Intent(this, MainActivity::class.java)
//////                intent.putParcelableArrayListExtra("SELECTED_STOPS", ArrayList(selectedStops))
//////                startActivity(intent)
//////                finish()
//////            }
//////        }
//////    }
//////
//////    private fun loadRoutesFromFile(): Map<Int, List<Stop>> {
//////        val routes = mutableMapOf<Int, List<Stop>>() // Store routes with index number
//////        val inputStream = resources.openRawResource(R.raw.stops)
//////        val reader = BufferedReader(InputStreamReader(inputStream))
//////
//////        reader.useLines { lines ->
//////            lines.forEach { line ->
//////                val parts = line.split("|").map { it.trim() }
//////
//////                if (parts.size > 2) {
//////                    val routeIndex = parts[0].toIntOrNull() ?: return@forEach // Extract index
//////
//////                    val stopsList = parts.drop(1).mapNotNull { stopData ->
//////                        val stopDetails = stopData.split(";")
//////                        if (stopDetails.size == 3) {
//////                            Stop(
//////                                name = stopDetails[0].trim(),
//////                                visaRequired = stopDetails[1].trim().toBoolean(),
//////                                distanceFromPrevious = stopDetails[2].trim().toDoubleOrNull() ?: 0.0
//////                            )
//////                        } else {
//////                            null
//////                        }
//////                    }
//////
//////                    if (stopsList.isNotEmpty()) {
//////                        routes[routeIndex] = stopsList
//////                    }
//////                }
//////            }
//////        }
//////        return routes
//////    }
//////}
//////
//////@Composable
//////fun RouteSelectionScreen(availableRoutes: Map<Int, List<Stop>>, onRouteSelected: (List<Stop>) -> Unit) {
//////    var startLocation by remember { mutableStateOf(TextFieldValue("")) }
//////    var destination by remember { mutableStateOf(TextFieldValue("")) }
//////    var filteredRoutes by remember { mutableStateOf<Map<Int, List<Stop>>>(emptyMap()) }
//////
//////    Column(
//////        modifier = Modifier.fillMaxSize().padding(16.dp),
//////        horizontalAlignment = Alignment.CenterHorizontally
//////    ) {
//////        Text(text = "Enter Journey Details", style = MaterialTheme.typography.headlineMedium)
//////
//////        Spacer(modifier = Modifier.height(10.dp))
//////
//////        OutlinedTextField(
//////            value = startLocation,
//////            onValueChange = { startLocation = it },
//////            label = { Text("Starting Location") },
//////            modifier = Modifier.fillMaxWidth()
//////        )
//////
//////        Spacer(modifier = Modifier.height(10.dp))
//////
//////        OutlinedTextField(
//////            value = destination,
//////            onValueChange = { destination = it },
//////            label = { Text("Destination Location") },
//////            modifier = Modifier.fillMaxWidth()
//////        )
//////
//////        Spacer(modifier = Modifier.height(20.dp))
//////
//////        Button(onClick = {
//////            filteredRoutes = filterRoutes(startLocation.text, destination.text, availableRoutes)
//////        }) {
//////            Text("Find Routes")
//////        }
//////
//////        Spacer(modifier = Modifier.height(20.dp))
//////
//////        LazyColumn {
//////            items(filteredRoutes.keys.toList()) { index ->
//////                RouteOptionItem(index, filteredRoutes[index]!!) {
//////                    onRouteSelected(filteredRoutes[index]!!)
//////                }
//////            }
//////        }
//////    }
//////}
//////
//////@Composable
//////fun RouteOptionItem(routeIndex: Int, route: List<Stop>, onClick: () -> Unit) {
//////    Card(
//////        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() },
//////        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
//////    ) {
//////        Column(modifier = Modifier.padding(16.dp)) {
//////            Text(text = "Route $routeIndex", style = MaterialTheme.typography.titleMedium)
//////            Text(text = "Stops: ${route.joinToString(" → ") { it.name }}")
//////            Text(text = "Total Distance: ${route.sumOf { it.distanceFromPrevious }} Km")
//////        }
//////    }
//////}
//////
//////fun filterRoutes(start: String, end: String, availableRoutes: Map<Int, List<Stop>>): Map<Int, List<Stop>> {
//////    return availableRoutes.filter { (_, routeStops) ->
//////        val firstStop = routeStops.first().name.equals(start, ignoreCase = true)
//////        val lastStop = routeStops.last().name.equals(end, ignoreCase = true)
//////        firstStop && lastStop
//////    }
//////}
////
////
////
//
//
//
//package com.example.app
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import java.io.BufferedReader
//import java.io.InputStreamReader
//
//class RouteSelectionActivity : ComponentActivity() {
//    private lateinit var availableRoutes: Map<Int, List<Stop>>
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Load routes from file
//        availableRoutes = loadRoutesFromFile()
//
//        setContent {
//            RouteSelectionScreen(availableRoutes) { selectedStops ->
//                val intent = Intent(this, MainActivity::class.java)
//                intent.putParcelableArrayListExtra("SELECTED_STOPS", ArrayList(selectedStops))
//                startActivity(intent)
//                finish()
//            }
//        }
//    }
//
//    private fun loadRoutesFromFile(): Map<Int, List<Stop>> {
//        val routes = mutableMapOf<Int, List<Stop>>()
//        val inputStream = resources.openRawResource(R.raw.stops)
//        val reader = BufferedReader(InputStreamReader(inputStream))
//
//        reader.useLines { lines ->
//            lines.forEach { line ->
//                val parts = line.split("|").map { it.trim() }
//
//                if (parts.size > 2) {
//                    val routeIndex = parts[0].toIntOrNull() ?: return@forEach
//
//                    val stopsList = parts.drop(1).mapNotNull { stopData ->
//                        val stopDetails = stopData.split(";")
//                        if (stopDetails.size == 3) {
//                            Stop(
//                                name = stopDetails[0].trim(),
//                                visaRequired = stopDetails[1].trim().toBoolean(),
//                                distanceFromPrevious = if (stopDetails[2].trim().isNotEmpty()) stopDetails[2].trim().toDoubleOrNull() ?: 0.0 else 0.0
//                            )
//                        } else {
//                            null
//                        }
//                    }
//
//                    if (stopsList.isNotEmpty()) {
//                        routes[routeIndex] = stopsList
//                    }
//                }
//            }
//        }
//        return routes
//    }
//}
//
//@Composable
//fun RouteSelectionScreen(availableRoutes: Map<Int, List<Stop>>, onRouteSelected: (List<Stop>) -> Unit) {
//    var startLocation by remember { mutableStateOf(TextFieldValue("")) }
//    var destination by remember { mutableStateOf(TextFieldValue("")) }
//    var filteredRoutes by remember { mutableStateOf<Map<Int, List<Stop>>>(emptyMap()) }
//    var routeNotFound by remember { mutableStateOf(false) } // Handle route not available case
//
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(text = "Enter Journey Details", style = MaterialTheme.typography.headlineMedium)
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        OutlinedTextField(
//            value = startLocation,
//            onValueChange = { startLocation = it },
//            label = { Text("Starting Location") },
//            modifier = Modifier.fillMaxWidth(),
////            singleLine = true,
//            keyboardOptions = KeyboardOptions(autoCorrect = false) // Disable suggestions
//        )
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        OutlinedTextField(
//            value = destination,
//            onValueChange = { destination = it },
//            label = { Text("Destination Location") },
//            modifier = Modifier.fillMaxWidth(),
////            singleLine = true,
//            keyboardOptions = KeyboardOptions(autoCorrect = false) // Disable suggestions
//        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        Button(
//            onClick = {
//            val routes = filterRoutes(startLocation.text, destination.text, availableRoutes)
//            filteredRoutes = routes
//            routeNotFound = routes.isEmpty() // Set flag if no route is found
//        }, modifier = Modifier.width(200.dp)
//        ) {
//            Text("Find Routes", fontSize = 18.sp)
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        if (routeNotFound) {
//            // Show "Route Not Available" message when no route is found
//            Text(
//                text = "Route Not Available",
//                color = MaterialTheme.colorScheme.error,
//                style = MaterialTheme.typography.titleMedium
//            )
//        } else {
//            LazyColumn {
//                items(filteredRoutes.keys.toList()) { index ->
//                    RouteOptionItem(index, filteredRoutes[index]!!) {
//                        onRouteSelected(filteredRoutes[index]!!)
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun RouteOptionItem(routeIndex: Int, route: List<Stop>, onClick: () -> Unit) {
//    Card(
//        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() },
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
////            Text(text = "Route $routeIndex", style = MaterialTheme.typography.titleMedium)
//            Text(text = "Stops: ${route.joinToString(" → ") { it.name }}")
//            Text(text = "Total Distance: ${route.sumOf { it.distanceFromPrevious }} Km")
//        }
//    }
//}
//
//fun filterRoutes(start: String, end: String, availableRoutes: Map<Int, List<Stop>>): Map<Int, List<Stop>> {
//    return availableRoutes.filter { (_, routeStops) ->
//        val firstStop = routeStops.first().name.equals(start, ignoreCase = true)
//        val lastStop = routeStops.last().name.equals(end, ignoreCase = true)
//        firstStop && lastStop
//    }
//}
//
//


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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
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
        modifier = Modifier.fillMaxWidth().padding(8.dp).clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Stops: ${route.joinToString(" → ") { it.name }}")
            Text(text = "Total Distance: ${route.sumOf { it.distanceFromPrevious }} Km")
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
