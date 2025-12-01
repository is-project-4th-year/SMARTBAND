package com.example.smartband.screens.navbar

import android.content.Context
import android.content.Intent
import android.widget.Toast
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
import com.github.mikephil.charting.data.BarEntry
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.FileProvider
import com.example.smartband.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.example.smartband.influx.ReportViewModel
import com.example.smartband.navigation.Screen
import com.example.smartband.screens.EmotionDonutChart
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import com.github.mikephil.charting.formatter.ValueFormatter
import java.io.File
import android.content.ContentValues
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import java.io.OutputStream
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import com.example.smartband.influx.SensorReading
import com.example.smartband.screens.DonutChartWithLegend
import com.itextpdf.text.pdf.PdfPTable
import java.io.FileOutputStream


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun Reports( navController: NavController, viewModel: ReportViewModel, viewModels: InfluxViewModel){

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier.fillMaxSize().background(Color.White).padding(10.dp).verticalScroll(scrollState)
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
                fontSize = 15.sp,
                fontFamily = bodyFont,
                letterSpacing = 0.02.em,
                lineHeight = 22.sp,
                color = Color.Gray,
                modifier = Modifier.padding(4.dp).offset(x= (12).dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.9f).offset(x = 10.dp)
        ){

           ExportReportButton(viewModel = viewModels)
        }

        Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.padding(10.dp)
                    .background(colorResource(R.color.gray), shape = RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp))
            ) {

                Text(
                    text = "Week Summary",
                    fontSize = 18.sp,
                    fontFamily = bodyFont,
                    modifier = Modifier.padding(13.dp).offset(x= (5).dp)
                )


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                    Spacer(modifier = Modifier.width(4.dp))

                    EmotionDonutChart(viewModel = viewModels)
                }

            }

        Column (
            modifier = Modifier
                .padding(10.dp)
                .background(colorResource(R.color.purple), shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
        ) {
            Text(
                text = "Heart Rate Graph",
                fontSize = 18.sp,
                fontFamily = bodyFont,
                modifier = Modifier.padding(13.dp).offset(x = (5).dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                HeartRateLineChart(viewModel)
            }
        }


        Column (
            modifier = Modifier
                .padding(10.dp)
                .background(colorResource(R.color.white), shape = RoundedCornerShape(16.dp))
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

        Column (
            modifier = Modifier
                .padding(10.dp)
                .background(colorResource(R.color.purple), shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
        ){
            Text(
                text = "Motion Graph",
                fontSize = 18.sp,
                fontFamily = bodyFont,
                modifier = Modifier.padding(13.dp).offset(x= (5).dp)
            )

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                val todayActivity = listOf(5f, 3f, 4f) // Motion, Resting, Light, High
                ActivityBarChart(activityData = todayActivity)

            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        HorizontalDivider(modifier = Modifier.fillMaxWidth(0.6f).padding(horizontal = 14.dp))

        Spacer(modifier = Modifier.height(20.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Monthly Reports",
                fontSize = 20.sp,
                fontFamily = headlineFont2,
                modifier = Modifier.padding(10.dp).offset(x= (5).dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(0.9f),
        ){
            Text(
                text = "Your monthly summary is here — explore the charts and stats that capture your progress.",
                fontSize = 14.sp,
                fontFamily = bodyFont,
                letterSpacing = 0.02.em,
                lineHeight = 22.sp,
                color = Color.Gray,
                modifier = Modifier.padding(4.dp).offset(x= (12).dp)
            )
        }


        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier.padding(10.dp)
                .background(colorResource(R.color.gray), shape = RoundedCornerShape(16.dp))
                .clip(RoundedCornerShape(16.dp))
        ) {

            Text(
                text = "Month Emotion Summary",
                fontSize = 18.sp,
                fontFamily = bodyFont,
                modifier = Modifier.padding(13.dp).offset(x= (5).dp)
            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Spacer(modifier = Modifier.width(6.dp))

                MonthlyEmotionDonut()
            }

        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.9f).offset(x = 10.dp)
        ){

            ExportReportButton(viewModel = viewModels)
        }

        Spacer(modifier = Modifier.height(30.dp))


    }


}

@Composable
fun MonthlyEmotionDonut() {
    // Fixed values and labels
    val labels = listOf("Stressed", "Calm", "Amused")
    val values = listOf(56f, 40f, 9f) // percentages or counts

    val colors = listOf(
        Color(0xFF8E44AD), // Purple
        Color(0xFF3498DB), // Blue
        Color(0xFFE67E22), // Orange
    )

    DonutChartWithLegend(
        values = values,
        colors = colors.take(labels.size),
        labels = labels
    )
}


@Composable
fun HeartRateLineChart(viewModel: ReportViewModel) {
    val tempData by viewModel.heartrateData
    val daysOfWeek = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    if (tempData.isEmpty()){
        Row(
            modifier = Modifier.height(50.dp)
        ){
            Text("Loading heart rate data..", color = Color.Gray)
        }

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
        Row(
            modifier = Modifier.height(50.dp)
        ){
            Text("Loading temperature data..", color = Color.Gray)
        }
    }
    else{
        val entries = tempData.mapIndexed { index, temp ->
            Entry(index.toFloat(), temp) // X=index, Y=temp
        }

        val lineDataSet = LineDataSet(entries, "Skin Temperature (°C)").apply {
            color = Color(0xFF9C27B0).toArgb()  // Purple line
            lineWidth = 2f
            setDrawCircles(false)
          //  setDrawValues(false)
        }
        val lineData = LineData(lineDataSet)

        val formatter = IntValueFormatter()
        AndroidView(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(200.dp)
                .padding(15.dp),
            factory = { context ->
                LineChart(context).apply {
                    val red = androidx.compose.ui.graphics.Color(0xFF8E24AA).toArgb()   // Deep red
                    val redLight = androidx.compose.ui.graphics.Color(0xFFD1C4E9).toArgb() // Light red fill

                    val dataSet = LineDataSet(entries, "Temperature Data").apply {
                        color = red
                        setCircleColor(redLight)
                        lineWidth = 3f
                        circleRadius = 4f
                       // valueTextSize = 10f
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                        setDrawFilled(true)
                        fillColor = redLight
                        fillAlpha = 80
                        valueFormatter = object : ValueFormatter() {
                            override fun getPointLabel(entry: Entry?): String {
                                return entry?.y?.toInt().toString() // show int values
                            }
                        }
                    }

                    data = LineData(dataSet)
                    description.isEnabled = false
                    axisRight.isEnabled = false
                    axisLeft.textColor = android.graphics.Color.BLACK
                    xAxis.textColor = android.graphics.Color.BLACK

                    // X-axis integer labels
                    xAxis.apply {
                        textColor = android.graphics.Color.BLACK
                        setLabelCount(7, true) // force 7 labels evenly
                        valueFormatter = formatter
                    }

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
fun ActivityBarChart(
    activityData: List<Float> // pass in activity values for Motion, Resting, Light, High
) {
    val categories = listOf("Resting", "Light", "High") // X-axis labels

    if (activityData.isEmpty()) {
        Row(
            modifier = Modifier.height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Loading activity data...", color = Color.Gray)
        }
    } else {
        val entries = activityData.mapIndexed { index, value ->
            BarEntry(index.toFloat(), value)
        }

        val barDataSet = BarDataSet(entries, "Activity Levels").apply {
            colors = listOf(
                Color(0xFFFFC107).toArgb(), // Amber for Resting
                Color(0xFF2196F3).toArgb(), // Blue for Light Activity
                Color(0xFFF44336).toArgb()  // Red for High Activity
            )
            valueTextSize = 10f
        }

        val barData = BarData(barDataSet)

        AndroidView(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(15.dp)
                .height(200.dp),
            factory = { context ->
                BarChart(context).apply {
                    data = barData
                    description.isEnabled = false
                    axisRight.isEnabled = false
                    axisLeft.textColor = android.graphics.Color.BLACK

                    xAxis.apply {
                        position = XAxis.XAxisPosition.BOTTOM
                        granularity = 1f
                        setDrawGridLines(false)
                        textColor = android.graphics.Color.BLACK
                        valueFormatter = IndexAxisValueFormatter(categories)
                    }
                    legend.isEnabled = false
                    animateY(1000)
                }
            },
            update = { chart ->
                chart.data = barData
                chart.invalidate()
            }
        )
    }
}


class IntValueFormatter : ValueFormatter() {
    override fun getPointLabel(entry: Entry?): String {
        return entry?.y?.toInt().toString()
    }

    override fun getFormattedValue(value: Float): String {
        return value.toInt().toString()
    }
}

@Composable
fun ExportReportButton(viewModel: InfluxViewModel) {
    val context = LocalContext.current
    Button(
        onClick = {
        val readings = viewModel.sensorData.value
        saveReportPdf(context, readings)
        Toast.makeText(context, "PDF saved in Downloads", Toast.LENGTH_SHORT).show()
    },
        colors = ButtonDefaults.buttonColors(Color(0xFF090909)),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .border( width = 1.dp, color = Color.Black, shape = RoundedCornerShape(16.dp) )
            .height(42.dp)
    ) {
        Text("Download report",
            fontFamily = headlineFont2,
            color = Color.White,
            fontSize = 14.sp,
            letterSpacing = 0.02.em)
    }
}

fun saveReportPdf(context: Context, readings: List<SensorReading>) {
    try {
        if (readings.isEmpty()) {
            Toast.makeText(context, "No readings to export!", Toast.LENGTH_LONG).show()
            return
        }

        // Downloads folder
        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!downloadsDir.exists()) downloadsDir.mkdirs()

        val file = File(downloadsDir, "sensorReport.pdf")
        val document = Document()
        PdfWriter.getInstance(document, FileOutputStream(file))
        document.open()

        // Title
        document.add(Paragraph("Smart Band Weekly Report\n\n"))

        // Table with 9 columns for your data
        val table = PdfPTable(9)
        table.addCell("Time")
        table.addCell("Heart Rate")
        table.addCell("Temp")
        table.addCell("AccX")
        table.addCell("AccY")
        table.addCell("AccZ")
        table.addCell("Emotion")
        table.addCell("Magnitude")
        table.addCell("Status")

        readings.forEach { r ->
            table.addCell(r.time)
            table.addCell(r.heartRate?.toInt().toString()) // as int
            table.addCell(r.temp?.toInt().toString())      // as int
            table.addCell(r.accX.toString())
            table.addCell(r.accY.toString())
            table.addCell(r.accZ.toString())
            table.addCell(r.emotion)
            table.addCell(r.magnitude.toString())
            table.addCell(r.status)
        }

        document.add(table)
        document.close()

        Toast.makeText(context, "PDF saved to Downloads!", Toast.LENGTH_LONG).show()

    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(context, "Failed to save PDF: ${e.message}", Toast.LENGTH_LONG).show()
    }
}