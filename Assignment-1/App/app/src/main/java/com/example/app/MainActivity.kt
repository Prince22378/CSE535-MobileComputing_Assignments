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
