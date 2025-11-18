package com.example.smartband.screens.navbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartband.influx.InfluxViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.smartband.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.example.smartband.influx.ReportViewModel
import com.example.smartband.navigation.Screen
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
@Composable
fun Reports( navController: NavController, viewModel: ReportViewModel){
    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(10.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            IconButton(
                onClick = { navController.navigate(Screen.Landing.route) },
                modifier = Modifier.size(53.dp) //the clickable area
            ){
                androidx.compose.material3.Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back icon",
                    modifier = Modifier.size(32.dp)
                )
            }

        }


        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Weekly Reports",
                fontSize = 20.sp,
                fontFamily = headlineFont2,
                modifier = Modifier.padding(10.dp).offset(x= (5).dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
        ){
            Text(
                text = "Your weekly summary is here — explore the charts and stats that capture your progress.",
                fontSize = 14.sp,
                fontFamily = bodyFont,
                letterSpacing = 0.02.em,
                lineHeight = 22.sp,
                color = Color.Gray,
                modifier = Modifier.padding(4.dp).offset(x= (12).dp)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))


        Column(
            modifier = Modifier.padding(10.dp)
                .background(colorResource(R.color.gray), shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text(
                text = "Heart Rate Graph",
                fontSize = 18.sp,
                fontFamily = bodyFont,
                modifier = Modifier.padding(13.dp).offset(x= (5).dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){

                HeartRateLineChart(viewModel)
            }
        }


        Column (
            modifier = Modifier
                .padding(10.dp)
                .background(colorResource(R.color.purple), shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
        ){
            Text(
                text = "Temperature Graph",
                fontSize = 18.sp,
                fontFamily = bodyFont,
                modifier = Modifier.padding(13.dp).offset(x= (5).dp)
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                TempLineChart(viewModel)
            }
        }

    }

}


@Composable
fun HeartRateLineChart(viewModel: ReportViewModel) {
    val tempData by viewModel.heartrateData
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    if (tempData.isEmpty()){
        Text("Loading temperature data..", color = Color.Gray)
    }
    else{
        val entries = tempData.mapIndexed { index, temp ->
            Entry(index.toFloat(), temp) // X=index, Y=temp
        }

        val lineDataSet = LineDataSet(entries, "Skin Temperature (°C)").apply {
            color = Color(0xFF9C27B0).toArgb()  // Purple line
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)
        }
        val lineData = LineData(lineDataSet)

        AndroidView(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(15.dp)
                .height(200.dp),
            factory = { context ->
                LineChart(context).apply {
                    val red = androidx.compose.ui.graphics.Color(0xFFF44336).toArgb()   // Deep red
                    val redLight = androidx.compose.ui.graphics.Color(0xFFF35F54).toArgb() // Light red fill

                    val dataSet = LineDataSet(entries, "Heart rate Data").apply {
                        color = red
                        setCircleColor(redLight)
                        lineWidth = 3f
                        circleRadius = 4f
                        valueTextSize = 10f
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                        setDrawFilled(true)
                        fillColor = redLight
                        fillAlpha = 80
                    }

                    data = LineData(dataSet)
                    description.isEnabled = false
                    axisRight.isEnabled = false
                    axisLeft.textColor = android.graphics.Color.BLACK
                    xAxis.textColor = android.graphics.Color.BLACK
                    legend.isEnabled = false
                    animateX(1000)

                    // Customize X Axis to show days
//                    xAxis.apply {
//                        position = XAxis.XAxisPosition.BOTTOM
//                        valueFormatter = IndexAxisValueFormatter(daysOfWeek)
//                        granularity = 1f
//                        setLabelCount(daysOfWeek.size, true)
//                        textSize = 12f
//                        axisMinimum = 0f
//                        axisMaximum = (daysOfWeek.size - 1).toFloat()
//                    }
//                    axisLeft.apply {
//                        axisMinimum = 35f
//                        axisMaximum = 38f
//                        textSize = 11f
//                    }

                }
            },
            update = { chart ->
                val red = androidx.compose.ui.graphics.Color(0xFFF44336).toArgb()
                val redLight = androidx.compose.ui.graphics.Color(0xFFF35F54).toArgb()
                val dataSet = LineDataSet(entries, "Heart rate Data").apply {
                    color = red
                    setCircleColor(redLight)
                    lineWidth = 3f
                    circleRadius = 3f
                    valueTextSize = 10f
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    setDrawFilled(true)
                    fillColor = redLight
                    fillAlpha = 80
                }

                chart.data = LineData(dataSet)
                chart.invalidate()
            },

            )
    }
}



