package com.example.smartband.influx

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner

class ReportViewModel : ViewModel() {

    private val flaskUrl = "https://89c24cf9e43f.ngrok-free.app"
    private val _temperatureData = mutableStateOf<List<Float>>(emptyList())
    val temperatureData: State<List<Float>> = _temperatureData

    private val _hrData = mutableStateOf<List<Float>>(emptyList())
    val heartrateData: State<List<Float>> = _hrData

    init {
        fetchReportDataPeriodically()
    }

    private fun fetchReportDataPeriodically() {
        viewModelScope.launch {
            while (true) {
                weeklyReport()
                delay(5000) // refresh every 5 seconds
            }
        }
    }

    suspend fun weeklyReport() {
        withContext(Dispatchers.IO) {
            println("🌐 Weekly report connect")

            var conn: HttpURLConnection? = null
            try {
                val url = URL("$flaskUrl/api/weekly_report")
                conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 5000
                conn.readTimeout = 5000

                val responseCode = conn.responseCode
                println("Weekly Report connecting: $url")
                println("✅ Report response code: $responseCode")

                if (responseCode == 200) {
                    val response = Scanner(conn.inputStream).useDelimiter("\\A").next()
                    println("✅ Response from Flask: $response")

                    val jsonArray = JSONArray(response)
                    val tempList = mutableListOf<Float>()
                    val hrList = mutableListOf<Float>()

                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)
                        val temp = obj.optDouble("temp", Double.NaN)
                        val heartRate = obj.optDouble("heart_rate", Double.NaN)

                        if (!temp.isNaN() && !heartRate.isNaN()) {
                            tempList.add(temp.toFloat())
                            hrList.add(heartRate.toFloat())
                        }
                    }

                    // Update State on Main thread
                    withContext(Dispatchers.Main) {
                        _temperatureData.value = tempList
                        _hrData.value = hrList
                    }
                } else {
                    println("❌ Failed to connect: $responseCode")
                }

            } catch (e: Exception) {
                println("❌ Failed to initialize connection: ${e.message}")
                e.printStackTrace()
            } finally {
                conn?.disconnect()
            }
        }
    }
}
