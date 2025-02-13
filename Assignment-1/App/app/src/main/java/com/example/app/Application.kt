//////////////////// fine but next button dissapears after reaching final destination
////////////////////package com.example.app
////////////////////
////////////////////import android.content.Intent
////////////////////import androidx.compose.foundation.background
////////////////////import androidx.compose.foundation.clickable
////////////////////import androidx.compose.foundation.layout.*
////////////////////import androidx.compose.material3.*
////////////////////import androidx.compose.runtime.*
////////////////////import androidx.compose.ui.Alignment
////////////////////import androidx.compose.ui.Modifier
////////////////////import androidx.compose.ui.graphics.Color
////////////////////import androidx.compose.ui.platform.LocalContext
////////////////////import androidx.compose.ui.text.font.FontWeight
////////////////////import androidx.compose.ui.unit.dp
////////////////////import androidx.compose.ui.unit.sp
////////////////////
////////////////////@Composable
////////////////////fun Application(stops: List<Stop>) {
////////////////////    var currentStopIndex by remember { mutableStateOf(0) }
////////////////////    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
////////////////////    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
////////////////////    val context = LocalContext.current
////////////////////
////////////////////    val totalDistance = stops.sumOf { it.distanceFromPrevious }
////////////////////    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
////////////////////    val remainingDistance = totalDistance - coveredDistance
////////////////////
////////////////////    Column(
////////////////////        modifier = Modifier.fillMaxSize()
////////////////////    ) {
////////////////////        // Header with Back Arrow
////////////////////        Box(
////////////////////            modifier = Modifier
////////////////////                .fillMaxWidth()
////////////////////                .height(60.dp)
////////////////////                .background(Color(0xFF0D47A1)), // Dark Blue Header
////////////////////        ) {
////////////////////            Row(
////////////////////                modifier = Modifier.fillMaxSize(),
////////////////////                verticalAlignment = Alignment.CenterVertically
////////////////////            ) {
////////////////////                // Back Arrow
////////////////////                Text(
////////////////////                    text = "←",
////////////////////                    fontSize = 28.sp,
////////////////////                    color = Color.White,
////////////////////                    fontWeight = FontWeight.Bold,
////////////////////                    modifier = Modifier
////////////////////                        .padding(start = 16.dp)
////////////////////                        .clickable {
////////////////////                            val intent = Intent(context, RouteSelectionActivity::class.java)
////////////////////                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
////////////////////                            context.startActivity(intent)
////////////////////                        }
////////////////////                )
////////////////////
////////////////////                Spacer(modifier = Modifier.weight(0.8f))
////////////////////
////////////////////                // Title
////////////////////                Text(
////////////////////                    text = "Journey Progress",
////////////////////                    fontSize = 20.sp,
////////////////////                    color = Color.White,
////////////////////                    fontWeight = FontWeight.Bold,
////////////////////                    modifier = Modifier.align(Alignment.CenterVertically)
////////////////////                )
////////////////////                // Spacer to Keep Title Centered
////////////////////                Spacer(modifier = Modifier.weight(1.2f))
////////////////////            }
////////////////////        }
////////////////////
////////////////////        Spacer(modifier = Modifier.height(10.dp))
////////////////////
////////////////////        Column(
////////////////////            modifier = Modifier.fillMaxSize().padding(20.dp),
////////////////////            horizontalAlignment = Alignment.CenterHorizontally
////////////////////        ) {
////////////////////            Text(
////////////////////                text = "Distance Covered: ${convertDistance(coveredDistance, currentUnit)}",
////////////////////                fontSize = 24.sp,
////////////////////                modifier = Modifier.padding(top = 12.dp)
////////////////////            )
////////////////////
////////////////////            LinearProgressIndicator(
////////////////////                progress = { (coveredDistance / totalDistance).toFloat() },
////////////////////                modifier = Modifier.fillMaxWidth().height(30.dp).padding(vertical = 12.dp),
////////////////////            )
////////////////////
////////////////////            Text(
////////////////////                text = "Remaining Distance: ${convertDistance(remainingDistance, currentUnit)}",
////////////////////                fontSize = 18.sp,
////////////////////                modifier = Modifier.padding(vertical = 8.dp)
////////////////////            )
////////////////////
////////////////////            Row(
////////////////////                modifier = Modifier.fillMaxWidth(),
////////////////////                horizontalArrangement = Arrangement.SpaceEvenly
////////////////////            ) {
////////////////////                Button(onClick = {
////////////////////                    currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
////////////////////                }) {
////////////////////                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
////////////////////                }
////////////////////
////////////////////                if (currentStopIndex < stops.lastIndex) {
////////////////////                    Button(onClick = {
////////////////////                        currentStopIndex++
////////////////////                        if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
////////////////////                            visibleStopCount++
////////////////////                        }
////////////////////                    }) {
////////////////////                        Text(text = "Next Stop")
////////////////////                    }
////////////////////                }
////////////////////            }
////////////////////
////////////////////            Spacer(modifier = Modifier.height(20.dp))
////////////////////
////////////////////            StopList(
////////////////////                stops = stops.take(visibleStopCount),
////////////////////                currentStopIndex = currentStopIndex,
////////////////////                unit = currentUnit
////////////////////            )
////////////////////        }
////////////////////    }
////////////////////}
////////////////////
////////////////////fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
////////////////////    return if (unit == DistanceUnit.Miles) {
////////////////////        "%.1f Miles".format(distanceKm * 0.621371)
////////////////////    } else {
////////////////////        "%.1f Km".format(distanceKm)
////////////////////    }
////////////////////}
//////////////////
//////////////////
////////////////////fine but next button changes to finish Journey button after reaching final destination and works as back button
////////////////////package com.example.app
////////////////////
////////////////////import android.content.Intent
////////////////////import androidx.compose.foundation.background
////////////////////import androidx.compose.foundation.clickable
////////////////////import androidx.compose.foundation.layout.*
////////////////////import androidx.compose.material3.*
////////////////////import androidx.compose.runtime.*
////////////////////import androidx.compose.ui.Alignment
////////////////////import androidx.compose.ui.Modifier
////////////////////import androidx.compose.ui.graphics.Color
////////////////////import androidx.compose.ui.platform.LocalContext
////////////////////import androidx.compose.ui.text.font.FontWeight
////////////////////import androidx.compose.ui.unit.dp
////////////////////import androidx.compose.ui.unit.sp
////////////////////
////////////////////@Composable
////////////////////fun Application(stops: List<Stop>) {
////////////////////    var currentStopIndex by remember { mutableStateOf(0) }
////////////////////    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
////////////////////    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
////////////////////    val context = LocalContext.current
////////////////////
////////////////////    val totalDistance = stops.sumOf { it.distanceFromPrevious }
////////////////////    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
////////////////////    val remainingDistance = totalDistance - coveredDistance
////////////////////
////////////////////    Column(
////////////////////        modifier = Modifier.fillMaxSize()
////////////////////    ) {
////////////////////        // Header with Back Arrow
////////////////////        Box(
////////////////////            modifier = Modifier
////////////////////                .fillMaxWidth()
////////////////////                .height(60.dp)
////////////////////                .background(Color(0xFF0D47A1)), // Dark Blue Header
////////////////////        ) {
////////////////////            Row(
////////////////////                modifier = Modifier.fillMaxSize(),
////////////////////                verticalAlignment = Alignment.CenterVertically
////////////////////            ) {
////////////////////                // Back Arrow
////////////////////                Text(
////////////////////                    text = "←",
////////////////////                    fontSize = 28.sp,
////////////////////                    color = Color.White,
////////////////////                    fontWeight = FontWeight.Bold,
////////////////////                    modifier = Modifier
////////////////////                        .padding(start = 16.dp)
////////////////////                        .clickable {
////////////////////                            val intent = Intent(context, RouteSelectionActivity::class.java)
////////////////////                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
////////////////////                            context.startActivity(intent)
////////////////////                        }
////////////////////                )
////////////////////
////////////////////                Spacer(modifier = Modifier.weight(0.8f))
////////////////////
////////////////////                // Title
////////////////////                Text(
////////////////////                    text = "Journey Progress",
////////////////////                    fontSize = 20.sp,
////////////////////                    color = Color.White,
////////////////////                    fontWeight = FontWeight.Bold,
////////////////////                    modifier = Modifier.align(Alignment.CenterVertically)
////////////////////                )
////////////////////                // Spacer to Keep Title Centered
////////////////////                Spacer(modifier = Modifier.weight(1.2f))
////////////////////            }
////////////////////        }
////////////////////
////////////////////        Spacer(modifier = Modifier.height(10.dp))
////////////////////
////////////////////        Column(
////////////////////            modifier = Modifier.fillMaxSize().padding(20.dp),
////////////////////            horizontalAlignment = Alignment.CenterHorizontally
////////////////////        ) {
////////////////////            Text(
////////////////////                text = "Distance Covered: ${convertDistance(coveredDistance, currentUnit)}",
////////////////////                fontSize = 24.sp,
////////////////////                modifier = Modifier.padding(top = 12.dp)
////////////////////            )
////////////////////
////////////////////            LinearProgressIndicator(
////////////////////                progress = { (coveredDistance / totalDistance).toFloat() },
////////////////////                modifier = Modifier.fillMaxWidth().height(30.dp).padding(vertical = 12.dp),
////////////////////            )
////////////////////
////////////////////            Text(
////////////////////                text = "Remaining Distance: ${convertDistance(remainingDistance, currentUnit)}",
////////////////////                fontSize = 18.sp,
////////////////////                modifier = Modifier.padding(vertical = 8.dp)
////////////////////            )
////////////////////
////////////////////            Row(
////////////////////                modifier = Modifier.fillMaxWidth(),
////////////////////                horizontalArrangement = Arrangement.SpaceEvenly
////////////////////            ) {
////////////////////                // Toggle KM <-> Miles Button
////////////////////                Button(onClick = {
////////////////////                    currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
////////////////////                }) {
////////////////////                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
////////////////////                }
////////////////////
////////////////////                // Next Stop / Finish Journey Button
////////////////////                Button(
////////////////////                    onClick = {
////////////////////                        if (currentStopIndex < stops.lastIndex) {
////////////////////                            currentStopIndex++
////////////////////                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
////////////////////                                visibleStopCount++
////////////////////                            }
////////////////////                        } else {
////////////////////                            // If last stop is reached, navigate back to RouteSelectionActivity
////////////////////                            val intent = Intent(context, RouteSelectionActivity::class.java)
////////////////////                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
////////////////////                            context.startActivity(intent)
////////////////////                        }
////////////////////                    },
////////////////////                    colors = if (currentStopIndex == stops.lastIndex) ButtonDefaults.buttonColors(containerColor = Color.Red) else ButtonDefaults.buttonColors()
////////////////////                ) {
////////////////////                    Text(text = if (currentStopIndex == stops.lastIndex) "Finish Journey" else "Next Stop", color = if (currentStopIndex == stops.lastIndex) Color.White else Color.Black)
////////////////////                }
////////////////////            }
////////////////////
////////////////////            Spacer(modifier = Modifier.height(20.dp))
////////////////////
////////////////////            StopList(
////////////////////                stops = stops.take(visibleStopCount),
////////////////////                currentStopIndex = currentStopIndex,
////////////////////                unit = currentUnit
////////////////////            )
////////////////////        }
////////////////////    }
////////////////////}
////////////////////
////////////////////fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
////////////////////    return if (unit == DistanceUnit.Miles) {
////////////////////        "%.1f Miles".format(distanceKm * 0.621371)
////////////////////    } else {
////////////////////        "%.1f Km".format(distanceKm)
////////////////////    }
////////////////////}
//////////////////
////////////////////fine but next button disables after reaching final destination
//////////////////package com.example.app
//////////////////
//////////////////import android.content.Intent
//////////////////import androidx.compose.foundation.background
//////////////////import androidx.compose.foundation.clickable
//////////////////import androidx.compose.foundation.layout.*
//////////////////import androidx.compose.material3.*
//////////////////import androidx.compose.runtime.*
//////////////////import androidx.compose.ui.Alignment
//////////////////import androidx.compose.ui.Modifier
//////////////////import androidx.compose.ui.graphics.Color
//////////////////import androidx.compose.ui.platform.LocalContext
//////////////////import androidx.compose.ui.text.font.FontWeight
//////////////////import androidx.compose.ui.unit.dp
//////////////////import androidx.compose.ui.unit.sp
//////////////////
//////////////////@Composable
//////////////////fun Application(stops: List<Stop>) {
//////////////////    var currentStopIndex by remember { mutableStateOf(0) }
//////////////////    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
//////////////////    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
//////////////////    val context = LocalContext.current
//////////////////
//////////////////    val totalDistance = stops.sumOf { it.distanceFromPrevious }
//////////////////    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
//////////////////    val remainingDistance = totalDistance - coveredDistance
//////////////////
//////////////////    Column(
//////////////////        modifier = Modifier.fillMaxSize()
//////////////////    ) {
//////////////////        // Header with Back Arrow
//////////////////        Box(
//////////////////            modifier = Modifier
//////////////////                .fillMaxWidth()
//////////////////                .height(60.dp)
//////////////////                .background(Color(0xFF0D47A1)), // Dark Blue Header
//////////////////        ) {
//////////////////            Row(
//////////////////                modifier = Modifier.fillMaxSize(),
//////////////////                verticalAlignment = Alignment.CenterVertically
//////////////////            ) {
//////////////////                // Back Arrow
//////////////////                Text(
//////////////////                    text = "←",
//////////////////                    fontSize = 28.sp,
//////////////////                    color = Color.White,
//////////////////                    fontWeight = FontWeight.Bold,
//////////////////                    modifier = Modifier
//////////////////                        .padding(start = 16.dp)
//////////////////                        .clickable {
//////////////////                            val intent = Intent(context, RouteSelectionActivity::class.java)
//////////////////                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//////////////////                            context.startActivity(intent)
//////////////////                        }
//////////////////                )
//////////////////
//////////////////                Spacer(modifier = Modifier.weight(0.8f))
//////////////////
//////////////////                // Title
//////////////////                Text(
//////////////////                    text = "Journey Progress",
//////////////////                    fontSize = 20.sp,
//////////////////                    color = Color.White,
//////////////////                    fontWeight = FontWeight.Bold,
//////////////////                    modifier = Modifier.align(Alignment.CenterVertically)
//////////////////                )
//////////////////                // Spacer to Keep Title Centered
//////////////////                Spacer(modifier = Modifier.weight(1.2f))
//////////////////            }
//////////////////        }
//////////////////
//////////////////        Spacer(modifier = Modifier.height(10.dp))
//////////////////
//////////////////        Column(
//////////////////            modifier = Modifier.fillMaxSize().padding(20.dp),
//////////////////            horizontalAlignment = Alignment.CenterHorizontally
//////////////////        ) {
//////////////////            Text(
//////////////////                text = "Distance Covered: ${convertDistance(coveredDistance, currentUnit)}",
//////////////////                fontSize = 24.sp,
//////////////////                modifier = Modifier.padding(top = 12.dp)
//////////////////            )
//////////////////
//////////////////            LinearProgressIndicator(
//////////////////                progress = { (coveredDistance / totalDistance).toFloat() },
//////////////////                modifier = Modifier.fillMaxWidth().height(30.dp).padding(vertical = 12.dp),
//////////////////            )
//////////////////
//////////////////            Text(
//////////////////                text = "Remaining Distance: ${convertDistance(remainingDistance, currentUnit)}",
//////////////////                fontSize = 18.sp,
//////////////////                modifier = Modifier.padding(vertical = 8.dp)
//////////////////            )
//////////////////
//////////////////            Row(
//////////////////                modifier = Modifier.fillMaxWidth(),
//////////////////                horizontalArrangement = Arrangement.SpaceEvenly
//////////////////            ) {
//////////////////                // Toggle KM <-> Miles Button
//////////////////                Button(onClick = {
//////////////////                    currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
//////////////////                }, modifier = Modifier.width(145.dp) // Fixing button width
//////////////////                ) {
//////////////////                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "KM"}")
//////////////////                }
//////////////////
//////////////////                // Next Stop Button (Disabled if at Last Stop)
//////////////////                Button(
//////////////////                    onClick = {
//////////////////                        if (currentStopIndex < stops.lastIndex) {
//////////////////                            currentStopIndex++
//////////////////                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
//////////////////                                visibleStopCount++
//////////////////                            }
//////////////////                        }
//////////////////                    }, modifier = Modifier.width(140.dp),
//////////////////                    enabled = currentStopIndex < stops.lastIndex, // Disabled when at last stop
//////////////////                    colors = if (currentStopIndex == stops.lastIndex)
//////////////////                        ButtonDefaults.buttonColors(containerColor = Color.Gray) // Grey when disabled
//////////////////                    else
//////////////////                        ButtonDefaults.buttonColors()
//////////////////                ) {
//////////////////                    Text(text = "Next Stop")
//////////////////                }
//////////////////            }
//////////////////
//////////////////            Spacer(modifier = Modifier.height(20.dp))
//////////////////
//////////////////            StopList(
//////////////////                stops = stops.take(visibleStopCount),
//////////////////                currentStopIndex = currentStopIndex,
//////////////////                unit = currentUnit
//////////////////            )
//////////////////        }
//////////////////    }
//////////////////}
//////////////////
//////////////////fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
//////////////////    return if (unit == DistanceUnit.Miles) {
//////////////////        "%.1f Miles".format(distanceKm * 0.621371)
//////////////////    } else {
//////////////////        "%.1f Km".format(distanceKm)
//////////////////    }
//////////////////}
////////////////
////////////////
////////////////
////////////////
////////////////
////////////////package com.example.app
////////////////
////////////////import android.content.Intent
////////////////import androidx.compose.foundation.background
////////////////import androidx.compose.foundation.clickable
////////////////import androidx.compose.foundation.layout.*
////////////////import androidx.compose.foundation.shape.RoundedCornerShape
////////////////import androidx.compose.material3.*
////////////////import androidx.compose.runtime.*
////////////////import androidx.compose.ui.Alignment
////////////////import androidx.compose.ui.Modifier
////////////////import androidx.compose.ui.draw.shadow
////////////////import androidx.compose.ui.graphics.Brush
////////////////import androidx.compose.ui.graphics.Color
////////////////import androidx.compose.ui.platform.LocalContext
////////////////import androidx.compose.ui.text.font.FontWeight
////////////////import androidx.compose.ui.unit.dp
////////////////import androidx.compose.ui.unit.sp
////////////////
////////////////@Composable
////////////////fun EnhancedMainScreen(stops: List<Stop>) {
////////////////    var currentStopIndex by remember { mutableStateOf(0) }
////////////////    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
////////////////    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
////////////////    val context = LocalContext.current
////////////////
////////////////    val totalDistance = stops.sumOf { it.distanceFromPrevious }
////////////////    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
////////////////    val remainingDistance = totalDistance - coveredDistance
////////////////
////////////////    // Gradient Background
////////////////    Box(
////////////////        modifier = Modifier
////////////////            .fillMaxSize()
////////////////            .background(
////////////////                Brush.verticalGradient(
////////////////                    colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6), Color.White),
////////////////                )
////////////////            )
////////////////            .padding(16.dp),
////////////////        contentAlignment = Alignment.TopCenter
////////////////    ) {
////////////////        Column(
////////////////            modifier = Modifier
////////////////                .fillMaxSize()
////////////////                .padding(top = 16.dp),
////////////////            horizontalAlignment = Alignment.CenterHorizontally
////////////////        ) {
////////////////            // Header with Back Arrow
////////////////            Box(
////////////////                modifier = Modifier
////////////////                    .fillMaxWidth()
////////////////                    .height(60.dp)
////////////////                    .background(Color(0xFF0D47A1)), // Dark Blue Header
////////////////            ) {
////////////////                Row(
////////////////                    modifier = Modifier.fillMaxSize(),
////////////////                    verticalAlignment = Alignment.CenterVertically
////////////////                ) {
////////////////                    // Back Arrow
////////////////                    Text(
////////////////                        text = "←",
////////////////                        fontSize = 28.sp,
////////////////                        color = Color.White,
////////////////                        fontWeight = FontWeight.Bold,
////////////////                        modifier = Modifier
////////////////                            .padding(start = 16.dp)
////////////////                            .clickable {
////////////////                                val intent = Intent(context, RouteSelectionActivity::class.java)
////////////////                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
////////////////                                context.startActivity(intent)
////////////////                            }
////////////////                    )
////////////////
////////////////                    Spacer(modifier = Modifier.weight(0.8f))
////////////////
////////////////                    // Title
////////////////                    Text(
////////////////                        text = "Journey Progress",
////////////////                        fontSize = 22.sp,
////////////////                        color = Color.White,
////////////////                        fontWeight = FontWeight.Bold,
////////////////                        modifier = Modifier.align(Alignment.CenterVertically)
////////////////                    )
////////////////                    Spacer(modifier = Modifier.weight(1.2f))
////////////////                }
////////////////            }
////////////////
////////////////            Spacer(modifier = Modifier.height(10.dp))
////////////////
////////////////            // Journey Progress Card
////////////////            Card(
////////////////                modifier = Modifier
////////////////                    .fillMaxWidth()
////////////////                    .padding(12.dp)
////////////////                    .shadow(8.dp, RoundedCornerShape(12.dp)),
////////////////                shape = RoundedCornerShape(12.dp),
////////////////                colors = CardDefaults.cardColors(containerColor = Color.White)
////////////////            ) {
////////////////                Column(
////////////////                    modifier = Modifier.padding(16.dp),
////////////////                    horizontalAlignment = Alignment.CenterHorizontally
////////////////                ) {
////////////////                    Text(
////////////////                        text = "Distance Covered",
////////////////                        fontSize = 18.sp,
////////////////                        color = Color.DarkGray
////////////////                    )
////////////////
////////////////                    Text(
////////////////                        text = "${convertDistance(coveredDistance, currentUnit)}",
////////////////                        fontSize = 24.sp,
////////////////                        fontWeight = FontWeight.Bold,
////////////////                        color = Color(0xFF0D47A1)
////////////////                    )
////////////////
////////////////                    LinearProgressIndicator(
////////////////                        progress = { (coveredDistance / totalDistance).toFloat() },
////////////////                        modifier = Modifier
////////////////                            .fillMaxWidth()
////////////////                            .height(12.dp)
////////////////                            .padding(vertical = 8.dp),
////////////////                        color = Color(0xFF0D47A1)
////////////////                    )
////////////////
////////////////                    Text(
////////////////                        text = "Remaining Distance",
////////////////                        fontSize = 18.sp,
////////////////                        color = Color.DarkGray
////////////////                    )
////////////////
////////////////                    Text(
////////////////                        text = "${convertDistance(remainingDistance, currentUnit)}",
////////////////                        fontSize = 24.sp,
////////////////                        fontWeight = FontWeight.Bold,
////////////////                        color = Color(0xFF0D47A1)
////////////////                    )
////////////////                }
////////////////            }
////////////////
////////////////            Spacer(modifier = Modifier.height(20.dp))
////////////////
////////////////            // Buttons Row
////////////////            Row(
////////////////                modifier = Modifier.fillMaxWidth(),
////////////////                horizontalArrangement = Arrangement.SpaceEvenly
////////////////            ) {
////////////////                Button(
////////////////                    onClick = {
////////////////                        currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
////////////////                    },
////////////////                    modifier = Modifier
////////////////                        .width(160.dp)
////////////////                        .shadow(4.dp, RoundedCornerShape(8.dp)),
////////////////                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B5F6))
////////////////                ) {
////////////////                    Text(
////////////////                        text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}",
////////////////                        color = Color.White
////////////////                    )
////////////////                }
////////////////
////////////////                // Next Stop Button (Disabled at Last Stop)
////////////////                Button(
////////////////                    onClick = {
////////////////                        if (currentStopIndex < stops.lastIndex) {
////////////////                            currentStopIndex++
////////////////                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
////////////////                                visibleStopCount++
////////////////                            }
////////////////                        }
////////////////                    },
////////////////                    enabled = currentStopIndex < stops.lastIndex,
////////////////                    modifier = Modifier
////////////////                        .width(160.dp)
////////////////                        .shadow(4.dp, RoundedCornerShape(8.dp)),
////////////////                    colors = if (currentStopIndex == stops.lastIndex)
////////////////                        ButtonDefaults.buttonColors(containerColor = Color.Gray)
////////////////                    else
////////////////                        ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
////////////////                ) {
////////////////                    Text(
////////////////                        text = "Next Stop",
////////////////                        color = Color.White
////////////////                    )
////////////////                }
////////////////            }
////////////////
////////////////            Spacer(modifier = Modifier.height(20.dp))
////////////////
////////////////            // Stop List
////////////////            StopList(
////////////////                stops = stops.take(visibleStopCount),
////////////////                currentStopIndex = currentStopIndex,
////////////////                unit = currentUnit
////////////////            )
////////////////        }
////////////////    }
////////////////}
////////////////
////////////////fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
////////////////    return if (unit == DistanceUnit.Miles) {
////////////////        "%.1f Miles".format(distanceKm * 0.621371)
////////////////    } else {
////////////////        "%.1f Km".format(distanceKm)
////////////////    }
////////////////}
//////////////
//////////////
//////////////
//////////////
//////////////
//////////////package com.example.app
//////////////
//////////////import android.content.Intent
//////////////import androidx.compose.foundation.background
//////////////import androidx.compose.foundation.clickable
//////////////import androidx.compose.foundation.layout.*
//////////////import androidx.compose.foundation.lazy.LazyColumn
//////////////import androidx.compose.foundation.lazy.items
//////////////import androidx.compose.foundation.shape.RoundedCornerShape
//////////////import androidx.compose.material3.*
//////////////import androidx.compose.runtime.*
//////////////import androidx.compose.ui.Alignment
//////////////import androidx.compose.ui.Modifier
//////////////import androidx.compose.ui.draw.shadow
//////////////import androidx.compose.ui.graphics.Brush
//////////////import androidx.compose.ui.graphics.Color
//////////////import androidx.compose.ui.platform.LocalContext
//////////////import androidx.compose.ui.unit.dp
//////////////import androidx.compose.ui.unit.sp
//////////////
//////////////@Composable
//////////////fun EnhancedMainScreen(stops: List<Stop>) {
//////////////    var currentStopIndex by remember { mutableStateOf(0) }
//////////////    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
//////////////    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
//////////////    val context = LocalContext.current
//////////////
//////////////    val totalDistance = stops.sumOf { it.distanceFromPrevious }
//////////////    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
//////////////    val remainingDistance = totalDistance - coveredDistance
//////////////
//////////////    // Gradient Background
//////////////    Box(
//////////////        modifier = Modifier
//////////////            .fillMaxSize()
//////////////            .background(
//////////////                Brush.verticalGradient(
//////////////                    colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6), Color.White),
//////////////                )
//////////////            )
//////////////            .padding(16.dp),
//////////////        contentAlignment = Alignment.TopCenter
//////////////    ) {
//////////////        Column(
//////////////            modifier = Modifier.fillMaxSize(),
//////////////            horizontalAlignment = Alignment.CenterHorizontally
//////////////        ) {
//////////////            // Header
//////////////            Box(
//////////////                modifier = Modifier
//////////////                    .fillMaxWidth()
//////////////                    .height(60.dp)
//////////////                    .background(Color(0xFF0D47A1)),
//////////////                contentAlignment = Alignment.Center
//////////////            ) {
//////////////                Text(
//////////////                    text = "Journey Progress",
//////////////                    fontSize = 22.sp,
//////////////                    color = Color.White
//////////////                )
//////////////            }
//////////////
//////////////            Spacer(modifier = Modifier.height(10.dp))
//////////////
//////////////            // Progress Card
//////////////            Card(
//////////////                modifier = Modifier
//////////////                    .fillMaxWidth()
//////////////                    .padding(12.dp)
//////////////                    .shadow(8.dp, RoundedCornerShape(12.dp)),
//////////////                shape = RoundedCornerShape(12.dp),
//////////////                colors = CardDefaults.cardColors(containerColor = Color.White)
//////////////            ) {
//////////////                Column(
//////////////                    modifier = Modifier.padding(16.dp),
//////////////                    horizontalAlignment = Alignment.CenterHorizontally
//////////////                ) {
//////////////                    Text(text = "Distance Covered", fontSize = 18.sp, color = Color.DarkGray)
//////////////
//////////////                    Text(
//////////////                        text = "${convertDistance(coveredDistance, currentUnit)}",
//////////////                        fontSize = 24.sp,
//////////////                        color = Color(0xFF0D47A1)
//////////////                    )
//////////////
//////////////                    LinearProgressIndicator(
//////////////                        progress = { (coveredDistance / totalDistance).toFloat() },
//////////////                        modifier = Modifier
//////////////                            .fillMaxWidth()
//////////////                            .height(12.dp)
//////////////                            .padding(vertical = 8.dp),
//////////////                        color = Color(0xFF0D47A1)
//////////////                    )
//////////////
//////////////                    Text(text = "Remaining Distance", fontSize = 18.sp, color = Color.DarkGray)
//////////////
//////////////                    Text(
//////////////                        text = "${convertDistance(remainingDistance, currentUnit)}",
//////////////                        fontSize = 24.sp,
//////////////                        color = Color(0xFF0D47A1)
//////////////                    )
//////////////                }
//////////////            }
//////////////
//////////////            Spacer(modifier = Modifier.height(10.dp))
//////////////
//////////////            // Stop List with Scroll
//////////////            Box(
//////////////                modifier = Modifier
//////////////                    .weight(1f) // Ensures this takes up available space
//////////////                    .fillMaxWidth()
//////////////                    .padding(bottom = 10.dp)
//////////////            ) {
//////////////                LazyColumn(
//////////////                    modifier = Modifier.fillMaxWidth()
//////////////                ) {
//////////////                    items(stops.take(visibleStopCount)) { stop ->
//////////////                        StopItem(stop)
//////////////                    }
//////////////                }
//////////////            }
//////////////
//////////////            // Buttons at the Bottom (Fixed)
//////////////            Row(
//////////////                modifier = Modifier
//////////////                    .fillMaxWidth()
//////////////                    .padding(bottom = 16.dp),
//////////////                horizontalArrangement = Arrangement.SpaceEvenly
//////////////            ) {
//////////////                Button(
//////////////                    onClick = {
//////////////                        currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
//////////////                    },
//////////////                    modifier = Modifier.width(160.dp)
//////////////                ) {
//////////////                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
//////////////                }
//////////////
//////////////                Button(
//////////////                    onClick = {
//////////////                        if (currentStopIndex < stops.lastIndex) {
//////////////                            currentStopIndex++
//////////////                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
//////////////                                visibleStopCount++
//////////////                            }
//////////////                        }
//////////////                    },
//////////////                    enabled = currentStopIndex < stops.lastIndex,
//////////////                    modifier = Modifier.width(160.dp),
//////////////                    colors = if (currentStopIndex == stops.lastIndex)
//////////////                        ButtonDefaults.buttonColors(containerColor = Color.Gray)
//////////////                    else
//////////////                        ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
//////////////                ) {
//////////////                    Text(text = "Next Stop")
//////////////                }
//////////////            }
//////////////        }
//////////////    }
//////////////}
//////////////
//////////////// Stop List Item
//////////////@Composable
//////////////fun StopItem(stop: Stop) {
//////////////    Card(
//////////////        modifier = Modifier
//////////////            .fillMaxWidth()
//////////////            .padding(8.dp),
//////////////        shape = RoundedCornerShape(12.dp),
//////////////        colors = CardDefaults.cardColors(containerColor = Color.White)
//////////////    ) {
//////////////        Column(modifier = Modifier.padding(12.dp)) {
//////////////            Text(text = stop.name, fontSize = 18.sp, color = Color.Black)
//////////////
//////////////            Text(
//////////////                text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}",
//////////////                fontSize = 14.sp,
//////////////                color = Color.Gray
//////////////            )
//////////////
//////////////            Text(
//////////////                text = "Distance: ${convertDistance(stop.distanceFromPrevious, DistanceUnit.Km)}",
//////////////                fontSize = 14.sp,
//////////////                color = Color.Gray
//////////////            )
//////////////        }
//////////////    }
//////////////}
//////////////
//////////////// Convert Distance Function
//////////////fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
//////////////    return if (unit == DistanceUnit.Miles) {
//////////////        "%.1f Miles".format(distanceKm * 0.621371)
//////////////    } else {
//////////////        "%.1f Km".format(distanceKm)
//////////////    }
//////////////}
////////////
////////////
////////////
////////////
//////////package com.example.app
//////////
//////////import android.content.Intent
//////////import androidx.compose.foundation.background
//////////import androidx.compose.foundation.clickable
//////////import androidx.compose.foundation.layout.*
//////////import androidx.compose.foundation.lazy.LazyColumn
//////////import androidx.compose.foundation.lazy.items
//////////import androidx.compose.foundation.shape.RoundedCornerShape
//////////import androidx.compose.material3.*
//////////import androidx.compose.runtime.*
//////////import androidx.compose.ui.Alignment
//////////import androidx.compose.ui.Modifier
//////////import androidx.compose.ui.draw.shadow
//////////import androidx.compose.ui.graphics.Brush
//////////import androidx.compose.ui.graphics.Color
//////////import androidx.compose.ui.platform.LocalContext
//////////import androidx.compose.ui.text.font.FontWeight
//////////import androidx.compose.ui.unit.dp
//////////import androidx.compose.ui.unit.sp
//////////
//////////@Composable
//////////fun EnhancedMainScreen(stops: List<Stop>) {
//////////    var currentStopIndex by remember { mutableStateOf(0) }
//////////    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
//////////    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
//////////    val context = LocalContext.current
//////////
//////////    val totalDistance = stops.sumOf { it.distanceFromPrevious }
//////////    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
//////////    val remainingDistance = totalDistance - coveredDistance
//////////
//////////    // Gradient Background
//////////    Box(
//////////        modifier = Modifier
//////////            .fillMaxSize()
//////////            .background(
//////////                Brush.verticalGradient(
//////////                    colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6), Color.White),
//////////                )
//////////            )
//////////            .padding(16.dp),
//////////        contentAlignment = Alignment.TopCenter
//////////    ) {
//////////        Column(
//////////            modifier = Modifier.fillMaxSize(),
//////////            horizontalAlignment = Alignment.CenterHorizontally
//////////        ) {
//////////            // Header
//////////            Box(
//////////                modifier = Modifier
//////////                    .fillMaxWidth()
//////////                    .height(60.dp)
//////////                    .background(Color(0xFF0D47A1)),
//////////                contentAlignment = Alignment.Center
//////////            ) {
//////////                Text(
//////////                    text = "Journey Progress",
//////////                    fontSize = 22.sp,
//////////                    color = Color.White
//////////                )
//////////            }
//////////
//////////            Spacer(modifier = Modifier.height(10.dp))
//////////
//////////            // Progress Card
//////////            Card(
//////////                modifier = Modifier
//////////                    .fillMaxWidth()
//////////                    .padding(12.dp)
//////////                    .shadow(8.dp, RoundedCornerShape(12.dp)),
//////////                shape = RoundedCornerShape(12.dp),
//////////                colors = CardDefaults.cardColors(containerColor = Color.White)
//////////            ) {
//////////                Column(
//////////                    modifier = Modifier.padding(16.dp),
//////////                    horizontalAlignment = Alignment.CenterHorizontally
//////////                ) {
//////////                    Text(text = "Distance Covered", fontSize = 18.sp, color = Color.DarkGray)
//////////
//////////                    Text(
//////////                        text = "${convertDistance(coveredDistance, currentUnit)}",
//////////                        fontSize = 24.sp,
//////////                        color = Color(0xFF0D47A1)
//////////                    )
//////////
//////////                    LinearProgressIndicator(
//////////                        progress = { (coveredDistance / totalDistance).toFloat() },
//////////                        modifier = Modifier
//////////                            .fillMaxWidth()
//////////                            .height(12.dp)
//////////                            .padding(vertical = 8.dp),
//////////                        color = Color(0xFF0D47A1)
//////////                    )
//////////
//////////                    Text(text = "Remaining Distance", fontSize = 18.sp, color = Color.DarkGray)
//////////
//////////                    Text(
//////////                        text = "${convertDistance(remainingDistance, currentUnit)}",
//////////                        fontSize = 24.sp,
//////////                        color = Color(0xFF0D47A1)
//////////                    )
//////////                }
//////////            }
//////////
//////////            Spacer(modifier = Modifier.height(10.dp))
//////////
//////////            // Stop List with Scroll
//////////            Box(
//////////                modifier = Modifier
//////////                    .weight(1f) // Ensures this takes up available space
//////////                    .fillMaxWidth()
//////////                    .padding(bottom = 10.dp)
//////////            ) {
//////////                LazyColumn(
//////////                    modifier = Modifier.fillMaxWidth()
//////////                ) {
//////////                    items(stops.take(visibleStopCount).withIndex().toList()) { (index, stop) ->
//////////                        StopItem(stop, isCurrentStop = index == currentStopIndex)
//////////                    }
//////////                }
//////////            }
//////////
//////////            // Buttons at the Bottom (Fixed)
//////////            Row(
//////////                modifier = Modifier
//////////                    .fillMaxWidth()
//////////                    .padding(bottom = 16.dp),
//////////                horizontalArrangement = Arrangement.SpaceEvenly
//////////            ) {
//////////                Button(
//////////                    onClick = {
//////////                        currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
//////////                    },
//////////                    modifier = Modifier.width(160.dp)
//////////                ) {
//////////                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
//////////                }
//////////
//////////                Button(
//////////                    onClick = {
//////////                        if (currentStopIndex < stops.lastIndex) {
//////////                            currentStopIndex++
//////////                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
//////////                                visibleStopCount++
//////////                            }
//////////                        }
//////////                    },
//////////                    enabled = currentStopIndex < stops.lastIndex,
//////////                    modifier = Modifier.width(160.dp),
//////////                    colors = if (currentStopIndex == stops.lastIndex)
//////////                        ButtonDefaults.buttonColors(containerColor = Color.Gray)
//////////                    else
//////////                        ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
//////////                ) {
//////////                    Text(text = "Next Stop")
//////////                }
//////////            }
//////////        }
//////////    }
//////////}
//////////
//////////// Stop List Item (Highlight Current Stop)
//////////@Composable
//////////fun StopItem(stop: Stop, isCurrentStop: Boolean) {
//////////    Card(
//////////        modifier = Modifier
//////////            .fillMaxWidth()
//////////            .padding(8.dp)
//////////            .shadow(if (isCurrentStop) 8.dp else 2.dp, RoundedCornerShape(12.dp)), // Highlight effect
//////////        shape = RoundedCornerShape(12.dp),
//////////        colors = CardDefaults.cardColors(
//////////            containerColor = if (isCurrentStop) Color(0xFFBBDEFB) else Color.White // Highlight current stop
//////////        )
//////////    ) {
//////////        Column(
//////////            modifier = Modifier.padding(12.dp)
//////////        ) {
//////////            Text(
//////////                text = stop.name,
//////////                fontSize = if (isCurrentStop) 20.sp else 18.sp,
//////////                color = Color.Black,
//////////                fontWeight = if (isCurrentStop) FontWeight.Bold else FontWeight.Normal
//////////            )
//////////
//////////            Text(
//////////                text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}",
//////////                fontSize = 14.sp,
//////////                color = Color.Gray
//////////            )
//////////
//////////            Text(
//////////                text = "Distance: ${convertDistance(stop.distanceFromPrevious, DistanceUnit.Km)}",
//////////                fontSize = 14.sp,
//////////                color = Color.Gray
//////////            )
//////////        }
//////////    }
//////////}
//////////
//////////// Convert Distance Function
//////////fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
//////////    return if (unit == DistanceUnit.Miles) {
//////////        "%.1f Miles".format(distanceKm * 0.621371)
//////////    } else {
//////////        "%.1f Km".format(distanceKm)
//////////    }
//////////}
//////////
//////////
//////////
//////////
//////////
//////////
//////////package com.example.app
//////////
//////////import android.content.Intent
//////////import androidx.compose.foundation.background
//////////import androidx.compose.foundation.clickable
//////////import androidx.compose.foundation.layout.*
//////////import androidx.compose.foundation.lazy.LazyColumn
//////////import androidx.compose.foundation.lazy.LazyListState
//////////import androidx.compose.foundation.lazy.itemsIndexed
//////////import androidx.compose.foundation.lazy.rememberLazyListState
//////////import androidx.compose.foundation.shape.RoundedCornerShape
//////////import androidx.compose.material3.*
//////////import androidx.compose.runtime.*
//////////import androidx.compose.ui.Alignment
//////////import androidx.compose.ui.Modifier
//////////import androidx.compose.ui.draw.shadow
//////////import androidx.compose.ui.graphics.Brush
//////////import androidx.compose.ui.graphics.Color
//////////import androidx.compose.ui.platform.LocalContext
//////////import androidx.compose.ui.unit.dp
//////////import androidx.compose.ui.unit.sp
//////////import kotlinx.coroutines.launch
//////////
//////////@Composable
//////////fun EnhancedMainScreen(stops: List<Stop>) {
//////////    var currentStopIndex by remember { mutableStateOf(0) }
//////////    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
//////////    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
//////////    val context = LocalContext.current
//////////
//////////    val totalDistance = stops.sumOf { it.distanceFromPrevious }
//////////    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
//////////    val remainingDistance = totalDistance - coveredDistance
//////////
//////////    // LazyColumn scrolling state
//////////    val listState = rememberLazyListState()
//////////    val coroutineScope = rememberCoroutineScope()
//////////
//////////    // Gradient Background
//////////    Box(
//////////        modifier = Modifier
//////////            .fillMaxSize()
//////////            .background(
//////////                Brush.verticalGradient(
//////////                    colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6), Color.White),
//////////                )
//////////            )
//////////            .padding(16.dp),
//////////        contentAlignment = Alignment.TopCenter
//////////    ) {
//////////        Column(
//////////            modifier = Modifier.fillMaxSize(),
//////////            horizontalAlignment = Alignment.CenterHorizontally
//////////        ) {
//////////            // Header
//////////            Box(
//////////                modifier = Modifier
//////////                    .fillMaxWidth()
//////////                    .height(60.dp)
//////////                    .background(Color(0xFF0D47A1)),
//////////                contentAlignment = Alignment.Center
//////////            ) {
//////////                Text(
//////////                    text = "Journey Progress",
//////////                    fontSize = 22.sp,
//////////                    color = Color.White
//////////                )
//////////            }
//////////
//////////            Spacer(modifier = Modifier.height(10.dp))
//////////
//////////            // Progress Card
//////////            Card(
//////////                modifier = Modifier
//////////                    .fillMaxWidth()
//////////                    .padding(12.dp)
//////////                    .shadow(8.dp, RoundedCornerShape(12.dp)),
//////////                shape = RoundedCornerShape(12.dp),
//////////                colors = CardDefaults.cardColors(containerColor = Color.White)
//////////            ) {
//////////                Column(
//////////                    modifier = Modifier.padding(16.dp),
//////////                    horizontalAlignment = Alignment.CenterHorizontally
//////////                ) {
//////////                    Text(text = "Distance Covered", fontSize = 18.sp, color = Color.DarkGray)
//////////
//////////                    Text(
//////////                        text = "${convertDistance(coveredDistance, currentUnit)}",
//////////                        fontSize = 24.sp,
//////////                        color = Color(0xFF0D47A1)
//////////                    )
//////////
//////////                    LinearProgressIndicator(
//////////                        progress = { (coveredDistance / totalDistance).toFloat() },
//////////                        modifier = Modifier
//////////                            .fillMaxWidth()
//////////                            .height(12.dp)
//////////                            .padding(vertical = 8.dp),
//////////                        color = Color(0xFF0D47A1)
//////////                    )
//////////
//////////                    Text(text = "Remaining Distance", fontSize = 18.sp, color = Color.DarkGray)
//////////
//////////                    Text(
//////////                        text = "${convertDistance(remainingDistance, currentUnit)}",
//////////                        fontSize = 24.sp,
//////////                        color = Color(0xFF0D47A1)
//////////                    )
//////////                }
//////////            }
//////////
//////////            Spacer(modifier = Modifier.height(10.dp))
//////////
//////////            // Stop List with Auto-Scroll
//////////            Box(
//////////                modifier = Modifier
//////////                    .weight(1f)
//////////                    .fillMaxWidth()
//////////                    .padding(bottom = 10.dp)
//////////            ) {
//////////                LazyColumn(
//////////                    state = listState, // Attach scroll state
//////////                    modifier = Modifier.fillMaxWidth()
//////////                ) {
//////////                    itemsIndexed(stops.take(visibleStopCount)) { index, stop ->
//////////                        StopItem(stop, isCurrentStop = index == currentStopIndex)
//////////                    }
//////////                }
//////////            }
//////////
//////////            // Buttons at the Bottom (Fixed)
//////////            Row(
//////////                modifier = Modifier
//////////                    .fillMaxWidth()
//////////                    .padding(bottom = 16.dp),
//////////                horizontalArrangement = Arrangement.SpaceEvenly
//////////            ) {
//////////                Button(
//////////                    onClick = {
//////////                        currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
//////////                    },
//////////                    modifier = Modifier.width(160.dp)
//////////                ) {
//////////                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
//////////                }
//////////
//////////                Button(
//////////                    onClick = {
//////////                        if (currentStopIndex < stops.lastIndex) {
//////////                            currentStopIndex++
//////////                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
//////////                                visibleStopCount++
//////////                            }
//////////
//////////                            // Scroll to the next stop
//////////                            coroutineScope.launch {
//////////                                listState.animateScrollToItem(currentStopIndex)
//////////                            }
//////////                        }
//////////                    },
//////////                    enabled = currentStopIndex < stops.lastIndex,
//////////                    modifier = Modifier.width(160.dp),
//////////                    colors = if (currentStopIndex == stops.lastIndex)
//////////                        ButtonDefaults.buttonColors(containerColor = Color.Gray)
//////////                    else
//////////                        ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
//////////                ) {
//////////                    Text(text = "Next Stop")
//////////                }
//////////            }
//////////        }
//////////    }
//////////}
//////////
//////////// Stop List Item with Highlight
//////////@Composable
//////////fun StopItem(stop: Stop, isCurrentStop: Boolean) {
//////////    val backgroundColor = if (isCurrentStop) Color(0xFF64B5F6) else Color.White
//////////    val textColor = if (isCurrentStop) Color.White else Color.Black
//////////
//////////    Card(
//////////        modifier = Modifier
//////////            .fillMaxWidth()
//////////            .padding(8.dp)
//////////            .background(backgroundColor),
//////////        shape = RoundedCornerShape(12.dp),
//////////        colors = CardDefaults.cardColors(containerColor = backgroundColor)
//////////    ) {
//////////        Column(modifier = Modifier.padding(12.dp)) {
//////////            Text(text = stop.name, fontSize = 18.sp, color = textColor)
//////////
//////////            Text(
//////////                text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}",
//////////                fontSize = 14.sp,
//////////                color = textColor
//////////            )
//////////
//////////            Text(
//////////                text = "Distance: ${convertDistance(stop.distanceFromPrevious, DistanceUnit.Km)}",
//////////                fontSize = 14.sp,
//////////                color = textColor
//////////            )
//////////        }
//////////    }
//////////}
//////////
//////////// Convert Distance Function
//////////fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
//////////    return if (unit == DistanceUnit.Miles) {
//////////        "%.1f Miles".format(distanceKm * 0.621371)
//////////    } else {
//////////        "%.1f Km".format(distanceKm)
//////////    }
//////////}
////////
////////
////////
////////package com.example.app
////////
////////import android.content.Intent
////////import androidx.compose.foundation.background
////////import androidx.compose.foundation.clickable
////////import androidx.compose.foundation.layout.*
////////import androidx.compose.foundation.lazy.LazyColumn
////////import androidx.compose.foundation.lazy.LazyListState
////////import androidx.compose.foundation.lazy.itemsIndexed
////////import androidx.compose.foundation.lazy.rememberLazyListState
////////import androidx.compose.foundation.shape.RoundedCornerShape
////////import androidx.compose.material3.*
////////import androidx.compose.runtime.*
////////import androidx.compose.ui.Alignment
////////import androidx.compose.ui.Modifier
////////import androidx.compose.ui.draw.shadow
////////import androidx.compose.ui.graphics.Brush
////////import androidx.compose.ui.graphics.Color
////////import androidx.compose.ui.platform.LocalContext
////////import androidx.compose.ui.unit.dp
////////import androidx.compose.ui.unit.sp
////////import kotlinx.coroutines.launch
////////
////////@Composable
////////fun EnhancedMainScreen(stops: List<Stop>) {
////////    var currentStopIndex by remember { mutableStateOf(0) }
////////    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
////////    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
////////    val context = LocalContext.current
////////
////////    val totalDistance = stops.sumOf { it.distanceFromPrevious }
////////    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
////////    val remainingDistance = totalDistance - coveredDistance
////////
////////    // Snackbar state
////////    val snackbarHostState = remember { SnackbarHostState() }
////////    val coroutineScope = rememberCoroutineScope()
////////
////////    // LazyColumn scrolling state
////////    val listState = rememberLazyListState()
////////
////////    // Gradient Background
////////    Box(
////////        modifier = Modifier
////////            .fillMaxSize()
////////            .background(
////////                Brush.verticalGradient(
////////                    colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6), Color.White),
////////                )
////////            )
////////            .padding(16.dp),
////////        contentAlignment = Alignment.TopCenter
////////    ) {
////////        Column(
////////            modifier = Modifier.fillMaxSize(),
////////            horizontalAlignment = Alignment.CenterHorizontally
////////        ) {
////////            // Snackbar Host
////////            SnackbarHost(hostState = snackbarHostState)
////////
////////            // Header
////////            Box(
////////                modifier = Modifier
////////                    .fillMaxWidth()
////////                    .height(60.dp)
////////                    .background(Color(0xFF0D47A1)),
////////                contentAlignment = Alignment.Center
////////            ) {
////////                Text(
////////                    text = "Journey Progress",
////////                    fontSize = 22.sp,
////////                    color = Color.White
////////                )
////////            }
////////
////////            Spacer(modifier = Modifier.height(10.dp))
////////
////////            // Progress Card
////////            Card(
////////                modifier = Modifier
////////                    .fillMaxWidth()
////////                    .padding(12.dp)
////////                    .shadow(8.dp, RoundedCornerShape(12.dp)),
////////                shape = RoundedCornerShape(12.dp),
////////                colors = CardDefaults.cardColors(containerColor = Color.White)
////////            ) {
////////                Column(
////////                    modifier = Modifier.padding(16.dp),
////////                    horizontalAlignment = Alignment.CenterHorizontally
////////                ) {
////////                    Text(text = "Distance Covered", fontSize = 18.sp, color = Color.DarkGray)
////////
////////                    Text(
////////                        text = "${convertDistance(coveredDistance, currentUnit)}",
////////                        fontSize = 24.sp,
////////                        color = Color(0xFF0D47A1)
////////                    )
////////
////////                    LinearProgressIndicator(
////////                        progress = { (coveredDistance / totalDistance).toFloat() },
////////                        modifier = Modifier
////////                            .fillMaxWidth()
////////                            .height(12.dp)
////////                            .padding(vertical = 8.dp),
////////                        color = Color(0xFF0D47A1)
////////                    )
////////
////////                    Text(text = "Remaining Distance", fontSize = 18.sp, color = Color.DarkGray)
////////
////////                    Text(
////////                        text = "${convertDistance(remainingDistance, currentUnit)}",
////////                        fontSize = 24.sp,
////////                        color = Color(0xFF0D47A1)
////////                    )
////////                }
////////            }
////////
////////            Spacer(modifier = Modifier.height(10.dp))
////////
////////            // Stop List with Auto-Scroll
////////            Box(
////////                modifier = Modifier
////////                    .weight(1f)
////////                    .fillMaxWidth()
////////                    .padding(bottom = 10.dp)
////////            ) {
////////                LazyColumn(
////////                    state = listState, // Attach scroll state
////////                    modifier = Modifier.fillMaxWidth()
////////                ) {
////////                    itemsIndexed(stops.take(visibleStopCount)) { index, stop ->
////////                        StopItem(stop, isCurrentStop = index == currentStopIndex)
////////                    }
////////                }
////////            }
////////
////////            // Buttons at the Bottom (Fixed)
////////            Row(
////////                modifier = Modifier
////////                    .fillMaxWidth()
////////                    .padding(bottom = 16.dp),
////////                horizontalArrangement = Arrangement.SpaceEvenly
////////            ) {
////////                Button(
////////                    onClick = {
////////                        currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
////////                    },
////////                    modifier = Modifier.width(160.dp)
////////                ) {
////////                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
////////                }
////////
////////                Button(
////////                    onClick = {
////////                        if (currentStopIndex < stops.lastIndex) {
////////                            currentStopIndex++
////////                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
////////                                visibleStopCount++
////////                            }
////////
////////                            // Scroll to the next stop
////////                            coroutineScope.launch {
////////                                listState.animateScrollToItem(currentStopIndex)
////////                            }
////////                        } else {
////////                            // Show "Final Destination Reached" message
////////                            coroutineScope.launch {
////////                                snackbarHostState.showSnackbar("You have reached your final destination!")
////////                            }
////////                        }
////////                    },
////////                    enabled = currentStopIndex < stops.lastIndex,
////////                    modifier = Modifier.width(160.dp),
////////                    colors = if (currentStopIndex == stops.lastIndex)
////////                        ButtonDefaults.buttonColors(containerColor = Color.Gray)
////////                    else
////////                        ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
////////                ) {
////////                    Text(text = "Next Stop")
////////                }
////////            }
////////        }
////////    }
////////}
////////
////////// Stop List Item with Highlight
////////@Composable
////////fun StopItem(stop: Stop, isCurrentStop: Boolean) {
////////    val backgroundColor = if (isCurrentStop) Color(0xFF64B5F6) else Color.White
////////    val textColor = if (isCurrentStop) Color.White else Color.Black
////////
////////    Card(
////////        modifier = Modifier
////////            .fillMaxWidth()
////////            .padding(8.dp)
////////            .background(backgroundColor),
////////        shape = RoundedCornerShape(12.dp),
////////        colors = CardDefaults.cardColors(containerColor = backgroundColor)
////////    ) {
////////        Column(modifier = Modifier.padding(12.dp)) {
////////            Text(text = stop.name, fontSize = 18.sp, color = textColor)
////////
////////            Text(
////////                text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}",
////////                fontSize = 14.sp,
////////                color = textColor
////////            )
////////
////////            Text(
////////                text = "Distance: ${convertDistance(stop.distanceFromPrevious, DistanceUnit.Km)}",
////////                fontSize = 14.sp,
////////                color = textColor
////////            )
////////        }
////////    }
////////}
////////
////////// Convert Distance Function
////////fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
////////    return if (unit == DistanceUnit.Miles) {
////////        "%.1f Miles".format(distanceKm * 0.621371)
////////    } else {
////////        "%.1f Km".format(distanceKm)
////////    }
////////}
//////
//////
//////
//////
//////
//////package com.example.app
//////
//////import android.content.Intent
//////import androidx.compose.foundation.background
//////import androidx.compose.foundation.clickable
//////import androidx.compose.foundation.layout.*
//////import androidx.compose.foundation.lazy.LazyColumn
//////import androidx.compose.foundation.lazy.LazyListState
//////import androidx.compose.foundation.lazy.itemsIndexed
//////import androidx.compose.foundation.lazy.rememberLazyListState
//////import androidx.compose.foundation.shape.RoundedCornerShape
//////import androidx.compose.material3.*
//////import androidx.compose.runtime.*
//////import androidx.compose.ui.Alignment
//////import androidx.compose.ui.Modifier
//////import androidx.compose.ui.draw.shadow
//////import androidx.compose.ui.graphics.Brush
//////import androidx.compose.ui.graphics.Color
//////import androidx.compose.ui.platform.LocalContext
//////import androidx.compose.ui.text.font.FontWeight
//////import androidx.compose.ui.unit.dp
//////import androidx.compose.ui.unit.sp
//////import kotlinx.coroutines.launch
//////
//////@Composable
//////fun EnhancedMainScreen(stops: List<Stop>) {
//////    var currentStopIndex by remember { mutableStateOf(0) }
//////    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
//////    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
//////    val context = LocalContext.current
//////
//////    val totalDistance = stops.sumOf { it.distanceFromPrevious }
//////    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
//////    val remainingDistance = totalDistance - coveredDistance
//////
//////    // Snackbar state
//////    val snackbarHostState = remember { SnackbarHostState() }
//////    val coroutineScope = rememberCoroutineScope()
//////
//////    // LazyColumn scrolling state
//////    val listState = rememberLazyListState()
//////
//////    // Gradient Background
//////    Box(
//////        modifier = Modifier
//////            .fillMaxSize()
//////            .background(
//////                Brush.verticalGradient(
//////                    colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6), Color.White),
//////                )
//////            )
//////            .padding(16.dp),
//////        contentAlignment = Alignment.TopCenter
//////    ) {
//////        Column(
//////            modifier = Modifier.fillMaxSize(),
//////            horizontalAlignment = Alignment.CenterHorizontally
//////        ) {
//////            // Snackbar Host
//////            SnackbarHost(hostState = snackbarHostState)
//////
//////            // Header
//////            Box(
//////                modifier = Modifier
//////                    .fillMaxWidth()
//////                    .height(60.dp)
//////                    .background(Color(0xFF0D47A1)),
//////                contentAlignment = Alignment.Center
//////            ) {
//////                Text(
//////                    text = "Journey Progress",
//////                    fontSize = 22.sp,
//////                    color = Color.White
//////                )
//////            }
//////
//////            Spacer(modifier = Modifier.height(10.dp))
//////
//////            // Progress Card
//////            Card(
//////                modifier = Modifier
//////                    .fillMaxWidth()
//////                    .padding(12.dp)
//////                    .shadow(8.dp, RoundedCornerShape(12.dp)),
//////                shape = RoundedCornerShape(12.dp),
//////                colors = CardDefaults.cardColors(containerColor = Color.White)
//////            ) {
//////                Column(
//////                    modifier = Modifier.padding(16.dp),
//////                    horizontalAlignment = Alignment.CenterHorizontally
//////                ) {
//////                    Text(text = "Distance Covered", fontSize = 18.sp, color = Color.DarkGray)
//////
//////                    Text(
//////                        text = "${convertDistance(coveredDistance, currentUnit)}",
//////                        fontSize = 24.sp,
//////                        color = Color(0xFF0D47A1)
//////                    )
//////
//////                    LinearProgressIndicator(
//////                        progress = { (coveredDistance / totalDistance).toFloat() },
//////                        modifier = Modifier
//////                            .fillMaxWidth()
//////                            .height(12.dp)
//////                            .padding(vertical = 8.dp),
//////                        color = Color(0xFF0D47A1)
//////                    )
//////
//////                    Text(text = "Remaining Distance", fontSize = 18.sp, color = Color.DarkGray)
//////
//////                    Text(
//////                        text = "${convertDistance(remainingDistance, currentUnit)}",
//////                        fontSize = 24.sp,
//////                        color = Color(0xFF0D47A1)
//////                    )
//////                }
//////            }
//////
//////            Spacer(modifier = Modifier.height(10.dp))
//////
//////            // Stop List with Auto-Scroll
//////            Box(
//////                modifier = Modifier
//////                    .weight(1f)
//////                    .fillMaxWidth()
//////                    .padding(bottom = 10.dp)
//////            ) {
//////                LazyColumn(
//////                    state = listState, // Attach scroll state
//////                    modifier = Modifier.fillMaxWidth()
//////                ) {
//////                    itemsIndexed(stops.take(visibleStopCount)) { index, stop ->
//////                        StopItem(stop, isCurrentStop = index == currentStopIndex)
//////                    }
//////                }
//////            }
//////
//////            // Buttons at the Bottom (Fixed)
//////            Row(
//////                modifier = Modifier
//////                    .fillMaxWidth()
//////                    .padding(bottom = 16.dp),
//////                horizontalArrangement = Arrangement.SpaceEvenly
//////            ) {
//////                Button(
//////                    onClick = {
//////                        currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
//////                    },
//////                    modifier = Modifier.width(160.dp)
//////                ) {
//////                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
//////                }
//////
//////                Button(
//////                    onClick = {
//////                        if (currentStopIndex < stops.lastIndex) {
//////                            currentStopIndex++
//////                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
//////                                visibleStopCount++
//////                            }
//////
//////                            // Scroll to the next stop
//////                            coroutineScope.launch {
//////                                listState.animateScrollToItem(currentStopIndex)
//////                            }
//////                        } else {
//////                            // Show "Final Destination Reached" message
//////                            coroutineScope.launch {
//////                                snackbarHostState.showSnackbar("You have reached your final destination!")
//////                            }
//////                        }
//////                    },
//////                    enabled = currentStopIndex < stops.lastIndex,
//////                    modifier = Modifier.width(160.dp),
//////                    colors = if (currentStopIndex == stops.lastIndex)
//////                        ButtonDefaults.buttonColors(containerColor = Color.Gray)
//////                    else
//////                        ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
//////                ) {
//////                    Text(text = "Next Stop")
//////                }
//////            }
//////        }
//////    }
//////}
//////
//////// Stop List Item with Highlight
//////@Composable
//////fun StopItem(stop: Stop, isCurrentStop: Boolean) {
//////    val backgroundColor = if (isCurrentStop) Color(0xFF64B5F6) else Color.White
//////    val textColor = if (isCurrentStop) Color.White else Color.Black
//////
//////    Card(
//////        modifier = Modifier
//////            .fillMaxWidth()
//////            .padding(8.dp)
//////            .shadow(if (isCurrentStop) 8.dp else 2.dp, RoundedCornerShape(12.dp)),
//////        shape = RoundedCornerShape(12.dp),
//////        colors = CardDefaults.cardColors(containerColor = backgroundColor)
//////    ) {
//////        Column(modifier = Modifier.padding(12.dp)) {
//////            Text(
//////                text = stop.name,
//////                fontSize = if (isCurrentStop) 20.sp else 18.sp,
//////                color = textColor,
//////                fontWeight = if (isCurrentStop) FontWeight.Bold else FontWeight.Normal
//////            )
//////
//////            Text(
//////                text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}",
//////                fontSize = 14.sp,
//////                color = textColor
//////            )
//////
//////            Text(
//////                text = "Distance: ${convertDistance(stop.distanceFromPrevious, DistanceUnit.Km)}",
//////                fontSize = 14.sp,
//////                color = textColor
//////            )
//////        }
//////    }
//////}
//////
//////// Convert Distance Function
//////fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
//////    return if (unit == DistanceUnit.Miles) {
//////        "%.1f Miles".format(distanceKm * 0.621371)
//////    } else {
//////        "%.1f Km".format(distanceKm)
//////    }
//////}
////
////
////
////
////
//////package com.example.app
//////
//////import android.content.Intent
//////import androidx.compose.foundation.background
//////import androidx.compose.foundation.clickable
//////import androidx.compose.foundation.layout.*
//////import androidx.compose.foundation.lazy.LazyColumn
//////import androidx.compose.foundation.lazy.itemsIndexed
//////import androidx.compose.foundation.lazy.rememberLazyListState
//////import androidx.compose.foundation.shape.RoundedCornerShape
//////import androidx.compose.material3.*
//////import androidx.compose.runtime.*
//////import androidx.compose.ui.Alignment
//////import androidx.compose.ui.Modifier
//////import androidx.compose.ui.draw.shadow
//////import androidx.compose.ui.graphics.Brush
//////import androidx.compose.ui.graphics.Color
//////import androidx.compose.ui.platform.LocalContext
//////import androidx.compose.ui.text.font.FontWeight
//////import androidx.compose.ui.unit.dp
//////import androidx.compose.ui.unit.sp
//////import kotlinx.coroutines.launch
//////
//////@Composable
//////fun EnhancedMainScreen(stops: List<Stop>) {
//////    var currentStopIndex by remember { mutableStateOf(0) }
//////    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
//////    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
//////    val context = LocalContext.current
//////
//////    val totalDistance = stops.sumOf { it.distanceFromPrevious }
//////    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
//////    val remainingDistance = totalDistance - coveredDistance
//////
//////    // Snackbar state
//////    val snackbarHostState = remember { SnackbarHostState() }
//////    val coroutineScope = rememberCoroutineScope()
//////
//////    // LazyColumn scrolling state
//////    val listState = rememberLazyListState()
//////
//////    // Gradient Background
//////    Box(
//////        modifier = Modifier
//////            .fillMaxSize()
//////            .background(
//////                Brush.verticalGradient(
//////                    colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6), Color.White),
//////                )
//////            )
//////            .padding(16.dp),
//////        contentAlignment = Alignment.TopCenter
//////    ) {
//////        Column(
//////            modifier = Modifier.fillMaxSize(),
//////            horizontalAlignment = Alignment.CenterHorizontally
//////        ) {
//////            // Snackbar Host
//////            SnackbarHost(hostState = snackbarHostState)
//////
//////            // Header
//////            Box(
//////                modifier = Modifier
//////                    .fillMaxWidth()
//////                    .height(60.dp)
//////                    .background(Color(0xFF0D47A1)),
//////                contentAlignment = Alignment.Center
//////            ) {
//////                Text(
//////                    text = "Journey Progress",
//////                    fontSize = 22.sp,
//////                    color = Color.White
//////                )
//////            }
//////
//////            Spacer(modifier = Modifier.height(10.dp))
//////
//////            // Progress Card
//////            Card(
//////                modifier = Modifier
//////                    .fillMaxWidth()
//////                    .padding(12.dp)
//////                    .shadow(8.dp, RoundedCornerShape(12.dp)),
//////                shape = RoundedCornerShape(12.dp),
//////                colors = CardDefaults.cardColors(containerColor = Color.White)
//////            ) {
//////                Column(
//////                    modifier = Modifier.padding(16.dp),
//////                    horizontalAlignment = Alignment.CenterHorizontally
//////                ) {
//////                    Text(text = "Distance Covered", fontSize = 18.sp, color = Color.DarkGray)
//////
//////                    Text(
//////                        text = "${convertDistance(coveredDistance, currentUnit)}",
//////                        fontSize = 24.sp,
//////                        color = Color(0xFF0D47A1)
//////                    )
//////
//////                    LinearProgressIndicator(
//////                        progress = { (coveredDistance / totalDistance).toFloat() },
//////                        modifier = Modifier
//////                            .fillMaxWidth()
//////                            .height(12.dp)
//////                            .padding(vertical = 8.dp),
//////                        color = Color(0xFF0D47A1)
//////                    )
//////
//////                    Text(text = "Remaining Distance", fontSize = 18.sp, color = Color.DarkGray)
//////
//////                    Text(
//////                        text = "${convertDistance(remainingDistance, currentUnit)}",
//////                        fontSize = 24.sp,
//////                        color = Color(0xFF0D47A1)
//////                    )
//////                }
//////            }
//////
//////            Spacer(modifier = Modifier.height(10.dp))
//////
//////            // Stop List with Auto-Scroll
//////            Box(
//////                modifier = Modifier
//////                    .weight(1f)
//////                    .fillMaxWidth()
//////                    .padding(bottom = 10.dp)
//////            ) {
//////                LazyColumn(
//////                    state = listState, // Attach scroll state
//////                    modifier = Modifier.fillMaxWidth()
//////                ) {
//////                    itemsIndexed(stops.take(visibleStopCount)) { index, stop ->
//////                        StopItem(stop = stop, isCurrentStop = index == currentStopIndex, unit = currentUnit)
//////                    }
//////                }
//////            }
//////
//////            // Buttons at the Bottom (Fixed)
//////            Row(
//////                modifier = Modifier
//////                    .fillMaxWidth()
//////                    .padding(bottom = 16.dp),
//////                horizontalArrangement = Arrangement.SpaceEvenly
//////            ) {
//////                Button(
//////                    onClick = {
//////                        currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
//////                    },
//////                    modifier = Modifier.width(160.dp)
//////                ) {
//////                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
//////                }
//////
//////                Button(
//////                    onClick = {
//////                        if (currentStopIndex < stops.lastIndex) {
//////                            currentStopIndex++
//////                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
//////                                visibleStopCount++
//////                            }
//////
//////                            // Scroll to the next stop
//////                            coroutineScope.launch {
//////                                listState.animateScrollToItem(currentStopIndex)
//////                            }
//////                        } else {
//////                            // Show "Final Destination Reached" message
//////                            coroutineScope.launch {
//////                                snackbarHostState.showSnackbar("You have reached your final destination!")
//////                            }
//////                        }
//////                    },
//////                    enabled = currentStopIndex < stops.lastIndex,
//////                    modifier = Modifier.width(160.dp),
//////                    colors = if (currentStopIndex == stops.lastIndex)
//////                        ButtonDefaults.buttonColors(containerColor = Color.Gray)
//////                    else
//////                        ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
//////                ) {
//////                    Text(text = "Next Stop")
//////                }
//////            }
//////        }
//////    }
//////}
//////
//////// Stop List Item with Highlight & Distance Toggle
//////@Composable
//////fun StopItem(stop: Stop, isCurrentStop: Boolean, unit: DistanceUnit) {
//////    val backgroundColor = if (isCurrentStop) Color(0xFF64B5F6) else Color.White
//////    val textColor = if (isCurrentStop) Color.White else Color.Black
//////
//////    Card(
//////        modifier = Modifier
//////            .fillMaxWidth()
//////            .padding(8.dp)
//////            .shadow(if (isCurrentStop) 8.dp else 2.dp, RoundedCornerShape(12.dp)),
//////        shape = RoundedCornerShape(12.dp),
//////        colors = CardDefaults.cardColors(containerColor = backgroundColor)
//////    ) {
//////        Column(modifier = Modifier.padding(12.dp)) {
//////            Text(
//////                text = stop.name,
//////                fontSize = if (isCurrentStop) 20.sp else 18.sp,
//////                color = textColor,
//////                fontWeight = if (isCurrentStop) FontWeight.Bold else FontWeight.Normal
//////            )
//////
//////            Text(
//////                text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}",
//////                fontSize = 14.sp,
//////                color = textColor
//////            )
//////
//////            Text(
//////                text = "Distance: ${convertDistance(stop.distanceFromPrevious, unit)}",
//////                fontSize = 14.sp,
//////                color = textColor
//////            )
//////        }
//////    }
//////}
//////
//////// Convert Distance Function
//////fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
//////    return if (unit == DistanceUnit.Miles) {
//////        "%.1f Miles".format(distanceKm * 0.621371)
//////    } else {
//////        "%.1f Km".format(distanceKm)
//////    }
//////}
////
////
////
////
////
////
////package com.example.app
////
////import android.content.Intent
////import androidx.compose.foundation.background
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.lazy.LazyColumn
////import androidx.compose.foundation.lazy.itemsIndexed
////import androidx.compose.foundation.lazy.rememberLazyListState
////import androidx.compose.foundation.shape.RoundedCornerShape
////import androidx.compose.material3.*
////import androidx.compose.runtime.*
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.draw.shadow
////import androidx.compose.ui.graphics.Brush
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.platform.LocalContext
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.unit.sp
////import kotlinx.coroutines.launch
////
////@Composable
////fun EnhancedMainScreen(stops: List<Stop>) {
////    var currentStopIndex by remember { mutableStateOf(0) }
////    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
////    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
////    val context = LocalContext.current
////
////    val totalDistance = stops.sumOf { it.distanceFromPrevious }
////    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
////    val remainingDistance = totalDistance - coveredDistance
////
////    // Snackbar state
////    val snackbarHostState = remember { SnackbarHostState() }
////    val coroutineScope = rememberCoroutineScope()
////
////    // LazyColumn scrolling state
////    val listState = rememberLazyListState()
////
////    Column(
////        modifier = Modifier
////            .fillMaxSize()
////            .background(
////                Brush.verticalGradient(
////                    colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6), Color.White),
////                )
////            )
////            .padding(16.dp),
////        horizontalAlignment = Alignment.CenterHorizontally
////    ) {
////        SnackbarHost(hostState = snackbarHostState)
////
////        // Header
////        Box(
////            modifier = Modifier
////                .fillMaxWidth()
////                .height(60.dp)
////                .background(Color(0xFF0D47A1)),
////            contentAlignment = Alignment.Center
////        ) {
////            Text(
////                text = "Journey Progress",
////                fontSize = 22.sp,
////                color = Color.White,
////                fontWeight = FontWeight.Bold
////            )
////        }
////
////        Spacer(modifier = Modifier.height(10.dp))
////
////        // Progress Card
////        Card(
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(12.dp)
////                .shadow(8.dp, RoundedCornerShape(12.dp)),
////            shape = RoundedCornerShape(12.dp),
////            colors = CardDefaults.cardColors(containerColor = Color.White)
////        ) {
////            Column(
////                modifier = Modifier.padding(16.dp),
////                horizontalAlignment = Alignment.CenterHorizontally
////            ) {
////                Text(text = "Distance Covered", fontSize = 18.sp, color = Color.DarkGray)
////
////                Text(
////                    text = convertDistance(coveredDistance, currentUnit),
////                    fontSize = 24.sp,
////                    color = Color(0xFF0D47A1),
////                    fontWeight = FontWeight.Bold
////                )
////
////                LinearProgressIndicator(
////                    progress = { (coveredDistance / totalDistance).toFloat() },
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .height(22.dp)
////                        .padding(vertical = 8.dp),
////                    color = Color(0xFF0D47A1)
////                )
////
////                Text(text = "Remaining Distance", fontSize = 18.sp, color = Color.DarkGray)
////
////                Text(
////                    text = convertDistance(remainingDistance, currentUnit),
////                    fontSize = 24.sp,
////                    color = Color(0xFF0D47A1),
////                    fontWeight = FontWeight.Bold
////                )
////            }
////        }
////
////        Spacer(modifier = Modifier.height(10.dp))
////
////        // Stop List with Auto-Scroll
////        Box(
////            modifier = Modifier
////                .weight(1f)
////                .fillMaxWidth()
////                .padding(bottom = 10.dp)
////        ) {
////            LazyColumn(
////                state = listState,
////                modifier = Modifier.fillMaxWidth()
////            ) {
////                itemsIndexed(stops.take(visibleStopCount)) { index, stop ->
////                    StopItem(stop, isCurrentStop = index == currentStopIndex, unit = currentUnit)
////                }
////            }
////        }
////
////        // Buttons at the Bottom (Fixed)
////        Row(
////            modifier = Modifier
////                .fillMaxWidth()
////                .padding(bottom = 16.dp),
////            horizontalArrangement = Arrangement.SpaceEvenly
////        ) {
////            Button(
////                onClick = {
////                    currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
////                },
////                modifier = Modifier.width(160.dp)
////            ) {
////                Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
////            }
////
////            Button(
////                onClick = {
////                    if (currentStopIndex < stops.lastIndex) {
////                        currentStopIndex++
////                        if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
////                            visibleStopCount++
////                        }
////
////                        coroutineScope.launch {
////                            listState.animateScrollToItem(currentStopIndex)
////                        }
////                    } else {
////                        coroutineScope.launch {
////                            snackbarHostState.showSnackbar("You have reached your final destination!")
////                        }
////                    }
////                },
////                enabled = currentStopIndex < stops.lastIndex,
////                modifier = Modifier.width(160.dp),
////                colors = if (currentStopIndex == stops.lastIndex)
////                    ButtonDefaults.buttonColors(containerColor = Color.Gray)
////                else
////                    ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
////            ) {
////                Text(text = "Next Stop")
////            }
////        }
////    }
////}
////
////// **Stop List Item with Highlight**
////@Composable
////fun StopItem(stop: Stop, isCurrentStop: Boolean, unit: DistanceUnit) {
////    val backgroundColor = if (isCurrentStop) Color(0xFF64B5F6) else Color.White
////    val textColor = if (isCurrentStop) Color.White else Color.Black
////
////    Card(
////        modifier = Modifier
////            .fillMaxWidth()
////            .padding(8.dp)
////            .shadow(if (isCurrentStop) 8.dp else 2.dp, RoundedCornerShape(12.dp)),
////        shape = RoundedCornerShape(12.dp),
////        colors = CardDefaults.cardColors(containerColor = backgroundColor)
////    ) {
////        Column(modifier = Modifier.padding(12.dp)) {
////            Text(
////                text = stop.name,
////                fontSize = if (isCurrentStop) 20.sp else 18.sp,
////                color = textColor,
////                fontWeight = if (isCurrentStop) FontWeight.Bold else FontWeight.Normal
////            )
////
////            Text(
////                text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}",
////                fontSize = 14.sp,
////                color = textColor
////            )
////
////            Text(
////                text = "Distance: ${convertDistance(stop.distanceFromPrevious, unit)}",
////                fontSize = 14.sp,
////                color = textColor
////            )
////        }
////    }
////}
////
////// **Convert Distance Function**
////fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
////    return if (unit == DistanceUnit.Miles) {
////        "%.1f Miles".format(distanceKm * 0.621371)
////    } else {
////        "%.1f Km".format(distanceKm)
////    }
////}
////
//
//
//
//package com.example.app
//
//import android.content.Intent
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import kotlinx.coroutines.launch
//
//@Composable
//fun EnhancedMainScreen(stops: List<Stop>) {
//    var currentStopIndex by remember { mutableStateOf(0) }
//    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
//    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
//    val context = LocalContext.current
//
//    val totalDistance = stops.sumOf { it.distanceFromPrevious }
//    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
//    val remainingDistance = totalDistance - coveredDistance
//
//    val snackbarHostState = remember { SnackbarHostState() }
//    val coroutineScope = rememberCoroutineScope()
//
//    val listState = rememberLazyListState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                Brush.verticalGradient(
//                    colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6), Color.White),
//                )
//            )
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        SnackbarHost(hostState = snackbarHostState)
//
//        // **Header with Back Arrow**
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp)
//                .background(Color(0xFF0D47A1)),
//            contentAlignment = Alignment.Center
//        ) {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                // **Back Arrow**
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
//                Spacer(modifier = Modifier.width(10.dp))
//
//                // **Title (Centered)**
//                Text(
//                    text = "Journey Progress",
//                    fontSize = 22.sp,
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.align(Alignment.CenterVertically)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        // Progress Card
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(12.dp)
//                .shadow(8.dp, RoundedCornerShape(12.dp)),
//            shape = RoundedCornerShape(12.dp),
//            colors = CardDefaults.cardColors(containerColor = Color.White)
//        ) {
//            Column(
//                modifier = Modifier.padding(16.dp),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                Text(text = "Distance Covered", fontSize = 18.sp, color = Color.DarkGray)
//
//                Text(
//                    text = convertDistance(coveredDistance, currentUnit),
//                    fontSize = 24.sp,
//                    color = Color(0xFF0D47A1),
//                    fontWeight = FontWeight.Bold
//                )
//
//                LinearProgressIndicator(
//                    progress = { (coveredDistance / totalDistance).toFloat() },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(12.dp)
//                        .padding(vertical = 8.dp),
//                    color = Color(0xFF0D47A1)
//                )
//
//                Text(text = "Remaining Distance", fontSize = 18.sp, color = Color.DarkGray)
//
//                Text(
//                    text = convertDistance(remainingDistance, currentUnit),
//                    fontSize = 24.sp,
//                    color = Color(0xFF0D47A1),
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(10.dp))
//
//        // Stop List with Auto-Scroll
//        Box(
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxWidth()
//                .padding(bottom = 10.dp)
//        ) {
//            LazyColumn(
//                state = listState,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                itemsIndexed(stops.take(visibleStopCount)) { index, stop ->
//                    StopItem(stop, isCurrentStop = index == currentStopIndex, unit = currentUnit)
//                }
//            }
//        }
//
//        // Buttons at the Bottom (Fixed)
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(bottom = 16.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            Button(
//                onClick = {
//                    currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
//                },
//                modifier = Modifier.width(160.dp)
//            ) {
//                Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
//            }
//
//            Button(
//                onClick = {
//                    if (currentStopIndex < stops.lastIndex) {
//                        currentStopIndex++
//                        if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
//                            visibleStopCount++
//                        }
//
//                        coroutineScope.launch {
//                            listState.animateScrollToItem(currentStopIndex)
//                        }
//                    } else {
//                        coroutineScope.launch {
//                            snackbarHostState.showSnackbar("You have reached your final destination!")
//                        }
//                    }
//                },
//                enabled = currentStopIndex < stops.lastIndex,
//                modifier = Modifier.width(160.dp),
//                colors = if (currentStopIndex == stops.lastIndex)
//                    ButtonDefaults.buttonColors(containerColor = Color.Gray)
//                else
//                    ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1))
//            ) {
//                Text(text = "Next Stop")
//            }
//        }
//    }
//}
//
//// **Stop List Item with Highlight**
//@Composable
//fun StopItem(stop: Stop, isCurrentStop: Boolean, unit: DistanceUnit) {
//    val backgroundColor = if (isCurrentStop) Color(0xFF64B5F6) else Color.White
//    val textColor = if (isCurrentStop) Color.White else Color.Black
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .shadow(if (isCurrentStop) 8.dp else 2.dp, RoundedCornerShape(12.dp)),
//        shape = RoundedCornerShape(12.dp),
//        colors = CardDefaults.cardColors(containerColor = backgroundColor)
//    ) {
//        Column(modifier = Modifier.padding(12.dp)) {
//            Text(
//                text = stop.name,
//                fontSize = if (isCurrentStop) 20.sp else 18.sp,
//                color = textColor,
//                fontWeight = if (isCurrentStop) FontWeight.Bold else FontWeight.Normal
//            )
//
//            Text(
//                text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}",
//                fontSize = 14.sp,
//                color = textColor
//            )
//
//            Text(
//                text = "Distance: ${convertDistance(stop.distanceFromPrevious, unit)}",
//                fontSize = 14.sp,
//                color = textColor
//            )
//        }
//    }
//}
//
//// **Convert Distance Function**
//fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
//    return if (unit == DistanceUnit.Miles) {
//        "%.1f Miles".format(distanceKm * 0.621371)
//    } else {
//        "%.1f Km".format(distanceKm)
//    }
//}



package com.example.app

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun EnhancedMainScreen(stops: List<Stop>) {
    var currentStopIndex by remember { mutableStateOf(0) }
    var currentUnit by remember { mutableStateOf(DistanceUnit.Km) }
    var visibleStopCount by remember { mutableStateOf(minOf(3, stops.size)) }
    val context = LocalContext.current

    val totalDistance = stops.sumOf { it.distanceFromPrevious }
    val coveredDistance = stops.subList(0, currentStopIndex + 1).sumOf { it.distanceFromPrevious }
    val remainingDistance = totalDistance - coveredDistance

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF1976D2), Color(0xFF64B5F6), Color.White),
                )
            )
    ) {
        // **Independent Header**
        // **Header with Back Arrow (Adjusted for Left Alignment)**
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFF0D47A1)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // **Back Arrow**
                Text(
                    text = "←",
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .clickable {
                            val intent = Intent(context, RouteSelectionActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            context.startActivity(intent)
                        }
                )

                // **Adding Spacer to Push Title Slightly Left**
                Spacer(modifier = Modifier.weight(0.9f))

                // **Title (Slightly Left-Aligned)**
                Text(
                    text = "Journey Progress",
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.weight(0.2f) // Adjust this value to fine-tune the left shift
                )

                // **Spacer to Balance the Title Shift**
                Spacer(modifier = Modifier.weight(1.1f))
            }

    }

        // **Independent Body Content** (No Effect from Header)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp), // Padding affects only the body
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SnackbarHost(hostState = snackbarHostState)

            Spacer(modifier = Modifier.height(10.dp))

            // Progress Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .shadow(8.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Distance Covered", fontSize = 18.sp, color = Color.DarkGray)

                    Text(
                        text = convertDistance(coveredDistance, currentUnit),
                        fontSize = 24.sp,
                        color = Color(0xFF0D47A1),
                        fontWeight = FontWeight.Bold
                    )

                    LinearProgressIndicator(
                        progress = { (coveredDistance / totalDistance).toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .padding(vertical = 8.dp),
                        color = Color(0xFF0D47A1)
                    )

                    Text(text = "Remaining Distance", fontSize = 18.sp, color = Color.DarkGray)

                    Text(
                        text = convertDistance(remainingDistance, currentUnit),
                        fontSize = 24.sp,
                        color = Color(0xFF0D47A1),
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Stop List with Auto-Scroll
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(stops.take(visibleStopCount)) { index, stop ->
                        StopItem(stop, isCurrentStop = index == currentStopIndex, unit = currentUnit)
                    }
                }
            }

            // Buttons at the Bottom (Fixed)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        currentUnit = if (currentUnit == DistanceUnit.Km) DistanceUnit.Miles else DistanceUnit.Km
                    },
                    modifier = Modifier.width(160.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0)) // **Lighter Blue**
                ) {
                    Text(text = "Switch to ${if (currentUnit == DistanceUnit.Km) "Miles" else "Km"}")
                }

                Button(
                    onClick = {
                        if (currentStopIndex < stops.lastIndex) {
                            currentStopIndex++
                            if (currentStopIndex >= 3 && visibleStopCount < stops.size) {
                                visibleStopCount++
                            }

                            coroutineScope.launch {
                                listState.animateScrollToItem(currentStopIndex)
                            }
                        } else {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("You have reached your final destination!")
                            }
                        }
                    },
                    enabled = currentStopIndex < stops.lastIndex,
                    modifier = Modifier.width(160.dp),
                    colors = if (currentStopIndex == stops.lastIndex)
                        ButtonDefaults.buttonColors(containerColor = Color.Magenta)
                    else
                        ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                ) {
                    Text(text = "Next Stop")
                }
            }
        }
    }
}

// **Stop List Item with Highlight**
@Composable
fun StopItem(stop: Stop, isCurrentStop: Boolean, unit: DistanceUnit) {
    val backgroundColor = if (isCurrentStop) Color(0xFF64B5F6) else Color.White
    val textColor = if (isCurrentStop) Color.White else Color.Black

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(if (isCurrentStop) 8.dp else 2.dp, RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = stop.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = textColor)
            Text(text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}", fontSize = 14.sp, color = textColor)
            Text(text = "Distance: ${convertDistance(stop.distanceFromPrevious, unit)}", fontSize = 14.sp, color = textColor)
        }
    }
}

// **Convert Distance Function**
fun convertDistance(distanceKm: Double, unit: DistanceUnit): String {
    return if (unit == DistanceUnit.Miles) {
        "%.1f Miles".format(distanceKm * 0.621371)
    } else {
        "%.1f Km".format(distanceKm)
    }
}
