package com.example.smartband.influx

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL
import java.util.Scanner
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class InfluxLocationModel : ViewModel(){
    private val flaskUrl = "https://89c24cf9e43f.ngrok-free.app"

    private val _locationData = mutableStateOf<List<Pair<Float, Float>>>(emptyList())
    val locationData: State<List<Pair<Float, Float>>> = _locationData

    private val _childLocation = MutableStateFlow<LatLng?>(null)
    val childLocation: StateFlow<LatLng?> = _childLocation.asStateFlow()

    init {
        fetchLocationDataPeriodically()
    }

    private fun fetchLocationDataPeriodically() {
        viewModelScope.launch {
            while (true) {
                locationData()
                delay(5000) // refresh every 5 seconds
            }
        }
    }

    suspend fun locationData() {
        viewModelScope.launch(Dispatchers.IO) {

            println("GPS Location report connect.....")

            try {
                val url = URL("$flaskUrl/api/location")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connectTimeout = 5000
                conn.readTimeout = 5000

                val responseCode = conn.responseCode
                println("Location Report connecting: $url")

                try {
                    val responseCode = conn.responseCode
                    println("✅ Report response code: $responseCode")
                } catch (e: Exception) {
                    println("❌ Failed to connect: ${e.message}")
                    e.printStackTrace()
                }

                if (responseCode == 200) {
                    val response = Scanner(conn.inputStream).useDelimiter("\\A").next()
                    println("✅ Response from Flask: $response")

                    val jsonObject = JSONObject(response)
                    val locationObj = jsonObject.optJSONObject("location")
                    val dataList = mutableListOf<Pair<Float, Float>>()

                    if (locationObj != null) {
                            val latitude = locationObj.optDouble("lat", Double.NaN)
                            val longitude = locationObj.optDouble("lon", Double.NaN)
                          //  val childLatLng = LatLng(latitude, longitude)

                            val latLng = LatLng(latitude, longitude)
                            // Store as Pair<lat, long>

                            withContext(Dispatchers.Main) {
                                _childLocation.value = latLng
                            }

                            // Also store in your list as Pair<Float, Float>
                            dataList.add(Pair(latitude.toFloat(), longitude.toFloat()))
                        } else {
                            println("⚠️ Invalid coordinates received")
                            withContext(Dispatchers.Main) {
                                _childLocation.value = null
                            }
                        }

                    }
                    else {
                    println("⚠️ Location unavailable (child may be indoors or GPS blocked)")
                    withContext(Dispatchers.Main) {
                        _childLocation.value = null
                    }

                }
                conn.disconnect()
                } catch (e: Exception) {
                    println("❌ Failed to initialize connection: ${e.message}")
                    e.printStackTrace()
                }
        }
    }
}