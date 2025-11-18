package com.example.smartband.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SensorViewModel : ViewModel(){

    // Function to send data to Flask API (and InfluxDB)
    fun sendDataToInflux(value: Double) {
        viewModelScope.launch { // runs code in background
            try {
                val response = RetrofitClient.api.sendData(SensorData(value))
                println(response.message)  // or update a LiveData/State
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    // Function to fetch data from InfluxDB via Flask
    fun fetchInfluxData() {
        viewModelScope.launch {
            try {
                val data = RetrofitClient.api.getData()
                println("Influx Data: $data")  // you can store this in a variable
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}