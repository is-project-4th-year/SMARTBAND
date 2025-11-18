package com.example.smartband.influx

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner

data class SensorReading(
    val time: String,
    val heartRate: Double?,
    val temp: Double?,
    val accX: Double?,
    val accY: Double?,
    val accZ: Double?,
    val emotion: String,
    val magnitude: Double?,
    val status: String
)

class InfluxViewModel : ViewModel() {

    // Replace with your Flask server IP and endpoint
    private val flaskUrl = "https://89c24cf9e43f.ngrok-free.app"

    private val _sensorData = MutableStateFlow<List<SensorReading>>(emptyList())
    val sensorData: StateFlow<List<SensorReading>> = _sensorData


    private val _emotionData = MutableStateFlow<Map<String, Int>>(emptyMap())
    val emotionData: StateFlow<Map<String, Int>> = _emotionData

    private val _temperatureData = mutableStateOf<List<Pair<String, Float>>>(emptyList())
    val temperatureData: State<List<Pair<String, Float>>> = _temperatureData


    init {
        fetchDataPeriodically()
        // fetchEmotionFrequencyPeriodically()
    }

    private fun fetchDataPeriodically() {
        viewModelScope.launch {
            while (true) {
                getInfluxData()
                delay(5000) // refresh every 5 seconds
            }
        }
    }

    suspend fun getInfluxData() {
        viewModelScope.launch(Dispatchers.IO) {
            println("🌐 Before the try to connect")

            try {
                val url = URL("$flaskUrl/api/query")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 5000
                conn.readTimeout = 5000

                val responseCode = conn.responseCode
                println("🌐 Connecting to: $url")

                try {
                    val responseCode = conn.responseCode
                    println("✅ Response code: $responseCode")
                } catch (e: Exception) {
                    println("❌ Failed to connect: ${e.message}")
                    e.printStackTrace()
                }

                if (responseCode == 200) {
                    val response = Scanner(conn.inputStream).useDelimiter("\\A").next()
                    println("✅ Response from Flask: $response")

                    val jsonArray = JSONArray(response)
                    val readings = mutableListOf<SensorReading>()

                    for (i in 0 until jsonArray.length()) {
                        val obj = jsonArray.getJSONObject(i)

                        val time = obj.optString("time")
                        val heartRate =
                            obj.optDouble("ir_values", Double.NaN).takeIf { !it.isNaN() }
                        val temp = obj.optDouble("temp", Double.NaN).takeIf { !it.isNaN() }
                        val accX = obj.optDouble("acc_X", Double.NaN).takeIf { !it.isNaN() }
                        val accY = obj.optDouble("acc_Y", Double.NaN).takeIf { !it.isNaN() }
                        val accZ = obj.optDouble("acc_Z", Double.NaN).takeIf { !it.isNaN() }
                        val emotion = obj.optString("emotion", "Api no send")
                        val magnitude =
                            obj.optDouble("avg_magnitude", Double.NaN).takeIf { !it.isNaN() }
                        val status =
                            obj.optString("activity_status", "No movement received from API")

                        readings.add(
                            SensorReading(
                                time,
                                heartRate,
                                temp,
                                accX,
                                accY,
                                accZ,
                                emotion,
                                magnitude,
                                status
                            )
                        )
                        // println("Readings are : $readings")
                    }

                    _sensorData.value = readings
                    println("📡 Updated ${readings.size} sensor readings.")
//                    readings.forEach {
//                        println("🕒 ${it.time} | ❤️ ${it.heartRate} | 🌡 ${it.temp} | X:${it.accX} Y:${it.accY} Z:${it.accZ}")
//                    }

                    try {
                        val url = URL("$flaskUrl/api/emotion_frequency")
                        val conn = url.openConnection() as HttpURLConnection
                        conn.requestMethod = "GET"
                        conn.connectTimeout = 5000
                        conn.readTimeout = 5000

                        println("🌐 Connecting to: $url")

                        try {
                            val responseCode = conn.responseCode
                            println("✅ Response code: $responseCode")
                        } catch (e: Exception) {
                            println("❌ Failed to connect: ${e.message}")
                            e.printStackTrace()
                        }

                        if (responseCode == 200) {
                            val responseCode = conn.responseCode
                            if (responseCode == HttpURLConnection.HTTP_OK) {
                                val response =
                                    conn.inputStream.bufferedReader().use { it.readText() }
                                Log.i("✅ Emotion frequency response", response)

                                // Parse JSON (e.g. {"Calm": 12, "Stressed": 3})
                                val json = JSONObject(response)
                                val map = mutableMapOf<String, Int>()
                                val keys = json.keys()
                                while (keys.hasNext()) {
                                    val key = keys.next()
                                    map[key] = json.getInt(key)
                                }

                                withContext(Dispatchers.Main) {
                                    _emotionData.value = map
                                    print("Map is $map")
                                }
                            } else {
                                Log.e("⚠️ Error", "HTTP error: $responseCode")
                            }

                        } else {
                            println("❌ HTTP Error while fetching emotions: $responseCode")
                        }

                    } catch (e: Exception) {
                        println("❌ Failed to initialize connection: ${e.message}")
                        e.printStackTrace()
                    }

                } else {
                    println("HTTP Error: $responseCode")
                }

                conn.disconnect()
            } catch (e: Exception) {
                println("⚠️ Error fetching data: ${e.message}")
                e.printStackTrace()
            }
        }
    }

}



