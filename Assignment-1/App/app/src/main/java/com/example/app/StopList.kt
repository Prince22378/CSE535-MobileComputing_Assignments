package com.example.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StopItem(stop: Stop, isCurrentStop: Boolean, unit: DistanceUnit){
    val backGroundColor = if (isCurrentStop) Color.LightGray else Color.Transparent

    Column(
        modifier = Modifier.fillMaxWidth().background(backGroundColor).padding(12.dp)
    ) {
        Text(text = stop.name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "Visa Required: ${if (stop.visaRequired) "Yes" else "No"}",
            fontSize = 15.sp,
        )
        if(stop.distanceFromPrevious>0){
            Text(
                text = "Distance: ${convertDistance(stop.distanceFromPrevious,unit)}",
                fontSize = 15.sp,
            )
        }
    }
}

@Composable
fun StopList(stops: List<Stop>, currentStopIndex: Int, unit: DistanceUnit){
    LazyColumn(
        modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.surface).padding(8.dp)
    ) {
        itemsIndexed(stops) { _, stop ->
            StopItem(stop, stop == stops[currentStopIndex], unit)
        }
    }
}