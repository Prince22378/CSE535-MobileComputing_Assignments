
//fine but next button disables after reaching final destination
package com.example.app

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Application(stops: List<Stop>) {
    var currentStopIndex by remember { mutableStateOf(0) }
    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
    val context = LocalContext.current

    val totalDistance = stops.sumOf { it.distanceFromPrevious }
    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
    val remainingDistance = totalDistance - coveredDistance

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Header with Back Arrow
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF0D47A1)), // Dark Blue Header
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Back Arrow
                Text(
                    text = "←",
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {
                            val intent = Intent(context, RouteSelectionActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            context.startActivity(intent)
                        }
                )

                Spacer(modifier = Modifier.weight(0.8f))

                // Title
                Text(
                    text = "Journey Progress",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                // Spacer to Keep Title Centered
                Spacer(modifier = Modifier.weight(1.2f))
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Distance Covered: ${convertDistance(coveredDistance, currentUnit)}",
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 12.dp)
            )

            LinearProgressIndicator(
                progress = { (coveredDistance / totalDistance).toFloat() },
                modifier = Modifier.fillMaxWidth().height(30.dp).padding(vertical = 12.dp),
            )

            Text(
                text = "Remaining Distance: ${convertDistance(remainingDistance, currentUnit)}",
                fontSize = 18.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Toggle KM <-> Miles Button
                Button(onClick = {
                    currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
                }, modifier = Modifier.width(145.dp) // Fixing button width
                ) {
                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "KM"}")
                }

                // Next Stop Button (Disabled if at Last Stop)
                Button(
                    onClick = {
                        if (currentStopIndex < stops.lastIndex) {
                            currentStopIndex++
                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
                                visibleStopCount++
                            }
                        }
                    }, modifier = Modifier.width(140.dp),
                    enabled = currentStopIndex < stops.lastIndex, // Disabled when at last stop
                    colors = if (currentStopIndex == stops.lastIndex)
                        ButtonDefaults.buttonColors(containerColor = Color.Gray) // Grey when disabled
                    else
                        ButtonDefaults.buttonColors()
                ) {
                    Text(text = "Next Stop")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            StopList(
                stops = stops.take(visibleStopCount),
                currentStopIndex = currentStopIndex,
                unit = currentUnit
            )
        }
    }
}

fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
    return if (unit == DistanceUnit.Miles) {
        "%.1f Miles".format(distanceKm * 0.621371)
    } else {
        "%.1f Km".format(distanceKm)
    }
}




