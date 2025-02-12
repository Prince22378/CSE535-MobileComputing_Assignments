//package com.example.app
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.ui.Modifier
//import com.example.app.ui.theme.AppTheme
//import java.io.BufferedReader
//import android.os.Parcel
//import android.os.Parcelable
//
//enum class DistanceUnit {
//    Km,
//    Miles
//}
//
////data class Stop(
////    val name: String,
////    val visaRequired: Boolean,
////    val distanceFromPrevious: Double
////)
//
//
//
//
//data class Stop(
//    val name: String,
//    val visaRequired: Boolean,
//    val distanceFromPrevious: Double
//) : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readString() ?: "",
//        parcel.readByte() != 0.toByte(),
//        parcel.readDouble()
//    )
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(name)
//        parcel.writeByte(if (visaRequired) 1 else 0)
//        parcel.writeDouble(distanceFromPrevious)
//    }
//
//    override fun describeContents(): Int = 0
//
//    companion object CREATOR : Parcelable.Creator<Stop> {
//        override fun createFromParcel(parcel: Parcel): Stop = Stop(parcel)
//        override fun newArray(size: Int): Array<Stop?> = arrayOfNulls(size)
//    }
//}
//
//
//
//class MainActivity : ComponentActivity() {
//    private fun loadStopsFromFile(): List<Stop>{
//        val stopList = mutableListOf<Stop>()
//        val inputStream = resources.openRawResource(R.raw.stops)
//        BufferedReader(inputStream.reader()).useLines { lines ->
//            lines.forEach { line ->
//                val parts = line.split(";")
//                if(parts.size == 3){
//                    stopList.add(
//                        Stop(
//                            name = parts[0],
//                            visaRequired = parts[1].toBoolean(),
//                            distanceFromPrevious = parts[2].toDoubleOrNull() ?: 0.0
//                        )
//                    )
//                }
//            }
//        }
//        return stopList
//    }
//
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        val stops = loadStopsFromFile()
////        setContent {
////            AppTheme {
////                Surface(
////                    modifier = Modifier.fillMaxSize(),
////                    color = MaterialTheme.colorScheme.background
////                ) {
////                    Application(stops)
////                }
////            }
////        }
////    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val selectedStops: List<Stop>? = intent.getParcelableArrayListExtra("SELECTED_STOPS")
//
//        if (selectedStops.isNullOrEmpty()) {
//            finish() // Close if no route is selected
//            return
//        }
//
//        setContent {
//            AppTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Application(selectedStops)
//                }
//            }
//        }
//    }
//
//}


//package com.example.app
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.app.ui.theme.AppTheme
//import java.io.BufferedReader
//
//enum class DistanceUnit {
//    Km,
//    Miles
//}
//
//data class Stop(
//    val name: String,
//    val visaRequired: Boolean,
//    val distanceFromPrevious: Double
//) : android.os.Parcelable {
//    constructor(parcel: android.os.Parcel) : this(
//        parcel.readString() ?: "",
//        parcel.readByte() != 0.toByte(),
//        parcel.readDouble()
//    )
//
//    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
//        parcel.writeString(name)
//        parcel.writeByte(if (visaRequired) 1 else 0)
//        parcel.writeDouble(distanceFromPrevious)
//    }
//
//    override fun describeContents(): Int = 0
//
//    companion object CREATOR : android.os.Parcelable.Creator<Stop> {
//        override fun createFromParcel(parcel: android.os.Parcel): Stop = Stop(parcel)
//        override fun newArray(size: Int): Array<Stop?> = arrayOfNulls(size)
//    }
//}
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val selectedStops: List<Stop>? = intent.getParcelableArrayListExtra("SELECTED_STOPS")
//
//        if (selectedStops.isNullOrEmpty()) {
//            finish() // Close if no route is selected
//            return
//        }
//
//        setContent {
//            AppTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    MainScreen(selectedStops) {
//                        // Navigate back to RouteSelectionActivity
//                        val intent = Intent(this, RouteSelectionActivity::class.java)
//                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
//                        startActivity(intent)
//                        finish() // Close MainActivity
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun MainScreen(stops: List<Stop>, onBackClick: () -> Unit) {
//    Column(
//        modifier = Modifier.fillMaxSize().padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        // Back Button at the Top
//        Button(
//            onClick = onBackClick,
//            modifier = Modifier.align(Alignment.Start)
//        ) {
//            Text("Back")
//        }
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        // Load the main application UI with the selected stops
//        Application(stops)
//    }
//}


package com.example.app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.app.ui.theme.AppTheme

enum class DistanceUnit {
    Km,
    Miles
}

data class Stop(
    val name: String,
    val visaRequired: Boolean,
    val distanceFromPrevious: Double
) : android.os.Parcelable {
    constructor(parcel: android.os.Parcel) : this(
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeByte(if (visaRequired) 1 else 0)
        parcel.writeDouble(distanceFromPrevious)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : android.os.Parcelable.Creator<Stop> {
        override fun createFromParcel(parcel: android.os.Parcel): Stop = Stop(parcel)
        override fun newArray(size: Int): Array<Stop?> = arrayOfNulls(size)
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedStops: List<Stop>? = intent.getParcelableArrayListExtra("SELECTED_STOPS")

        if (selectedStops.isNullOrEmpty()) {
            finish() // Close if no route is selected
            return
        }

        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(selectedStops)
                }
            }
        }
    }
}

@Composable
fun MainScreen(stops: List<Stop>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Load the main application UI with the selected stops
        Application(stops)
    }
}
