//////////package com.example.app1
//////////
//////////import android.os.Bundle
//////////import android.widget.Button
//////////import android.widget.ProgressBar
//////////import android.widget.TextView
//////////import androidx.appcompat.app.AppCompatActivity
//////////import androidx.recyclerview.widget.LinearLayoutManager
//////////import androidx.recyclerview.widget.RecyclerView
//////////import java.io.BufferedReader
//////////import java.io.InputStreamReader
//////////import kotlin.math.roundToInt
//////////
//////////class MainActivity : AppCompatActivity() {
//////////
//////////    private lateinit var distanceCoveredText: TextView
//////////    private lateinit var distanceRemainingText: TextView
//////////    private lateinit var progressBar: ProgressBar
//////////    private lateinit var switchUnitButton: Button
//////////    private lateinit var nextStopButton: Button
//////////    private lateinit var stopRecyclerView: RecyclerView
//////////    private var stopList = mutableListOf<Stop>()
//////////    private var totalDistance = 0.0
//////////    private var coveredDistance = 0.0
//////////    private var isKm = true // Track unit
//////////
//////////    override fun onCreate(savedInstanceState: Bundle?) {
//////////        super.onCreate(savedInstanceState)
//////////        setContentView(R.layout.activity_main)
//////////
//////////        distanceCoveredText = findViewById(R.id.distanceCovered)
//////////        distanceRemainingText = findViewById(R.id.distanceRemaining)
//////////        progressBar = findViewById(R.id.progressBar)
//////////        switchUnitButton = findViewById(R.id.switchUnitButton)
//////////        nextStopButton = findViewById(R.id.nextStopButton)
//////////        stopRecyclerView = findViewById(R.id.stopList)
//////////
//////////        loadStopsFromFile()
//////////        setupRecyclerView()
//////////        updateUI()
//////////
//////////        switchUnitButton.setOnClickListener { toggleUnit() }
//////////        nextStopButton.setOnClickListener { reachNextStop() }
//////////    }
//////////
//////////    private fun loadStopsFromFile() {
//////////        val inputStream = resources.openRawResource(R.raw.stops)
//////////        val reader = BufferedReader(InputStreamReader(inputStream))
//////////        reader.forEachLine { line ->
//////////            val parts = line.split(";")
//////////            if (parts.size == 3) {
//////////                val stop = Stop(parts[0], parts[1].toBoolean(), parts[2].toDouble())
//////////                stopList.add(stop)
//////////                totalDistance += stop.distance
//////////            }
//////////        }
//////////        reader.close()
//////////    }
//////////
//////////    private fun setupRecyclerView() {
//////////        stopRecyclerView.layoutManager = LinearLayoutManager(this)
//////////        stopRecyclerView.adapter = StopAdapter(stopList)
//////////    }
//////////
//////////    private fun updateUI() {
//////////        val remainingDistance = totalDistance - coveredDistance
//////////        val progress = ((coveredDistance / totalDistance) * 100).roundToInt()
//////////
//////////        val unitText = if (isKm) "Km" else "Miles"
//////////        val conversionFactor = if (isKm) 1.0 else 0.621371
//////////
//////////        distanceCoveredText.text = "Distance Covered: ${(coveredDistance * conversionFactor).roundToInt()} $unitText"
//////////        distanceRemainingText.text = "Remaining Distance: ${(remainingDistance * conversionFactor).roundToInt()} $unitText"
//////////        progressBar.progress = progress
//////////    }
//////////
//////////    private fun toggleUnit() {
//////////        isKm = !isKm
//////////        switchUnitButton.text = if (isKm) "Switch to Miles" else "Switch to Km"
//////////        updateUI()
//////////    }
//////////
//////////    private fun reachNextStop() {
//////////        if (stopList.isNotEmpty()) {
//////////            coveredDistance += stopList.removeAt(0).distance
//////////            stopRecyclerView.adapter?.notifyDataSetChanged()
//////////            updateUI()
//////////        }
//////////    }
//////////}
////////
////////
////////package com.example.app1
////////
////////import android.graphics.Color
////////import android.os.Bundle
////////import android.widget.Button
////////import android.widget.ProgressBar
////////import android.widget.TextView
////////import androidx.appcompat.app.AppCompatActivity
////////import androidx.recyclerview.widget.LinearLayoutManager
////////import androidx.recyclerview.widget.RecyclerView
////////import java.io.BufferedReader
////////import java.io.InputStreamReader
////////import kotlin.math.roundToInt
////////
////////class MainActivity : AppCompatActivity() {
////////
////////    private lateinit var distanceCoveredText: TextView
////////    private lateinit var distanceRemainingText: TextView
////////    private lateinit var progressBar: ProgressBar
////////    private lateinit var switchUnitButton: Button
////////    private lateinit var nextStopButton: Button
////////    private lateinit var stopRecyclerView: RecyclerView
////////    private lateinit var stopAdapter: StopAdapter
////////    private var stopList = mutableListOf<Stop>()
////////    private var totalDistance = 0.0
////////    private var coveredDistance = 0.0
////////    private var isKm = true // Track unit
////////    private var currentStopIndex = 0
////////
////////    override fun onCreate(savedInstanceState: Bundle?) {
////////        super.onCreate(savedInstanceState)
////////        setContentView(R.layout.activity_main)
////////
////////        distanceCoveredText = findViewById(R.id.distanceCovered)
////////        distanceRemainingText = findViewById(R.id.distanceRemaining)
////////        progressBar = findViewById(R.id.progressBar)
////////        switchUnitButton = findViewById(R.id.switchUnitButton)
////////        nextStopButton = findViewById(R.id.nextStopButton)
////////        stopRecyclerView = findViewById(R.id.stopList)
////////
////////        loadStopsFromFile()
////////        setupRecyclerView()
////////        updateUI()
////////
////////        switchUnitButton.setOnClickListener { toggleUnit() }
////////        nextStopButton.setOnClickListener { reachNextStop() }
////////    }
////////
////////    private fun loadStopsFromFile() {
////////        val inputStream = resources.openRawResource(R.raw.stops)
////////        val reader = BufferedReader(InputStreamReader(inputStream))
////////        reader.forEachLine { line ->
////////            val parts = line.split(";")
////////            if (parts.size == 3) {
////////                val stop = Stop(parts[0], parts[1].toBoolean(), parts[2].toDouble())
////////                stopList.add(stop)
////////                totalDistance += stop.distance
////////            }
////////        }
////////        reader.close()
////////    }
////////
////////    private fun setupRecyclerView() {
////////        stopRecyclerView.layoutManager = LinearLayoutManager(this)
////////        stopAdapter = StopAdapter(stopList)
////////        stopRecyclerView.adapter = stopAdapter
////////    }
////////
////////    private fun updateUI() {
////////        val remainingDistance = totalDistance - coveredDistance
////////        val progress = ((coveredDistance / totalDistance) * 100).roundToInt()
////////
////////        val unitText = if (isKm) "Km" else "Miles"
////////        val conversionFactor = if (isKm) 1.0 else 0.621371
////////
////////        distanceCoveredText.text = "Distance Covered: ${(coveredDistance * conversionFactor).roundToInt()} $unitText"
////////        distanceRemainingText.text = "Remaining Distance: ${(remainingDistance * conversionFactor).roundToInt()} $unitText"
////////        progressBar.progress = progress
////////    }
////////
////////    private fun toggleUnit() {
////////        isKm = !isKm
////////        switchUnitButton.text = if (isKm) "Switch to Miles" else "Switch to Km"
////////        updateUI()
////////    }
////////
////////    private fun reachNextStop() {
////////        if (currentStopIndex < stopList.size) {
////////            coveredDistance += stopList[currentStopIndex].distance
////////            stopAdapter.highlightStop(currentStopIndex)
////////            currentStopIndex++
////////            updateUI()
////////        }
////////    }
////////}
//////
//////
//////package com.example.app1
//////
//////import android.graphics.Color
//////import android.os.Bundle
//////import android.widget.Button
//////import android.widget.ProgressBar
//////import android.widget.TextView
//////import androidx.appcompat.app.AppCompatActivity
//////import androidx.recyclerview.widget.LinearLayoutManager
//////import androidx.recyclerview.widget.RecyclerView
//////import java.io.BufferedReader
//////import java.io.InputStreamReader
//////import kotlin.math.roundToInt
//////
//////class MainActivity : AppCompatActivity() {
//////
//////    private lateinit var distanceCoveredText: TextView
//////    private lateinit var distanceRemainingText: TextView
//////    private lateinit var progressBar: ProgressBar
//////    private lateinit var switchUnitButton: Button
//////    private lateinit var nextStopButton: Button
//////    private lateinit var stopRecyclerView: RecyclerView
//////    private lateinit var stopAdapter: StopAdapter
//////    private var stopList = mutableListOf<Stop>()
//////    private var visibleStops = mutableListOf<Stop>()
//////    private var totalDistance = 0.0
//////    private var coveredDistance = 0.0
//////    private var isKm = true // Track unit
//////    private var currentStopIndex = 0
//////
//////    override fun onCreate(savedInstanceState: Bundle?) {
//////        super.onCreate(savedInstanceState)
//////        setContentView(R.layout.activity_main)
//////
//////        distanceCoveredText = findViewById(R.id.distanceCovered)
//////        distanceRemainingText = findViewById(R.id.distanceRemaining)
//////        progressBar = findViewById(R.id.progressBar)
//////        switchUnitButton = findViewById(R.id.switchUnitButton)
//////        nextStopButton = findViewById(R.id.nextStopButton)
//////        stopRecyclerView = findViewById(R.id.stopList)
//////
//////        loadStopsFromFile()
//////        setupRecyclerView()
//////        updateUI()
//////
//////        switchUnitButton.setOnClickListener { toggleUnit() }
//////        nextStopButton.setOnClickListener { reachNextStop() }
//////    }
//////
//////    private fun loadStopsFromFile() {
//////        val inputStream = resources.openRawResource(R.raw.stops)
//////        val reader = BufferedReader(InputStreamReader(inputStream))
//////        reader.forEachLine { line ->
//////            val parts = line.split(";")
//////            if (parts.size == 3) {
//////                val stop = Stop(parts[0], parts[1].toBoolean(), parts[2].toDouble())
//////                stopList.add(stop)
//////                totalDistance += stop.distance
//////            }
//////        }
//////        reader.close()
//////        visibleStops.addAll(stopList.take(3))
//////    }
//////
//////    private fun setupRecyclerView() {
//////        stopRecyclerView.layoutManager = LinearLayoutManager(this)
//////        stopAdapter = StopAdapter(visibleStops)
//////        stopRecyclerView.adapter = stopAdapter
//////    }
//////
//////    private fun updateUI() {
//////        val remainingDistance = totalDistance - coveredDistance
//////        val progress = ((coveredDistance / totalDistance) * 100).roundToInt()
//////
//////        val unitText = if (isKm) "Km" else "Miles"
//////        val conversionFactor = if (isKm) 1.0 else 0.621371
//////
//////        distanceCoveredText.text = "Distance Covered: ${(coveredDistance * conversionFactor).roundToInt()} $unitText"
//////        distanceRemainingText.text = "Remaining Distance: ${(remainingDistance * conversionFactor).roundToInt()} $unitText"
//////        progressBar.progress = progress
//////    }
//////
//////    private fun toggleUnit() {
//////        isKm = !isKm
//////        switchUnitButton.text = if (isKm) "Switch to Miles" else "Switch to Km"
//////        updateUI()
//////    }
//////
//////    private fun reachNextStop() {
//////        if (currentStopIndex < stopList.size) {
//////            coveredDistance += stopList[currentStopIndex].distance
//////            stopAdapter.highlightStop(currentStopIndex)
//////            currentStopIndex++
//////            if (currentStopIndex < stopList.size) {
//////                visibleStops.add(stopList[currentStopIndex])
//////                stopAdapter.notifyItemInserted(visibleStops.size - 1)
//////            }
//////            updateUI()
//////        }
//////    }
//////}
//////
////
////
////package com.example.app1
////
////import android.graphics.Color
////import android.os.Bundle
////import android.widget.Button
////import android.widget.ProgressBar
////import android.widget.TextView
////import androidx.appcompat.app.AppCompatActivity
////import androidx.recyclerview.widget.LinearLayoutManager
////import androidx.recyclerview.widget.RecyclerView
////import java.io.BufferedReader
////import java.io.InputStreamReader
////import kotlin.math.roundToInt
////
////class MainActivity : AppCompatActivity() {
////
////    private lateinit var distanceCoveredText: TextView
////    private lateinit var distanceRemainingText: TextView
////    private lateinit var progressBar: ProgressBar
////    private lateinit var switchUnitButton: Button
////    private lateinit var nextStopButton: Button
////    private lateinit var stopRecyclerView: RecyclerView
////    private lateinit var stopAdapter: StopAdapter
////    private var stopList = mutableListOf<Stop>()
////    private var visibleStops = mutableListOf<Stop>()
////    private var totalDistance = 0.0
////    private var coveredDistance = 0.0
////    private var isKm = true // Track unit
////    private var currentStopIndex = 0
////
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_main)
////
////        distanceCoveredText = findViewById(R.id.distanceCovered)
////        distanceRemainingText = findViewById(R.id.distanceRemaining)
////        progressBar = findViewById(R.id.progressBar)
////        switchUnitButton = findViewById(R.id.switchUnitButton)
////        nextStopButton = findViewById(R.id.nextStopButton)
////        stopRecyclerView = findViewById(R.id.stopList)
////
////        loadStopsFromFile()
////        setupRecyclerView()
////        updateUI()
////
////        switchUnitButton.setOnClickListener { toggleUnit() }
////        nextStopButton.setOnClickListener { reachNextStop() }
////    }
////
////    private fun loadStopsFromFile() {
////        val inputStream = resources.openRawResource(R.raw.stops)
////        val reader = BufferedReader(InputStreamReader(inputStream))
////        reader.forEachLine { line ->
////            val parts = line.split(";")
////            if (parts.size == 3) {
////                val stop = Stop(parts[0], parts[1].toBoolean(), parts[2].toDouble())
////                stopList.add(stop)
////                totalDistance += stop.distance
////            }
////        }
////        reader.close()
////        visibleStops.addAll(stopList.take(3))
////    }
////
////    private fun setupRecyclerView() {
////        stopRecyclerView.layoutManager = LinearLayoutManager(this)
////        stopAdapter = StopAdapter(visibleStops)
////        stopRecyclerView.adapter = stopAdapter
////    }
////
////    private fun updateUI() {
////        val remainingDistance = totalDistance - coveredDistance
////        val progress = ((coveredDistance / totalDistance) * 100).roundToInt()
////
////        val unitText = if (isKm) "Km" else "Miles"
////        val conversionFactor = if (isKm) 1.0 else 0.621371
////
////        distanceCoveredText.text = "Distance Covered: ${(coveredDistance * conversionFactor).roundToInt()} $unitText"
////        distanceRemainingText.text = "Remaining Distance: ${(remainingDistance * conversionFactor).roundToInt()} $unitText"
////        progressBar.progress = progress
////
////        // Disable next stop button if there are no more stops
////        nextStopButton.isEnabled = currentStopIndex < stopList.size
////    }
////
////    private fun toggleUnit() {
////        isKm = !isKm
////        switchUnitButton.text = if (isKm) "Switch to Miles" else "Switch to Km"
////        updateUI()
////    }
////
////    private fun reachNextStop() {
////        if (currentStopIndex < stopList.size) {
////            coveredDistance += stopList[currentStopIndex].distance
////            stopAdapter.highlightStop(currentStopIndex)
////            currentStopIndex++
////            if (currentStopIndex < stopList.size && !visibleStops.contains(stopList[currentStopIndex])) {
////                visibleStops.add(stopList[currentStopIndex])
////                stopAdapter.notifyItemInserted(visibleStops.size - 1)
////            }
////            updateUI()
////        }
////    }
////}
//
//
//
//
//
//package com.example.app1
//
//import android.os.Bundle
//import android.widget.Button
//import android.widget.ProgressBar
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import java.io.BufferedReader
//import java.io.InputStreamReader
//import kotlin.math.roundToInt
//
//class MainActivity : AppCompatActivity() {
//
//    private lateinit var distanceCoveredText: TextView
//    private lateinit var distanceRemainingText: TextView
//    private lateinit var progressBar: ProgressBar
//    private lateinit var switchUnitButton: Button
//    private lateinit var nextStopButton: Button
//    private lateinit var stopRecyclerView: RecyclerView
//    private lateinit var stopAdapter: StopAdapter
//    private var stopList = mutableListOf<Stop>()
//    private var visibleStops = mutableListOf<Stop>()
//    private var totalDistance = 0.0
//    private var coveredDistance = 0.0
//    private var isKm = true
//    private var currentStopIndex = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        distanceCoveredText = findViewById(R.id.distanceCovered)
//        distanceRemainingText = findViewById(R.id.distanceRemaining)
//        progressBar = findViewById(R.id.progressBar)
//        switchUnitButton = findViewById(R.id.switchUnitButton)
//        nextStopButton = findViewById(R.id.nextStopButton)
//        stopRecyclerView = findViewById(R.id.stopList)
//
//        loadStopsFromFile()
//        setupRecyclerView()
//        updateUI()
//
//        switchUnitButton.setOnClickListener { toggleUnit() }
//        nextStopButton.setOnClickListener { reachNextStop() }
//    }
//
//    private fun loadStopsFromFile() {
//        val inputStream = resources.openRawResource(R.raw.stops)
//        val reader = BufferedReader(InputStreamReader(inputStream))
//        reader.forEachLine { line ->
//            val parts = line.split(";")
//            if (parts.size == 3) {
//                val distance = parts[2].toDouble()
//                val stop = Stop(parts[0], parts[1].toBoolean(), distance)
//                stopList.add(stop)
//                totalDistance += distance
//            }
//        }
//        reader.close()
//        visibleStops.addAll(stopList.take(3))
//    }
//
//    private fun setupRecyclerView() {
//        stopRecyclerView.layoutManager = LinearLayoutManager(this)
//        stopAdapter = StopAdapter(visibleStops, isKm)
//        stopRecyclerView.adapter = stopAdapter
//    }
//
//    private fun updateUI() {
//        val remainingDistance = totalDistance - coveredDistance
//        val progress = ((coveredDistance / totalDistance) * 100).roundToInt()
//
//        val unitText = if (isKm) "Km" else "Miles"
//        val conversionFactor = if (isKm) 1.0 else 0.621371
//
//        distanceCoveredText.text = "Distance Covered: ${(coveredDistance * conversionFactor).roundToInt()} $unitText"
//        distanceRemainingText.text = "Remaining Distance: ${(remainingDistance * conversionFactor).roundToInt()} $unitText"
//        progressBar.progress = progress
//
//        stopAdapter.updateUnit(isKm)
//
//        nextStopButton.isEnabled = currentStopIndex < stopList.size
//    }
//
//    private fun toggleUnit() {
//        isKm = !isKm
//        switchUnitButton.text = if (isKm) "Switch to Miles" else "Switch to Km"
//        updateUI()
//    }
//
//    private fun reachNextStop() {
//        if (currentStopIndex < stopList.size) {
//            coveredDistance += stopList[currentStopIndex].distance
//            stopAdapter.setHighlightedStop(currentStopIndex)
//            currentStopIndex++
//            if (currentStopIndex < stopList.size && !visibleStops.contains(stopList[currentStopIndex])) {
//                visibleStops.add(stopList[currentStopIndex])
//                stopAdapter.notifyItemInserted(visibleStops.size - 1)
//            }
//            updateUI()
//        }
//    }
//}