// fine but next button dissapears after reaching final destination
//package com.example.app
//
//import android.content.Intent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun Application(stops: List<Stop>) {
//    var currentStopIndex by remember { mutableStateOf(0) }
//    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
//    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
//    val context = LocalContext.current
//
//    val totalDistance = stops.sumOf { it.distanceFromPrevious }
//    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
//    val remainingDistance = totalDistance - coveredDistance
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        // Header with Back Arrow
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp)
//                .background(Color(0xFF0D47A1)), // Dark Blue Header
//        ) {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // Back Arrow
//                Text(
//                    text = "←",
//                    fontSize = 28.sp,
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier
//                        .padding(start = 16.dp)
//                        .clickable {
//                            val intent = Intent(context, RouteSelectionActivity::class.java)
//                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//                            context.startActivity(intent)
//                        }
//                )
//
//                Spacer(modifier = Modifier.weight(0.8f))
//
//                // Title
//                Text(
//                    text = "Journey Progress",
//                    fontSize = 20.sp,
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.align(Alignment.CenterVertically)
//                )
//                // Spacer to Keep Title Centered
//                Spacer(modifier = Modifier.weight(1.2f))
//            }
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Column(
//            modifier = Modifier.fillMaxSize().padding(20.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Distance Covered: ${convertDistance(coveredDistance, currentUnit)}",
//                fontSize = 24.sp,
//                modifier = Modifier.padding(top = 12.dp)
//            )
//
//            LinearProgressIndicator(
//                progress = { (coveredDistance / totalDistance).toFloat() },
//                modifier = Modifier.fillMaxWidth().height(30.dp).padding(vertical = 12.dp),
//            )
//
//            Text(
//                text = "Remaining Distance: ${convertDistance(remainingDistance, currentUnit)}",
//                fontSize = 18.sp,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                Button(onClick = {
//                    currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
//                }) {
//                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
//                }
//
//                if (currentStopIndex < stops.lastIndex) {
//                    Button(onClick = {
//                        currentStopIndex++
//                        if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
//                            visibleStopCount++
//                        }
//                    }) {
//                        Text(text = "Next Stop")
//                    }
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            StopList(
//                stops = stops.take(visibleStopCount),
//                currentStopIndex = currentStopIndex,
//                unit = currentUnit
//            )
//        }
//    }
//}
//
//fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
//    return if (unit == DistanceUnit.Miles) {
//        "%.1f Miles".format(distanceKm * 0.621371)
//    } else {
//        "%.1f Km".format(distanceKm)
//    }
//}


//fine but next button changes to finish Journey button after reaching final destination and works as back button
//package com.example.app
//
//import android.content.Intent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//@Composable
//fun Application(stops: List<Stop>) {
//    var currentStopIndex by remember { mutableStateOf(0) }
//    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
//    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
//    val context = LocalContext.current
//
//    val totalDistance = stops.sumOf { it.distanceFromPrevious }
//    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
//    val remainingDistance = totalDistance - coveredDistance
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        // Header with Back Arrow
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp)
//                .background(Color(0xFF0D47A1)), // Dark Blue Header
//        ) {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // Back Arrow
//                Text(
//                    text = "←",
//                    fontSize = 28.sp,
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier
//                        .padding(start = 16.dp)
//                        .clickable {
//                            val intent = Intent(context, RouteSelectionActivity::class.java)
//                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//                            context.startActivity(intent)
//                        }
//                )
//
//                Spacer(modifier = Modifier.weight(0.8f))
//
//                // Title
//                Text(
//                    text = "Journey Progress",
//                    fontSize = 20.sp,
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.align(Alignment.CenterVertically)
//                )
//                // Spacer to Keep Title Centered
//                Spacer(modifier = Modifier.weight(1.2f))
//            }
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        Column(
//            modifier = Modifier.fillMaxSize().padding(20.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Distance Covered: ${convertDistance(coveredDistance, currentUnit)}",
//                fontSize = 24.sp,
//                modifier = Modifier.padding(top = 12.dp)
//            )
//
//            LinearProgressIndicator(
//                progress = { (coveredDistance / totalDistance).toFloat() },
//                modifier = Modifier.fillMaxWidth().height(30.dp).padding(vertical = 12.dp),
//            )
//
//            Text(
//                text = "Remaining Distance: ${convertDistance(remainingDistance, currentUnit)}",
//                fontSize = 18.sp,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                // Toggle KM <-> Miles Button
//                Button(onClick = {
//                    currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
//                }) {
//                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
//                }
//
//                // Next Stop / Finish Journey Button
//                Button(
//                    onClick = {
//                        if (currentStopIndex < stops.lastIndex) {
//                            currentStopIndex++
//                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
//                                visibleStopCount++
//                            }
//                        } else {
//                            // If last stop is reached, navigate back to RouteSelectionActivity
//                            val intent = Intent(context, RouteSelectionActivity::class.java)
//                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//                            context.startActivity(intent)
//                        }
//                    },
//                    colors = if (currentStopIndex == stops.lastIndex) ButtonDefaults.buttonColors(containerColor = Color.Red) else ButtonDefaults.buttonColors()
//                ) {
//                    Text(text = if (currentStopIndex == stops.lastIndex) "Finish Journey" else "Next Stop", color = if (currentStopIndex == stops.lastIndex) Color.White else Color.Black)
//                }
//            }
//
//            Spacer(modifier = Modifier.height(20.dp))
//
//            StopList(
//                stops = stops.take(visibleStopCount),
//                currentStopIndex = currentStopIndex,
//                unit = currentUnit
//            )
//        }
//    }
//}
//
//fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
//    return if (unit == DistanceUnit.Miles) {
//        "%.1f Miles".format(distanceKm * 0.621371)
//    } else {
//        "%.1f Km".format(distanceKm)
//    }
//}