@Composable
fun TempLineChart(viewModel: ReportViewModel) {
    val tempData by viewModel.temperatureData
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    if (tempData.isEmpty()){
        Text("Loading temperature data..", color = Color.Gray)
    }
    else{
        val entries = tempData.mapIndexed { index, temp ->
            Entry(index.toFloat(), temp) // X=index, Y=temp
        }

        val lineDataSet = LineDataSet(entries, "Skin Temperature (°C)").apply {
            color = Color(0xFF9C27B0).toArgb()  // Purple line
            lineWidth = 2f
            setDrawCircles(false)
            setDrawValues(false)
        }
        val lineData = LineData(lineDataSet)

        AndroidView(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(15.dp)
                .height(200.dp),
            factory = { context ->
                LineChart(context).apply {
                    val red = androidx.compose.ui.graphics.Color(0xFF8E24AA).toArgb()   // Deep red
                    val redLight = androidx.compose.ui.graphics.Color(0xFFD1C4E9).toArgb() // Light red fill

                    val dataSet = LineDataSet(entries, "Temperature Data").apply {
                        color = red
                        setCircleColor(redLight)
                        lineWidth = 3f
                        circleRadius = 4f
                        valueTextSize = 10f
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                        setDrawFilled(true)
                        fillColor = redLight
                        fillAlpha = 80
                    }

                    data = LineData(dataSet)
                    description.isEnabled = false
                    axisRight.isEnabled = false
                    axisLeft.textColor = android.graphics.Color.BLACK
                    xAxis.textColor = android.graphics.Color.BLACK
                    legend.isEnabled = false
                    animateX(1000)


                }
            },
            update = { chart ->
                val red = androidx.compose.ui.graphics.Color(0xFF8E24AA).toArgb()
                val redLight = androidx.compose.ui.graphics.Color(0xFF8E24AA).toArgb()
                val dataSet = LineDataSet(entries, "Temperature Data").apply {
                    color = red
                    setCircleColor(redLight)
                    lineWidth = 3f
                    circleRadius = 3f
                    valueTextSize = 10f
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    setDrawFilled(true)
                    fillColor = redLight
                    fillAlpha = 80
                }

                chart.data = LineData(dataSet)
                chart.invalidate()
            },

        )
    }
}



@Composable
fun InfluxDataScreen(viewModel: InfluxViewModel = viewModel()) {
    val sensorData by viewModel.sensorData.collectAsState()
//    val emotion by viewModel.emotion.collectAsState()

    if (sensorData.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp)
        ) {
            Text("Loading sensor data...", modifier = Modifier.padding(16.dp))
        }

    } else {

        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item {
                Text("Live Sensor Data")
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(sensorData) { reading ->
                Card(modifier = Modifier.padding(vertical = 4.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("Time: ${reading.time}")
                        Text("Heart Rate: ${reading.heartRate ?: 0.0}")
                        Text("Temperature: ${reading.temp ?: 0.0}")
                        Text("Acc X: ${reading.accX ?: 0.0}")
                        Text("Acc Y: ${reading.accY ?: 0.0}")
                        Text("Acc Z: ${reading.accZ ?: 0.0}")
                    }
                }
            }
        }


    }
}
