package com.example.flighttrackerques1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flighttrackerques1.data.ApiClient
import com.example.flighttrackerques1.data.FlightData
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class FlightViewModel : ViewModel() {

    private val _flightData = MutableStateFlow<FlightData?>(null)
    val flightData: StateFlow<FlightData?> = _flightData

    private val _isTracking = MutableStateFlow(false)
    val isTracking: StateFlow<Boolean> = _isTracking

    private val _lastUpdated = MutableStateFlow<String?>(null)
    val lastUpdated: StateFlow<String?> = _lastUpdated

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading


    private var trackingJob: Job? = null

    fun trackFlight(flightIata: String) {
        if (_isTracking.value) return  // Prevent multiple tracking jobs

        _isTracking.value = true
//        _isLoading.value = true
        trackingJob = viewModelScope.launch {
            while (isActive) {
                try {
                    val response = ApiClient.api.getFlight("7349081e78b9e9e733b373b878e99951", flightIata)

                    if (response.data.isNotEmpty()) {
                        _flightData.value = response.data[0]
                        _lastUpdated.value = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                        _errorMessage.value = null
                        _isLoading.value = false
//                        delay(60_000)
//                        delay(60_000)
//                        trackFlight(flightIata)
                    } else {
                        _flightData.value = null
                        _errorMessage.value = "Please Check the Flight Number!"
                        stopTracking()
                    }
                } catch (e: Exception) {
//                    e.printStackTrace()
                    _flightData.value = null
                    _errorMessage.value = "Failed to fetch flight data. Check your Internet Connection."
                    stopTracking()
                }
                delay(60_000)
            }
        }
    }

    fun stopTracking() {
        trackingJob?.cancel()
        trackingJob = null
        _isTracking.value = false
        _isLoading.value = false
    }
}