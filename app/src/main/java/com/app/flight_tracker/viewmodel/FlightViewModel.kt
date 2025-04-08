package com.app.flight_tracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.flight_tracker.data.ApiClient
import com.app.flight_tracker.data.FlightData
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

    private val _hasFetchedOnce = MutableStateFlow(false)
    val hasFetchedOnce: StateFlow<Boolean> = _hasFetchedOnce

    private var trackingJob: Job? = null

    fun trackFlight(flightIata: String) {
        if (_isTracking.value) return  // Prevent multiple tracking jobs

        _isTracking.value = true
        _hasFetchedOnce.value = false
        trackingJob = viewModelScope.launch {
            while (isActive) {
                try {
                    val response = ApiClient.api.getFlight("9d05fe1fff997194d6241d90febd2792", flightIata)
                    _hasFetchedOnce.value = true
                    if (response.data.isNotEmpty()) {
                        _flightData.value = response.data[0]
                    } else {
                        _flightData.value = null
                    }
                    _lastUpdated.value = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
                } catch (e: Exception) {
                    e.printStackTrace()
                    _flightData.value = null
                    _hasFetchedOnce.value = true
                }
                delay(60_000)
            }
        }
    }

    fun stopTracking() {
        trackingJob?.cancel()
        trackingJob = null
        _isTracking.value = false
    }
}