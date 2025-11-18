package com.example.smartband.data

import retrofit2.http.*

data class SensorData(val value: Double)
data class SensorResponse(val message: String)

interface ApiService {
    @POST("api/write")
    suspend fun sendData(@Body data: SensorData): SensorResponse

    @GET("api/query")
    suspend fun getData(): List<Map<String, Any>>
}