package com.example.app1

import com.example.app1.StopAdapter
import android.graphics.Color
import android.os.Bundle
import androidx.cardview.widget.CardView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private lateinit var distanceCoveredText: TextView
    private lateinit var distanceRemainingText: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var switchUnitButton: Button
    private lateinit var nextStopButton: Button
    private lateinit var stopRecyclerView: RecyclerView
    private lateinit var stopAdapter: StopAdapter
    private var stopList = mutableListOf<Stop>()
    private var visibleStops = mutableListOf<Stop>()
    private var totalDistance = 0.0
    private var coveredDistance = 0.0
    private var isKm = true
    private var currentStopIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        distanceCoveredText = findViewById(R.id.distanceCovered)
        distanceRemainingText = findViewById(R.id.distanceRemaining)
        progressBar = findViewById(R.id.progressBar)
        switchUnitButton = findViewById(R.id.switchUnitButton)
        nextStopButton = findViewById(R.id.nextStopButton)
        stopRecyclerView = findViewById(R.id.stopList)

        stopRecyclerView.layoutManager = LinearLayoutManager(this)
        stopRecyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        stopRecyclerView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.background_light))

        loadStopsFromFile()
        setupRecyclerView()
        updateUI()

        switchUnitButton.setOnClickListener { toggleUnit() }
        nextStopButton.setOnClickListener { reachNextStop() }
    }

    private fun loadStopsFromFile() {
        val inputStream = resources.openRawResource(R.raw.stops)
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.forEachLine { line ->
            val parts = line.split(";")
            if (parts.size == 3) {
                val distance = parts[2].toDouble()
                val stop = Stop(parts[0], parts[1].toBoolean(), distance)
                stopList.add(stop)
                totalDistance += distance
            }
        }
        reader.close()
        visibleStops.addAll(stopList.take(3))
    }

    private fun setupRecyclerView() {
        stopAdapter = StopAdapter(visibleStops, isKm)
        stopRecyclerView.adapter = stopAdapter
    }

    private fun updateUI() {
        val remainingDistance = totalDistance - coveredDistance
        val progress = ((coveredDistance / totalDistance) * 100).roundToInt()

        val unitText = if (isKm) "Km" else "Miles"
        val conversionFactor = if (isKm) 1.0 else 0.621371

        distanceCoveredText.text = "Distance Covered: ${(coveredDistance * conversionFactor).roundToInt()} $unitText"
        distanceRemainingText.text = "Remaining Distance: ${(remainingDistance * conversionFactor).roundToInt()} $unitText"
        progressBar.progress = progress

        stopAdapter.updateUnit(isKm)
        nextStopButton.isEnabled = currentStopIndex < stopList.size
    }

    private fun toggleUnit() {
        isKm = !isKm
        switchUnitButton.text = if (isKm) "Switch to Miles" else "Switch to Km"
        updateUI()
    }
    private fun reachNextStop() {
        if (currentStopIndex < stopList.size) {
            coveredDistance += stopList[currentStopIndex].distance
            stopAdapter.setHighlightedStop(currentStopIndex)
            currentStopIndex++
            if (currentStopIndex < stopList.size && !visibleStops.contains(stopList[currentStopIndex])) {
                visibleStops.add(stopList[currentStopIndex])
                stopAdapter.notifyItemInserted(visibleStops.size - 1)
            }
            updateUI()
        }
    }
}